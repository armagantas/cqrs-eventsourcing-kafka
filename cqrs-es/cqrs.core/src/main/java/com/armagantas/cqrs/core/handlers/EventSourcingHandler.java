package com.armagantas.cqrs.core.handlers;

import com.armagantas.cqrs.core.domain.AggregateRoot;

public interface EventSourcingHandler<T> {
    void save(AggregateRoot aggregateRoot);
    T getById(String id);
}
