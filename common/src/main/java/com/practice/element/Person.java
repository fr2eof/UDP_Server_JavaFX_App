package com.practice.element;

import com.practice.enumeration.EColor;
import com.practice.enumeration.HColor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Person {
    private String passportID;
    EColor eyeColor;
    private HColor hairColor;
    private Location location;

    public Person() {
    }

    public Person(String passportID, EColor eyeColor, HColor hairColor, Location location) {
        this.passportID = passportID;
        this.eyeColor = eyeColor;
        this.hairColor = hairColor;
        this.location = location;
    }

    //todo finish
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        sb.append("   \"passportID\":").append(this.passportID).append("\n");
        sb.append("   \"eyeColor\":\"").append(this.eyeColor).append("\"\n");
        sb.append("   \"hairColor\":\"").append(this.hairColor).append("\"\n");
        sb.append("   \"location\":").append(this.location != null ? this.location.toString() : "null").append("\n");
        sb.append("   }");
        return sb.toString();
    }

}