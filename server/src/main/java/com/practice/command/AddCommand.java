package com.practice.command;

import com.practice.Main;
import com.practice.database.DataBaseManager;
import com.practice.dto.CommandResponseDto;
import com.practice.transformer.Transformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.practice.dto.WorkerDto;
import com.practice.element.Worker;
import com.practice.exception.ScriptReadingException;
import com.practice.exception.WrongAmountOfArgumentsException;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class AddCommand extends AbstractCommand {
    private static final Logger log = LoggerFactory.getLogger(AddCommand.class);

    public AddCommand() {
        super("add", "add a new element to a collection");
    }

    public boolean execute(Object[] args) {
        try {
            CommandResponseDto commandResponseDto;
            WorkerDto workerDto = (WorkerDto) args[0];
            Worker worker = Transformer.WorkerDtoToWorker(workerDto);
            worker.setCreationDate(String.valueOf(LocalDate.now()));
            worker.setStartDate(ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX")));
            if (DataBaseManager.add(worker)) {
                commandResponseDto = new CommandResponseDto("Worker has been added successfully");
            } else {
                commandResponseDto = new CommandResponseDto("Worker has NOT been added successfully");
            }
            Main.queueToResponse.add(commandResponseDto);
            return true;
        } catch (WrongAmountOfArgumentsException var5) {
            log.error("No arguments in " + this.getName());
            return false;
        } catch (ScriptReadingException | NullPointerException var6) {
            log.error("Reading from script "+var6.getMessage());
            return false;
        } catch (Exception var7) {
            log.error("Sending a response "+var7.getMessage());
            return false;
        }
    }

    @Override
    public String getDescription() {
        return super.getDescription();
    }
}
