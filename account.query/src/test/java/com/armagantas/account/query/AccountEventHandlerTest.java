package com.armagantas.account.query;

import com.armagantas.account.common.events.AccountClosedEvent;
import com.armagantas.account.common.events.AccountOpenedEvent;
import com.armagantas.account.common.events.FundsDepositedEvent;
import com.armagantas.account.common.events.FundsWithdrawnEvent;
import com.armagantas.account.query.domain.AccountRepository;
import com.armagantas.account.query.domain.BankAccount;
import com.armagantas.account.query.infrastructure.handlers.AccountEventHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.Optional;

import static com.armagantas.account.common.dto.AccountType.SAVINGS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountEventHandlerTest {
    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountEventHandler accountEventHandler;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void onAccountOpenedEvent_shouldSaveAccount() {
        AccountOpenedEvent event = AccountOpenedEvent.builder()
                .id("123")
                .accountHolder("John Doe")
                .createdDate(new Date())
                .accountType(SAVINGS)
                .openingBalance(1000.0)
                .build();

        accountEventHandler.on(event);
        verify(accountRepository, times(1)).save(any(BankAccount.class));
    }

    @Test
    void onFundsDepositedEvent_shouldUpdateBalance() {
        FundsDepositedEvent event = FundsDepositedEvent.builder()
                .id("123")
                .amount(500.0)
                .build();

        BankAccount account = BankAccount.builder()
                .id("123")
                .accountHolder("John Doe")
                .balance(1000.0)
                .build();

        when(accountRepository.findById(event.getId())).thenReturn(Optional.of(account));
        accountEventHandler.on(event);
        verify(accountRepository, times(1)).save(account);
        assertEquals(1500.0, account.getBalance(), "The balance updated successfully.");

    }

    @Test
    void onFundsWithdrawnEvent_shouldUpdateBalance() {
        FundsWithdrawnEvent event = FundsWithdrawnEvent.builder()
                .id("123")
                .amount(500.0)
                .build();

        BankAccount account = BankAccount.builder()
                .id("123")
                .accountHolder("John Doe")
                .balance(1000.0)
                .build();

        when(accountRepository.findById(event.getId())).thenReturn(Optional.of(account));
        accountEventHandler.on(event);
        verify(accountRepository, times(1)).save(account);
        assertEquals(500.0, account.getBalance(), "The balance updated successfully.");
    }

    @Test
    void onAccountClosedEvent_shouldDeleteAccount() {
        AccountClosedEvent event = AccountClosedEvent.builder()
                .id("123")
                .build();

        accountEventHandler.on(event);

        verify(accountRepository, times(1)).deleteById(event.getId());
    }

}
