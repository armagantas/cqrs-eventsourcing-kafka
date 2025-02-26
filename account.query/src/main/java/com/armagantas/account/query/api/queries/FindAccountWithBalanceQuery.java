package com.armagantas.account.query.api.queries;

import com.armagantas.account.query.api.dto.EqualityType;
import com.armagantas.cqrs.core.queries.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindAccountWithBalanceQuery extends BaseQuery {
    private EqualityType equalityType;
    private double balance;
}
