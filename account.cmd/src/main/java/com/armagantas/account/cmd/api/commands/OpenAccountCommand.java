package com.armagantas.account.cmd.api.commands;

import com.armagantas.account.common.dto.AccountType;
import com.armagantas.cqrs.core.commands.BaseCommand;
import lombok.Data;

@Data
public class OpenAccountCommand extends BaseCommand {
    private String accountHolder;
    private AccountType accountType;
    private double openingBalance;

    public OpenAccountCommand(String accountId,String accountHolder, AccountType accountType, double openingBalance) {
        super(accountId);
        this.accountHolder = accountHolder;
        this.accountType = accountType;
        this.openingBalance = openingBalance;
    }
}
