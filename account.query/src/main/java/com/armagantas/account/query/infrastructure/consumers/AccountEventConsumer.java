package com.armagantas.account.query.infrastructure.consumers;

import com.armagantas.account.common.events.AccountClosedEvent;
import com.armagantas.account.common.events.AccountOpenedEvent;
import com.armagantas.account.common.events.FundsDepositedEvent;
import com.armagantas.account.common.events.FundsWithdrawnEvent;
import com.armagantas.account.query.infrastructure.handlers.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
public class AccountEventConsumer implements EventConsumer {

    @Autowired
    private EventHandler eventHandler;

    @KafkaListener(topics = "AccountOpenedEvent", groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consume(AccountOpenedEvent accountOpenedEvent, Acknowledgment acknowledgment) {
        this.eventHandler.on(accountOpenedEvent);
        acknowledgment.acknowledge();
    }

    @KafkaListener(topics = "FundsDepositedEvent", groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consume(FundsDepositedEvent fundsDepositedEvent, Acknowledgment acknowledgment) {
        this.eventHandler.on(fundsDepositedEvent);
        acknowledgment.acknowledge();
    }

    @KafkaListener(topics = "FundsWithdrawnEvent", groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consume(FundsWithdrawnEvent fundsWithdrawnEvent, Acknowledgment acknowledgment) {
        this.eventHandler.on(fundsWithdrawnEvent);
        acknowledgment.acknowledge();
    }

    @KafkaListener(topics = "AccountClosedEvent", groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consume(AccountClosedEvent accountClosedEvent, Acknowledgment acknowledgment) {
        this.eventHandler.on(accountClosedEvent);
        acknowledgment.acknowledge();
    }
}
