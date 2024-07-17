package com.armagantas.cqrs.core.infrastructure;

import com.armagantas.cqrs.core.domain.BaseEntity;
import com.armagantas.cqrs.core.queries.BaseQuery;
import com.armagantas.cqrs.core.queries.QueryHandlerMethod;

import java.util.List;

public interface QueryDispatcher {
    <T extends BaseQuery> void registerHandler(Class<T> type, QueryHandlerMethod<T> handler);
    <U extends BaseEntity> List<U> send(BaseQuery query);
}
