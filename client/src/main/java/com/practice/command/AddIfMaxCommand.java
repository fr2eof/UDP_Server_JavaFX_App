package com.practice.command;//package command;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import transformer.Transformer;
//import command.stuff.Describable;
//import dto.CommandRequestDto;
//import dto.WorkerDto;
//import element.*;
//import exception.WrongAmountOfArgumentsException;
//import exception.WrongArgumentTypeException;
//
//import network.RequestSender;
//
//public class AddIfMaxCommand extends AbstractCommand implements Describable {
//    private static final Logger log = LoggerFactory.getLogger(AddIfMaxCommand.class);
//
//    public AddIfMaxCommand() {
//        super("add_if_max");
//    }
//
//    public boolean execute(Object[] args) {
//        try {
//            if (args.length != 3) {
//                throw new WrongAmountOfArgumentsException();
//            }
//            try {
//                int salary = Integer.parseInt((String) args[0]);
//            } catch (NumberFormatException e) {
//                throw new WrongArgumentTypeException();
//            }
//            CommandRequestDto crd = (CommandRequestDto) args[2];
//            RequestSender requestSender = (RequestSender) args[1];
//            Worker worker = new Worker();
//            worker.setUser(null);
//            worker.setName(WorkerNameInput.readWorkerName());
//            worker.setCoordinates(WorkerCoordinatesInput.readWorkerCoordinates());
//            worker.setSalary(Integer.parseInt((String) args[0]));
//            worker.setPosition(WorkerPositionInput.readWorkerPosition());
//            worker.setStatus(WorkerStatusInput.readWorkerStatus());
//            worker.setPerson(new Person(PersonPassportIdInput.readPersonPassportId(), PersonEyeColorInput.readPersonEyeColor(), PersonHairColorInput.readPersonHairColor(), new Location(LocationXInput.readLocationX(), LocationYInput.readLocationY(), LocationNameInput.readLocationName())));
//            worker.setLocation(worker.getPerson().getLocation());
//            WorkerDto workerDto = transformer.Transformer.WorkerToWorkerDto(worker);
//            crd.setCommandName("add_if_max");
//            crd.setCommandArgs(new Object[]{workerDto, Integer.parseInt((String) args[0])});
//            requestSender.sendRequest(transformer.Transformer.writeObject(crd));
//            return true;
//        } catch (WrongAmountOfArgumentsException var6) {
//            log.error("One argument in " + this.getName());
//            return false;
//        } catch (WrongArgumentTypeException var6) {
//            throw var6;
//        } catch (Exception var7) {
//            log.error("Sending a request " + var7.getMessage());
//            return false;
//        }
//    }
//}
