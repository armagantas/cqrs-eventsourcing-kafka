package com.armagantas.account.query.infrastructure.handlers;

import com.armagantas.account.common.events.AccountClosedEvent;
import com.armagantas.account.common.events.AccountOpenedEvent;
import com.armagantas.account.common.events.FundsDepositedEvent;
import com.armagantas.account.common.events.FundsWithdrawnEvent;

public interface EventHandler {
    void on(AccountOpenedEvent event);
    void on(FundsWithdrawnEvent event);
    void on(AccountClosedEvent event);
    void on(FundsDepositedEvent event);
}
