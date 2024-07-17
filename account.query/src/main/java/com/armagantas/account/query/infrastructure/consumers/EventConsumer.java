package com.armagantas.account.query.infrastructure.consumers;

import com.armagantas.account.common.events.AccountClosedEvent;
import com.armagantas.account.common.events.AccountOpenedEvent;
import com.armagantas.account.common.events.FundsDepositedEvent;
import com.armagantas.account.common.events.FundsWithdrawnEvent;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;

public interface EventConsumer {
    void consume(@Payload AccountOpenedEvent accountOpenedEvent, Acknowledgment acknowledgment);
    void consume(@Payload FundsDepositedEvent fundsDepositedEvent, Acknowledgment acknowledgment);
    void consume(@Payload FundsWithdrawnEvent fundsWithdrawnEvent, Acknowledgment acknowledgment);
    void consume(@Payload AccountClosedEvent accountClosedEvent, Acknowledgment acknowledgment);
}
