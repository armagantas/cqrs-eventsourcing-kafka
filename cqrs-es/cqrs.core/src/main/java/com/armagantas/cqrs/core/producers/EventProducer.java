package com.armagantas.cqrs.core.producers;

import com.armagantas.cqrs.core.events.BaseEvent;

public interface EventProducer {
    void produce(String topic, BaseEvent event);
}
