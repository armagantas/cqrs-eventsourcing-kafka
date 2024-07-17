package com.armagantas.account.query.api.controllers;

import com.armagantas.account.query.api.dto.AccountQueryResponse;
import com.armagantas.account.query.api.dto.EqualityType;
import com.armagantas.account.query.api.queries.FindAccountByHolderQuery;
import com.armagantas.account.query.api.queries.FindAccountByIdQuery;
import com.armagantas.account.query.api.queries.FindAccountWithBalanceQuery;
import com.armagantas.account.query.api.queries.FindAllAccountsQuery;
import com.armagantas.account.query.domain.BankAccount;
import com.armagantas.cqrs.core.infrastructure.QueryDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/api/v1/bankAccountQuery")
public class AccountQueryController {
    private final Logger logger = Logger.getLogger(AccountQueryController.class.getName());

    @Autowired
    private QueryDispatcher queryDispatcher;

    @GetMapping(path = "/")
    public ResponseEntity<AccountQueryResponse> getAllAccounts() {
        try {
            List<BankAccount> accounts = queryDispatcher.send(new FindAllAccountsQuery());
            if(accounts == null || accounts.size() == 0) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            var response = AccountQueryResponse.builder()
                    .accounts(accounts)
                    .message(MessageFormat.format("Accounts : {0}", accounts.size()))
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            var safeError = "Failed to get accounts";
            logger.log(Level.SEVERE, safeError, e);
            return new ResponseEntity<>(new AccountQueryResponse(safeError), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/byId/{id}")
    public ResponseEntity<AccountQueryResponse> getAccountById(@PathVariable(value = "id") String id) {
        try {
            List<BankAccount> accounts = queryDispatcher.send(new FindAccountByIdQuery(id));
            if(accounts == null || accounts.size() == 0) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            var response = AccountQueryResponse.builder()
                    .accounts(accounts)
                    .message("Succesfully returned.")
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            var safeError = "Failed to get account";
            logger.log(Level.SEVERE, safeError, e);
            return new ResponseEntity<>(new AccountQueryResponse(safeError), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/byHolder/{accountHolder}")
    public ResponseEntity<AccountQueryResponse> getAccountByHolder(@PathVariable(value = "accountHolder") String accountHolder) {
        try {
            List<BankAccount> accounts = queryDispatcher.send(new FindAccountByHolderQuery(accountHolder));
            if(accounts == null || accounts.size() == 0) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            var response = AccountQueryResponse.builder()
                    .accounts(accounts)
                    .message("Succesfully returned.")
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            var safeError = "Failed to get holder account.";
            logger.log(Level.SEVERE, safeError, e);
            return new ResponseEntity<>(new AccountQueryResponse(safeError), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/withBalance/{equalityType}/{balance}")
    public ResponseEntity<AccountQueryResponse> getAccountWithBalance(@PathVariable(value = "equalityType") EqualityType equalityType,
                                                                      @PathVariable(value = "balance") double balance) {
        try {
            List<BankAccount> accounts = queryDispatcher.send(new FindAccountWithBalanceQuery(equalityType, balance));
            if(accounts == null || accounts.size() == 0) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            var response = AccountQueryResponse.builder()
                    .accounts(accounts)
                    .message(MessageFormat.format("Accounts : {0}", accounts.size()))
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            var safeError = "Failed to get holder account.";
            logger.log(Level.SEVERE, safeError, e);
            return new ResponseEntity<>(new AccountQueryResponse(safeError), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
