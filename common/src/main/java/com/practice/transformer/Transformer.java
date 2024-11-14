package com.practice.transformer;

import com.practice.dto.CoordinatesDto;
import com.practice.dto.PersonDto;
import com.practice.dto.WorkerDto;
import com.practice.element.Coordinates;
import com.practice.element.Person;
import com.practice.element.Worker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class Transformer {
    private static final Logger log = LoggerFactory.getLogger(Transformer.class);

    public Transformer() {
    }

    public static byte[] writeObject(Object obj) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            byte[] var3;
            try {
                ObjectOutputStream out = new ObjectOutputStream(bos);
                out.writeObject(obj);
                var3 = bos.toByteArray();
            } catch (Throwable var5) {
                try {
                    bos.close();
                } catch (Throwable var4) {
                    var5.addSuppressed(var4);
                }

                throw var5;
            }

            bos.close();
            return var3;
        } catch (IOException var6) {
            log.error("[transformer.Transformer]: writeObject");
            return new byte[0];
        }
    }

    public static Object readObject(byte[] arr) {
        ByteArrayInputStream bis = new ByteArrayInputStream(arr);

        try {
            ObjectInputStream in = new ObjectInputStream(bis);

            Object var3;
            try {
                var3 = in.readObject();
            } catch (Throwable var6) {
                try {
                    in.close();
                } catch (Throwable var5) {
                    var6.addSuppressed(var5);
                }

                throw var6;
            }

            in.close();
            return var3;
        } catch (ClassNotFoundException | IOException var7) {
            log.error("[transformer.Transformer]: Deserialize");
            return null;
        }
    }

    public static WorkerDto WorkerToWorkerDto(Worker worker) {
        WorkerDto workerDto = new WorkerDto();
        workerDto.setUser(worker.getUser());
        workerDto.setId(worker.getId());
        workerDto.setName(worker.getName());
        workerDto.setCoordinatesDto(CoordinatesToCoordinatesDto(worker.getCoordinates()));
        workerDto.setCreationDate(worker.getCreationDate());
        workerDto.setSalary(worker.getSalary());
        workerDto.setStartDate(worker.getStartDate());
        workerDto.setPosition(worker.getPosition());
        workerDto.setStatus(worker.getStatus());
        workerDto.setPersonDto(PersonToPersonDto(worker.getPerson()));
        workerDto.setLocation(worker.getLocation());
        return workerDto;
    }

    public static PersonDto PersonToPersonDto(Person person) {
        PersonDto personDto = new PersonDto();
        personDto.setPassportID(person.getPassportID());
        personDto.setEyeColor(person.getEyeColor());
        personDto.setHairColor(person.getHairColor());
        personDto.setLocation(person.getLocation());
        return personDto;
    }

    public static CoordinatesDto CoordinatesToCoordinatesDto(Coordinates coordinates) {
        CoordinatesDto coordinatesDto = new CoordinatesDto();
        coordinatesDto.setX(coordinates.getX());
        coordinatesDto.setY(coordinates.getY());
        return coordinatesDto;
    }

    public static Worker WorkerDtoToWorker(WorkerDto workerDto) {
        Worker worker = new Worker();
        worker.setUser(workerDto.getUser());
        worker.setName(workerDto.getName());
        worker.setCoordinates(CoordinatesDtoToCoordinates(workerDto.getCoordinatesDto()));
        worker.setSalary(workerDto.getSalary());
        worker.setPosition(workerDto.getPosition());
        worker.setStatus(workerDto.getStatus());
        worker.setPerson(PersonToPersonDto(workerDto.getPersonDto()));
        worker.setLocation(workerDto.getLocation());
        return worker;
    }

    public static Person PersonToPersonDto(PersonDto personDto) {
        Person person = new Person();
        person.setPassportID(personDto.getPassportID());
        person.setEyeColor(personDto.getEyeColor());
        person.setHairColor(personDto.getHairColor());
        person.setLocation(personDto.getLocation());
        return person;
    }

    public static Coordinates CoordinatesDtoToCoordinates(CoordinatesDto coordinatesDto) {
        Coordinates coordinates = new Coordinates();
        coordinates.setX(coordinatesDto.getX());
        coordinates.setY(coordinatesDto.getY());
        return coordinates;
    }
}
