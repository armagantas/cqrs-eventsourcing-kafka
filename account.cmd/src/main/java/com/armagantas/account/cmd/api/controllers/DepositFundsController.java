package com.armagantas.account.cmd.api.controllers;

import com.armagantas.account.cmd.api.commands.DepositFundsCommand;
import com.armagantas.account.cmd.api.dto.OpenAccountResponse;
import com.armagantas.account.common.dto.BaseResponse;
import com.armagantas.cqrs.core.exceptions.AggregateNotFoundException;
import com.armagantas.cqrs.core.infrastructure.CommandDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/depositFunds")
public class DepositFundsController {
    private final Logger logger = Logger.getLogger(DepositFundsController.class.getName());

    @Autowired
    private CommandDispatcher commandDispatcher;

    @PutMapping(path = "/{id}")
    public ResponseEntity<BaseResponse> depositFunds(@PathVariable(value = "id") String id,
                                                     @RequestBody DepositFundsCommand command){
        try {
            command.setId(id);
            commandDispatcher.send(command);
            return new ResponseEntity<>(new BaseResponse("Deposit funds request done."), HttpStatus.OK);
        } catch (IllegalStateException | AggregateNotFoundException e)  {
            logger.log(Level.WARNING, MessageFormat.format("Bad Request {0}", e.getMessage()), e.toString());
            return new ResponseEntity<>(new BaseResponse("Bad Request"), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            var safeMessage = MessageFormat.format("Error!!! - {0}", e.toString());
            logger.log(Level.SEVERE, safeMessage, e);
            return new ResponseEntity<>(new OpenAccountResponse(safeMessage, id), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
