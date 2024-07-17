package com.armagantas.account.cmd.infrastructure;

import com.armagantas.cqrs.core.commands.BaseCommand;
import com.armagantas.cqrs.core.commands.CommandHandlerMethod;
import com.armagantas.cqrs.core.infrastructure.CommandDispatcher;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AccountCommandDispatcher implements CommandDispatcher {
    private final Map<Class<? extends BaseCommand>, List<CommandHandlerMethod>> routes = new HashMap<>();

    @Override
    public <T extends BaseCommand> void registerHandler(Class<T> type, CommandHandlerMethod<T> handler) {
        var handlers = routes.computeIfAbsent(type, k -> new LinkedList<>());
        handlers.add(handler);
    }

    @Override
    public void send(BaseCommand command) {
        var handlers = routes.get(command.getClass());
        if (handlers == null || handlers.size() == 0) {
            throw new RuntimeException("No command handler was registered.");
        }
        if(handlers.size() > 1) {
            throw new RuntimeException("One command cannot be acceptable from more than one handler.");
        }
        handlers.get(0).handle(command);
    }
}
