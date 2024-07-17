package com.armagantas.cqrs.core.infrastructure;

import com.armagantas.cqrs.core.commands.BaseCommand;
import com.armagantas.cqrs.core.commands.CommandHandlerMethod;

public interface CommandDispatcher {
    <T extends BaseCommand> void registerHandler(Class<T> type, CommandHandlerMethod<T> handler);
    void send(BaseCommand command);
}
