package com.armagantas.account.query;

import com.armagantas.account.cmd.api.commands.*;
import com.armagantas.account.cmd.domain.AccountAggregate;
import com.armagantas.account.common.dto.AccountType;
import com.armagantas.cqrs.core.handlers.EventSourcingHandler;
import org.hibernate.event.spi.EventSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static com.armagantas.account.common.dto.AccountType.SAVINGS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AccountCommandHandlerTest {
    @Mock
    private EventSourcingHandler<AccountAggregate> eventSourcingHandler;

    @InjectMocks
    private AccountCommandHandler accountCommandHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void handleOpenAccountCommand_shouldInvokeSave() {
        OpenAccountCommand command = new OpenAccountCommand("123","Armagan123",SAVINGS, 55.0);

        accountCommandHandler.handle(command);

        verify(eventSourcingHandler, times(1)).save(any(AccountAggregate.class));

    }

    @Test
    void handleDepositFundsCommand_shouldInvokeSave() {
        DepositFundsCommand command = new DepositFundsCommand("db53386b-a466-443e-87b0-ef0a3e23421a",10.0);
        AccountAggregate aggregate = mock(AccountAggregate.class);

        when(eventSourcingHandler.getById(command.getId())).thenReturn(aggregate);

        accountCommandHandler.handle(command);

        verify(aggregate, times(1)).depositFunds(command.getAmount());
        verify(eventSourcingHandler, times(1)).save(aggregate);
    }

    @Test
    void handleWithdrawFundsCommand_shouldInvokeSave() {
        WithdrawFundsCommand command = new WithdrawFundsCommand("42e0adfa-a613-4721-8f61-e84bafa971e4",200.0);
        AccountAggregate aggregate = mock(AccountAggregate.class);

        when(eventSourcingHandler.getById(command.getId())).thenReturn(aggregate);
        when(aggregate.getBalance()).thenReturn(1000.0);

        accountCommandHandler.handle(command);

        verify(aggregate, times(1)).withdrawFunds(command.getAmount());
        verify(eventSourcingHandler, times(1)).save(aggregate);

    }

    @Test
    void handleCloseAccountCommand_shouldInvokeSave() {
        CloseAccountCommand command = new CloseAccountCommand("db53386b-a466-443e-87b0-ef0a3e23421a");
        AccountAggregate aggregate = mock(AccountAggregate.class);
        when(eventSourcingHandler.getById(command.getId())).thenReturn(aggregate);

        accountCommandHandler.handle(command);

        verify(aggregate, times(1)).closeAccount();
        verify(eventSourcingHandler, times(1)).save(aggregate);
    }
}
