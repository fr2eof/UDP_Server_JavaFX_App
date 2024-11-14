package com.practice.database;

import com.practice.Hasher;
import com.practice.element.Coordinates;
import com.practice.element.Location;
import com.practice.element.Person;
import com.practice.element.Worker;
import com.practice.enumeration.EColor;
import com.practice.enumeration.HColor;
import com.practice.enumeration.Position;
import com.practice.enumeration.Status;
import com.practice.exception.LoginMatchException;
import com.practice.exception.UserLoginExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Proxy;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class DataBaseManager {
    private static final Logger log = LoggerFactory.getLogger(DataBaseManager.class);
    private static final String URL = "db.url";
    private static final String USERNAME = "db.username";
    private static final String PASSWORD = "db.password";
    private static int POOL_SIZE;
    private static BlockingQueue<Connection> pool;
    private static List<Connection> sourceConnections;

    public DataBaseManager() {
        try {
            Properties prop = new Properties();
            FileInputStream fis = new FileInputStream("application.properties");
            prop.load(fis);
            //POOL_SIZE = Integer.parseInt(prop.getProperty("db.poolSize"));
            POOL_SIZE = 10;
        } catch (IOException ex) {
            log.error("Problem with resources opening {}", ex.getMessage());
        }
        initConnectionPool();
    }

    private static void initConnectionPool() {
        try {
            sourceConnections = new ArrayList<>(POOL_SIZE);
            pool = new ArrayBlockingQueue<>(10);//todo change on POOL_SIZE;l
            for (int i = 0; i < POOL_SIZE; ++i) {
                var connection = open();
                var proxyCon = (Connection) Proxy.newProxyInstance(
                        DataBaseManager.class.getClassLoader(),
                        new Class[]{Connection.class},
                        (proxy, method, args) -> method.getName().equals("close") ? pool.offer((Connection) proxy) : method.invoke(connection, args));
                pool.add(proxyCon);
                sourceConnections.add(connection);
            }
        } catch (IOException | SQLException e) {
            log.error("Connection pool initialization {}", e.getMessage());
            System.exit(0);
        }
    }

    public static void closePool() {
        for (Connection connection : sourceConnections) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static Connection open() throws IOException, SQLException {
        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream("application.properties");
        prop.load(fis);
        return DriverManager.getConnection(prop.getProperty(URL), prop.getProperty(USERNAME), prop.getProperty(PASSWORD));
    }

    public static synchronized Connection get() {
        try {
            return pool.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean login(String login, String password) throws LoginMatchException {
        try (Connection con = get()) {
            PreparedStatement statement = con.prepareStatement("SELECT * FROM users WHERE login = ?;");
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                if (password == null) {
                    password = "";
                }
                return resultSet.getString("password").equals(Hasher.getHash(password) + resultSet.getString("salt"));
            } else {
                log.error("Login matching: ");
                throw new LoginMatchException();
            }
        } catch (SQLException e) {
            log.error("Login & password matching: {}", e.getMessage());
            return false;
        }
    }

    public static boolean register(String login, String password) throws UserLoginExistsException {
        try (Connection con = get()) {
            PreparedStatement stat = con.prepareStatement("SELECT * FROM users WHERE login = ?;");
            stat.setString(1, login);
            ResultSet rows = stat.executeQuery();
            if (!rows.next()) {
                PreparedStatement statement = con.prepareStatement("INSERT INTO users (login, password, salt) VALUES (?,?,?);");
                byte[] salt = Hasher.generateSalt();
                statement.setString(1, login);
                statement.setString(2, Hasher.getHash(password) + Hasher.getHash(Arrays.toString(salt)));
                statement.setString(3, Hasher.getHash(Arrays.toString(salt)));
                int updatedRows = statement.executeUpdate();
                con.close();
                return updatedRows > 0;
            } else {
                throw new UserLoginExistsException();
            }
        } catch (SQLException e) {
            log.error("[DATA BASE MANAGER]: Registration " + e.getMessage());
        }
        return false;
    }

    public static synchronized boolean add(Worker worker) {
        try (PreparedStatement statement = get().prepareStatement("INSERT INTO objects (userLogin, name, creationDate, startDate, position, status, salary, x, y, person_passportId, person_eyeColor,person_hairColor, location_x, location_y, location_name) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
            statement.setString(1, worker.getUser());
            statement.setString(2, worker.getName());
            statement.setString(3, String.valueOf(worker.getCreationDate()));
            statement.setString(4, String.valueOf(worker.getStartDate()));
            statement.setString(5, String.valueOf(worker.getPosition()));
            statement.setString(6, String.valueOf(worker.getStatus()));
            statement.setInt(7, worker.getSalary());
            statement.setFloat(8, worker.getCoordinates().getX());
            statement.setInt(9, worker.getCoordinates().getY());
            statement.setString(10, worker.getPerson().getPassportID());
            statement.setString(11, String.valueOf(worker.getPerson().getEyeColor()));
            statement.setString(12, String.valueOf(worker.getPerson().getHairColor()));
            statement.setLong(13, worker.getPerson().getLocation().getX());
            statement.setLong(14, worker.getPerson().getLocation().getY());
            statement.setString(15, worker.getPerson().getLocation().getName());
            int updatedRows = statement.executeUpdate();
            return updatedRows > 0;
        } catch (SQLException e) {
            log.error("Exception caught: " + e.getMessage());
            return false;
        }
    }

    public static synchronized boolean addIfMax(Worker worker, Integer salary) {
        try (PreparedStatement statement = get().prepareStatement("SELECT MAX(salary) AS max_salary FROM objects WHERE userlogin=?;");) {
            statement.setString(1, worker.getUser());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int maxSalary = resultSet.getInt(1);
                if (salary > maxSalary) {
                    add(worker);
                    return true;
                }
            }
            return false;
        } catch (SQLException e) {
            log.error("Exception caught: " + e.getMessage());
            return false;
        }
    }

    public static boolean clear(String userLogin) {
        try (PreparedStatement statement = get().prepareStatement("DELETE FROM objects WHERE userlogin=?;")) {
            statement.setString(1, userLogin);
            int updatedRows = statement.executeUpdate();
            return updatedRows > 0;
        } catch (SQLException e) {
            log.error("Exception caught: " + e.getMessage());
            return false;
        }
    }

    public static int size(String userLogin) {
        try (PreparedStatement statement = get().prepareStatement("SELECT COUNT(*) FROM objects WHERE userlogin = ?;")) {
            statement.setString(1, userLogin);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            log.error("Exception caught: " + e.getMessage());
            return -1;
        }
        return -1;
    }

    public static String minById(String userLogin) {
        try (PreparedStatement statement = get().prepareStatement("SELECT * FROM objects where userlogin=? order BY workerid ASC LIMIT 1;")) {
            statement.setString(1, userLogin);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Worker worker = new Worker();
                worker.setUser(userLogin);
                worker.setId(resultSet.getInt(2));
                worker.setName(resultSet.getString(3));
                worker.setCreationDate(resultSet.getString(4));
                worker.setStartDate(resultSet.getString(5));
                worker.setPosition(Position.valueOf(resultSet.getString(6)));
                worker.setStatus(Status.valueOf(resultSet.getString(7)));
                worker.setSalary(resultSet.getInt(8));
                worker.setCoordinates(new Coordinates(resultSet.getFloat(9), resultSet.getInt(10)));
                worker.setPerson(new Person(resultSet.getString(11),
                        EColor.valueOf(resultSet.getString(12)),
                        HColor.valueOf(resultSet.getString(13)),
                        new Location(resultSet.getLong(14),
                                resultSet.getLong(15),
                                resultSet.getString(16))));
                return worker.toString();
            } else {
                return "No objects have been created under your login yet";
            }
        } catch (SQLException e) {
            log.error("Exception caught: " + e.getMessage());
            return null;
        }
    }

    public static synchronized String printAscending(String userLogin) {
        try (PreparedStatement statement = get().prepareStatement("SELECT * FROM objects where userlogin=? order BY workerid ASC;")) {
            statement.setString(1, userLogin);
            ResultSet resultSet = statement.executeQuery();
            StringBuilder stringBuilder = new StringBuilder();
            if (resultSet.next()) {
                do {
                    Worker worker = new Worker();
                    worker.setUser(userLogin);
                    worker.setId(resultSet.getInt(2));
                    worker.setName(resultSet.getString(3));
                    worker.setCreationDate(resultSet.getString(4));
                    worker.setStartDate(resultSet.getString(5));
                    worker.setPosition(Position.valueOf(resultSet.getString(6)));
                    worker.setStatus(Status.valueOf(resultSet.getString(7)));
                    worker.setSalary(resultSet.getInt(8));
                    worker.setCoordinates(new Coordinates(resultSet.getFloat(9), resultSet.getInt(10)));
                    worker.setPerson(new Person(resultSet.getString(11),
                            EColor.valueOf(resultSet.getString(12)),
                            HColor.valueOf(resultSet.getString(13)),
                            new Location(resultSet.getLong(14),
                                    resultSet.getLong(15),
                                    resultSet.getString(16))));
                    stringBuilder.append(worker);
                } while (resultSet.next());
                return stringBuilder.toString();
            } else {
                return "No objects have been created under your login yet";
            }
        } catch (SQLException e) {
            log.error("Exception caught: " + e.getMessage());
            return null;
        }
    }

    public static synchronized String removeFirst(String userLogin) {
        try (PreparedStatement statement = get().prepareStatement("DELETE FROM objects WHERE workerid = (SELECT MIN(workerid) FROM objects where userlogin=?);")) {
            statement.setString(1, userLogin);
            int updatedRows = statement.executeUpdate();
            if (updatedRows == 1) {
                return "Removal was successful";
            } else {
                return "No matching items were found";
            }
        } catch (SQLException e) {
            log.error("Exception caught: " + e.getMessage());
            return null;
        }
    }

    public static synchronized String removeById(String userLogin, long id) {
        try (PreparedStatement statement = get().prepareStatement("DELETE FROM objects WHERE userlogin=? and workerid=?;")) {
            statement.setString(1, userLogin);
            statement.setLong(2, id);
            int updatedRows = statement.executeUpdate();
            if (updatedRows == 1) {
                return "Removal was successful";
            } else {
                return "No matching items were found";
            }
        } catch (SQLException e) {
            log.error("Exception caught: " + e.getMessage());
            return null;
        }
    }

    public static synchronized String removeAnyBySalary(String userLogin, int salary) {
        try (PreparedStatement statement = get().prepareStatement("DELETE FROM objects\n" +
                "WHERE workerid IN (\n" +
                "    SELECT workerid FROM objects\n" + "WHERE userlogin=?" + "and salary = ?\n" +
                "    LIMIT 1\n" +
                ");")) {
            statement.setString(1, userLogin);
            statement.setInt(2, salary);
            int updatedRows = statement.executeUpdate();
            if (updatedRows == 1) {
                return "Removal was successful";
            } else {
                return "No matching items were found";
            }
        } catch (SQLException e) {
            log.error("Exception caught: " + e.getMessage());
            return null;
        }
    }

    public static String show(String userLogin) {
        try (PreparedStatement statement = get().prepareStatement("SELECT * FROM objects where userlogin=?;")) {
            statement.setString(1, userLogin);
            ResultSet resultSet = statement.executeQuery();
            StringBuilder stringBuilder = new StringBuilder();
            if (resultSet.next()) {
                do {
                    Worker worker = new Worker();
                    worker.setUser(userLogin);
                    worker.setId(resultSet.getInt(2));
                    worker.setName(resultSet.getString(3));
                    worker.setCreationDate(resultSet.getString(4));
                    worker.setStartDate(resultSet.getString(5));
                    worker.setPosition(Position.valueOf(resultSet.getString(6)));
                    worker.setStatus(Status.valueOf(resultSet.getString(7)));
                    worker.setSalary(resultSet.getInt(8));
                    worker.setCoordinates(new Coordinates(resultSet.getFloat(9), resultSet.getInt(10)));
                    worker.setPerson(new Person(resultSet.getString(11),
                            EColor.valueOf(resultSet.getString(12)),
                            HColor.valueOf(resultSet.getString(13)),
                            new Location(resultSet.getLong(14),
                                    resultSet.getLong(15),
                                    resultSet.getString(16))));
                    stringBuilder.append(worker);
                } while (resultSet.next());
                return stringBuilder.toString();
            } else {
                return "No objects have been created under your login yet";
            }
        } catch (SQLException e) {
            log.error("Exception caught: " + e.getMessage());
            return null;
        }
    }

    public static synchronized String update(Worker worker, int id) {
        try (PreparedStatement statement = get().prepareStatement("UPDATE objects set userLogin=?, name=?, creationDate=?, startDate=?, position=?, status=?, salary=?, x=?, y=?, person_passportId=?, person_eyeColor=?,person_hairColor=?, location_x=?, location_y=?, location_name=? WHERE workerid =?")) {
            statement.setString(1, worker.getUser());
            statement.setString(2, worker.getName());
            statement.setString(3, String.valueOf(worker.getCreationDate()));
            statement.setString(4, String.valueOf(worker.getStartDate()));
            statement.setString(5, String.valueOf(worker.getPosition()));
            statement.setString(6, String.valueOf(worker.getStatus()));
            statement.setInt(7, worker.getSalary());
            statement.setFloat(8, worker.getCoordinates().getX());
            statement.setInt(9, worker.getCoordinates().getY());
            statement.setString(10, worker.getPerson().getPassportID());
            statement.setString(11, String.valueOf(worker.getPerson().getEyeColor()));
            statement.setString(12, String.valueOf(worker.getPerson().getHairColor()));
            statement.setLong(13, worker.getPerson().getLocation().getX());
            statement.setLong(14, worker.getPerson().getLocation().getY());
            statement.setString(15, worker.getPerson().getLocation().getName());
            statement.setInt(16, id);
            int updatedRows = statement.executeUpdate();
            if (updatedRows == 1) {
                return "Update was successful";
            } else {
                return "No matching items were found";
            }
        } catch (SQLException e) {
            log.error("Exception caught: " + e.getMessage());
            return null;
        }
    }
}