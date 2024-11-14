package com.practice.dto;


import com.practice.element.Location;
import com.practice.enumeration.Position;
import com.practice.enumeration.Status;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.ZonedDateTime;

@Setter
@Getter
public class WorkerDto extends AbstractCommandDto {
    private String user;
    private int id;
    private String name;
    private CoordinatesDto coordinatesDto;
    private LocalDate creationDate;
    private Integer salary;
    private ZonedDateTime startDate;
    private Position position;
    private Status status;
    private PersonDto personDto;
    private Location location;

    public WorkerDto() {
    }
}
