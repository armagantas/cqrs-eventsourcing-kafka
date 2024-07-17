package com.armagantas.account.query.api.dto;

import com.armagantas.account.common.dto.BaseResponse;
import com.armagantas.account.query.domain.BankAccount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class AccountQueryResponse extends BaseResponse {
    private List<BankAccount> accounts;

    public AccountQueryResponse(String message) {
        super(message);
    }
}
