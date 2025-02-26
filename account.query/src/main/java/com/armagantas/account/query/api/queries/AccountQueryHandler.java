package com.armagantas.account.query.api.queries;

import com.armagantas.account.query.api.dto.EqualityType;
import com.armagantas.account.query.domain.AccountRepository;
import com.armagantas.account.query.domain.BankAccount;
import com.armagantas.cqrs.core.domain.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountQueryHandler implements QueryHandler {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public List<BaseEntity> handle(FindAllAccountsQuery query) {
        Iterable<BankAccount> accounts = accountRepository.findAll();

        List<BaseEntity> bankAccounts = new ArrayList<>();
        accounts.forEach(bankAccounts::add);
        return bankAccounts;
    }

    @Override
    public List<BaseEntity> handle(FindAccountByIdQuery query) {
        var bankAccount = accountRepository.findById(query.getId());
        if(bankAccount.isEmpty()) {
            return null;
        }

        List<BaseEntity> bankAccountList = new ArrayList<>();
        bankAccountList.add(bankAccount.get());
        return bankAccountList;
    }

    @Override
    public List<BaseEntity> handle(FindAccountByHolderQuery query) {
        var bankAccount = accountRepository.findByAccountHolder(query.getAccountHolder());
        if(bankAccount.isEmpty()) {
            return null;
        }

        List<BaseEntity> bankAccountList = new ArrayList<>();
        bankAccountList.add(bankAccount.get());
        return bankAccountList;
    }

    @Override
    public List<BaseEntity> handle(FindAccountWithBalanceQuery query) {
        List<BaseEntity> bankAccountsList =
                query.getEqualityType() ==
                        EqualityType.GREATER_THAN ?
                            accountRepository.findByBalanceGreaterThan(query.getBalance()) :
                                accountRepository.findByBalanceLessThan(query.getBalance());
        return bankAccountsList;
    }
}
