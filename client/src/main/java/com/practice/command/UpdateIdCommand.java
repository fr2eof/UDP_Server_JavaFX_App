package com.practice.command;

import com.practice.dto.CommandRequestDto;
import com.practice.dto.WorkerDto;
import com.practice.element.*;
import com.practice.enumeration.EColor;
import com.practice.enumeration.HColor;
import com.practice.enumeration.Position;
import com.practice.enumeration.Status;
import com.practice.exception.WrongAmountOfArgumentsException;
import com.practice.network.RequestSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.practice.transformer.Transformer;

public class UpdateIdCommand extends AbstractCommand {
    private static final Logger log = LoggerFactory.getLogger(UpdateIdCommand.class);

    public UpdateIdCommand() {
        super("update");
    }

    public boolean execute(Object[] args) {
        try {
            if (args.length != 3) {
                throw new WrongAmountOfArgumentsException();
            }
            Object[] arguments = (Object[]) args[2];
            CommandRequestDto crd = (CommandRequestDto) args[1];
            RequestSender requestSender = (RequestSender) args[0];
            Worker worker = new Worker();
            worker.setUser(null);
            worker.setName((String) arguments[0]);
            worker.setCoordinates(new Coordinates((Float) arguments[1],(Integer) arguments[2]));
            worker.setSalary((int) arguments[3]);
            worker.setPosition(Position.valueOf((String) arguments[4]));
            worker.setStatus(Status.valueOf((String) arguments[5]));
            worker.setPerson(new Person((String) arguments[6],
                    EColor.valueOf((String) arguments[7]),
                    HColor.valueOf((String) arguments[8]),
                    new Location((long) arguments[9], (long) arguments[10], (String) arguments[11])));
            worker.setLocation(worker.getPerson().getLocation());
            WorkerDto workerDto = Transformer.WorkerToWorkerDto(worker);
            crd.setCommandName("update");
            crd.setCommandArgs(new Object[]{workerDto, Integer.valueOf((String) arguments[0])});
            requestSender.sendRequest(Transformer.writeObject(crd));
            return true;
        } catch (WrongAmountOfArgumentsException var6) {
            log.error("One argument in " + this.getName());
            return false;
        } catch (NumberFormatException var7) {
            log.error("Not Integer in argument " + this.getName());
            return false;
        } catch (Exception var8) {
            log.error("Sending a request " + var8.getMessage());
            return false;
        }
    }
}
