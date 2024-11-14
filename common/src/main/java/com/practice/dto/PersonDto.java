package com.practice.dto;


import com.practice.element.Location;
import com.practice.enumeration.EColor;
import com.practice.enumeration.HColor;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class PersonDto extends AbstractCommandDto {
    private String passportID;
    private EColor eyeColor;
    private HColor hairColor;
    private Location location;

    public PersonDto() {
    }

}
