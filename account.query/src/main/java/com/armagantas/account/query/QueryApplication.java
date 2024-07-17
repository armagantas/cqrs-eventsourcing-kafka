package com.armagantas.account.query;

import com.armagantas.account.query.api.queries.*;
import com.armagantas.cqrs.core.infrastructure.QueryDispatcher;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QueryApplication {

	@Autowired
	private QueryHandler queryHandler;

	@Autowired
	private QueryDispatcher queryDispatcher;

	public static void main(String[] args) {
		SpringApplication.run(QueryApplication.class, args);
	}

	@PostConstruct
	public void registeredHandlers() {
		queryDispatcher.registerHandler(FindAllAccountsQuery.class, queryHandler :: handle);
		queryDispatcher.registerHandler(FindAccountWithBalanceQuery.class, queryHandler :: handle);
		queryDispatcher.registerHandler(FindAccountByHolderQuery.class, queryHandler :: handle);
		queryDispatcher.registerHandler(FindAccountByIdQuery.class, queryHandler :: handle);
	}

}
