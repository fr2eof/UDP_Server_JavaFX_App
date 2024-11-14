package com.practice.command;

import com.practice.Main;
import com.practice.database.DataBaseManager;
import com.practice.dto.CommandResponseDto;
import com.practice.dto.WorkerDto;
import com.practice.element.Worker;
import com.practice.exception.ScriptReadingException;
import com.practice.exception.WrongAmountOfArgumentsException;
import com.practice.transformer.Transformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class UpdateIdCommand extends AbstractCommand {
    private static final Logger log = LoggerFactory.getLogger(UpdateIdCommand.class);

    public UpdateIdCommand() {
        super("update", "update the value of a collection element whose id is equal to a given one");
    }

    public boolean execute(Object[] args) {
        try {
            WorkerDto workerDto = (WorkerDto) args[0];
            Worker worker = Transformer.WorkerDtoToWorker(workerDto);
            worker.setCreationDate(String.valueOf(LocalDate.now()));
            worker.setStartDate(ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX")));
            Main.queueToResponse.add(new CommandResponseDto(DataBaseManager.update(worker,(Integer) args[1])));
            return true;
        } catch (WrongAmountOfArgumentsException var8) {
            log.error("No arguments in " + this.getName());
            return false;
        } catch (ScriptReadingException | NullPointerException var9) {
            log.error("Reading from script " + var9.getMessage());
            return false;
        } catch (Exception var10) {
            log.error("Sending a response " + var10.getMessage());
            return false;
        }
    }
}
