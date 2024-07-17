package com.armagantas.account.query.infrastructure;

import com.armagantas.cqrs.core.domain.BaseEntity;
import com.armagantas.cqrs.core.infrastructure.QueryDispatcher;
import com.armagantas.cqrs.core.queries.BaseQuery;
import com.armagantas.cqrs.core.queries.QueryHandlerMethod;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AccountQueryDispatcher implements QueryDispatcher {
    private final Map<Class<? extends BaseQuery>, List<QueryHandlerMethod>> queryHandlerMethods = new HashMap<>();

    @Override
    public <T extends BaseQuery> void registerHandler(Class<T> type, QueryHandlerMethod<T> handler) {
        var handlers = queryHandlerMethods.computeIfAbsent(type, k -> new LinkedList<>());
        handlers.add(handler);
    }

    @Override
    public <U extends BaseEntity> List<U> send(BaseQuery query) {
        var handlers = queryHandlerMethods.get(query.getClass());
        if (handlers == null || handlers.size() <= 0) {
            throw new RuntimeException("No query handler was registered");
        }
        if(handlers.size() > 1) {
            throw new RuntimeException("More than one query handler was registered");
        }
        return handlers.get(0).handle(query);
    }
}
