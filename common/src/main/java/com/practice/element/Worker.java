package com.practice.element;

import com.practice.enumeration.Position;
import com.practice.enumeration.Status;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Getter
@Setter
public class Worker implements Comparable<Worker> {
    private String user;
    private int id;
    private String name;
    private Coordinates coordinates = new Coordinates();
    private LocalDate creationDate;
    private Integer salary;
    ZonedDateTime startDate;
    private Position position;
    private Status status;
    private Person person = new Person();
    public static ArrayList<Integer> idArrayList = new ArrayList();
    private Location location = new Location();

    public Worker() {
    }

    public Worker(String user, int id, String name, Coordinates coordinates, LocalDate creationDate, Integer salary, ZonedDateTime startDate, Position position, Status status, Person person) {
        this.user = user;
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.salary = salary;
        this.startDate = startDate;
        this.position = position;
        this.status = status;
        this.person = person;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = LocalDate.parse(creationDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public void setStartDate(String startDate) {
        this.startDate = ZonedDateTime.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX"));
    }
//todo finish
    public String toString() {
        try {
            return "{\n\"id\":" + this.id +
                    ",\n\"name\":\"" + this.name +
                    "\",\n\"coordinates\":" + this.coordinates.toString() +
                    ",\n\"creationDate\":\"" + this.creationDate +
                    "\",\n\"salary\":" + this.salary +
                    ",\n\"startDate\":\"" + this.startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX")) +
                    "\",\n\"position\":\"" + this.position +
                    "\",\n\"status\":\"" + this.status +
                    "\",\n\"person\":" + this.person.toString() + "\n}";
        } catch (Exception e) {
            return "{\n\"id\":" + this.id +
                    ",\n\"name\":\"" + this.name +
                    "\",\n\"coordinates\":" + "this.coordinates.toString()" +
                    ",\n\"creationDate\":\"" + this.creationDate +
                    "\",\n\"salary\":" + this.salary +
                    ",\n\"startDate\":\"" + "" +
                    "\",\n\"position\":\"" + this.position +
                    "\",\n\"status\":\"" + this.status +
                    "\",\n\"person\":" + "this.person.toString()" + "\n}";
        }
    }

    public int compareTo(Worker other) {
        return this.salary.compareTo(other.salary);
    }

}
