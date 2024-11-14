package com.practice.element;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Location implements Serializable {
    private Long x;
    private Long y;
    private String name;

    public Location() {
    }

    public Location(Long x, Long y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
    }

    //todo fninsh
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        sb.append("       \"locX\":").append(this.x).append("\n");
        sb.append("       \"locY\":").append(this.y).append("\n");
        sb.append("       \"locName\":\"").append(this.name).append("\"\n");
        sb.append("       }");
        return sb.toString();
    }


}
