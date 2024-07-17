package com.armagantas.account.cmd.api.commands;

import com.armagantas.cqrs.core.commands.BaseCommand;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class WithdrawFundsCommand extends BaseCommand {
    private double amount;

    public WithdrawFundsCommand(String account, double amount) {
        super(account);
        this.amount = amount;
    }
}
