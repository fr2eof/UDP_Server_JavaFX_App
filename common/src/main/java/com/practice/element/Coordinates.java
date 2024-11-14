package com.practice.element;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Coordinates {
    private Float x;
    private Integer y;

    public Coordinates() {
    }

    public Coordinates(Float x, Integer y) {
        this.x = x;
        this.y = y;
    }
//todo finish
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"x\":").append(this.x).append(",\"y\":").append(this.y).append("}");
        return sb.toString();
    }
}
