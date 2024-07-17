package com.armagantas.account.cmd.api.commands;

import com.armagantas.cqrs.core.commands.BaseCommand;
import lombok.Data;

@Data
public class DepositFundsCommand extends BaseCommand {
    private double amount;

    public DepositFundsCommand(String accountId, double amount) {
        super(accountId);
        this.amount = amount;
    }
}
