package com.practice;


import com.practice.element.Worker;
import com.practice.enumeration.EColor;
import com.practice.enumeration.HColor;
import com.practice.enumeration.Position;
import com.practice.enumeration.Status;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashSet;
import java.util.Set;

public class Validator {
    static Set<String> personIDSet = new HashSet();

    public Validator() {
    }

    public static boolean validateWorkerName(String name) {
        return name != null && name.matches("^[a-zA-Z0-9_]+$");
    }

    public static boolean validateWorkerCoordinates(String coords) {
        if (coords.matches("^[+-]?[0-9]+.[0-9]+ [+-]?[0-9]+$")) {
            String[] parsed = coords.split(" ");

            try {
                Float x = Float.parseFloat(parsed[0]);
                Integer y = Integer.parseInt(parsed[1]);
                if (x > -255.0F && y > -131) {
                    return true;
                }
            } catch (NumberFormatException var4) {
                return false;
            }
        }

        return false;
    }

    public static boolean validateWorkerSalary(String line) {
        try {
            Integer salary = Integer.parseInt(line);
            return true;
        } catch (NumberFormatException var2) {
            return false;
        }
    }

    public static boolean validateWorkerPosition(String line) {
        try {
            Position position = Position.valueOf(line.toUpperCase());
            return true;
        } catch (IllegalArgumentException var2) {
            return false;
        }
    }

    public static boolean validateWorkerStatus(String line) {
        try {
            Status status = Status.valueOf(line.toUpperCase());
            return true;
        } catch (IllegalArgumentException var2) {
            return false;
        }
    }

    public static boolean validatePersonPassportID(String line) {
        if (4 < line.length() && line.length() < 33) {
            if (personIDSet.contains(line)) {
                return false;
            } else {
                try {
                    long personId = Long.parseLong(line);
                    return true;
                } catch (NumberFormatException var3) {
                    return false;
                }
            }
        } else {
            return false;
        }
    }

    public static boolean validatePersonEyeColor(String line) {
        try {
            EColor eyeColor = EColor.valueOf(line.toUpperCase());
            return true;
        } catch (IllegalArgumentException var2) {
            return false;
        }
    }

    public static boolean validatePersonHairColor(String line) {
        try {
            HColor hairColor = HColor.valueOf(line.toUpperCase());
            return true;
        } catch (IllegalArgumentException var2) {
            return false;
        }
    }

    public static boolean validateLocationX(String line) {
        try {
            Long x = Long.parseLong(line);
            return true;
        } catch (NumberFormatException var2) {
            return false;
        }
    }

    public static boolean validateLocationY(String line) {
        try {
            Long y = Long.parseLong(line);
            return true;
        } catch (NumberFormatException var2) {
            return false;
        }
    }

    public static boolean validateLocationName(String line) {
        return line != null;
    }

    public static boolean validateWorkerCreationDate(String line) {
        try {
            LocalDate localDate = LocalDate.parse(line, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            return true;
        } catch (DateTimeParseException var2) {
            return false;
        }
    }

    public static boolean validateWorkerStartDate(String line) {
        try {
            ZonedDateTime startDate = ZonedDateTime.parse(line, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z"));
            return true;
        } catch (DateTimeParseException var2) {
            return false;
        }
    }

    public static boolean validateWorkerID(String line) {
        try {
            int id = Integer.parseInt(line);
            return id >= 0 && !Worker.idArrayList.contains(id);
        } catch (NumberFormatException var2) {
            return false;
        }
    }
}
