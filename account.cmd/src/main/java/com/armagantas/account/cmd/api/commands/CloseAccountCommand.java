package com.armagantas.account.cmd.api.commands;

import com.armagantas.cqrs.core.commands.BaseCommand;
import lombok.Data;

@Data
public class CloseAccountCommand  extends BaseCommand {
    public CloseAccountCommand(String id) {
        super(id);
    }
}

