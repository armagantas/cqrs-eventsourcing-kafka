# Bank Account CQRS and Event Sourcing with Kafka Application

This project is a sample implementation of CQRS (Command Query Responsible Segregation) and Event Sourcing architecture using Spring Boot, Kafka , and MongoDB/PostgreSQL. It provides functionalities for creating and closing bank account also depositing and withdrawing operations.

## Contents
- [Properties]
- [Architecture]
- [Setup]
- [Usage]

## Features
- Create a bank account
- Deposit funds into an account
- Withdraw funds from an account
- Close an account
- Query account details
- Monitor the all events with MongoDB


## Architecture
The application follows the CQRS and Event Sourcing patterns:
- **CQRS:** Separates the write side from the read side.
- **Event Sourcing:** Stores the state of the application as a sequence of events.
  

### Components
- **Command Side:** Handles account creation, updates, and deletions.
- **Query Side:** Handles querying of account details.
- **Event Store:** Stores events in MongoDB and publishes them to Kafka.
- **Event Consumers:** Listens to Kafka topics and updates the read models in PostgreSQL.


## Setup

### Clone the Repository
```bash
git clone https://github.com/armagantas/cqrs-eventsourcing-kafka
cd cqrs-eventsourcing-kafka
```

### Start dependencies with Docker
```bash
docker-compose up -d
```

### Build the application and Run.

## API Endpoints and Usage
### Create Account 
```http
POST /api/v1/openBankAccount
Content-Type: application/json
{
    "accountHolder": "John Doe",
    "accountType": "CURRENT",
    "openingBalance": 1500.0
}
```

### Deposit Funds
```http
PUT /api/v1/depositFunds/{USERID}
Content-Type: application/json
{
    "amount": 150.0
}
```

### Withdraw Funds
```http
PUT /api/v1/withdrawFunds/{USERID}
Content-Type: application/json
{
    "amount": 150.0
}
```

### Close Account
```http
POST /api/v1/closeBankAccount/{USERID}
Content-Type: application/json
```

### Get all accounts
```http
GET /api/v1/bankAccountQuery/
```

### Get account with an id
```http
GET /api/v1/bankAccountQuery/byId/{USERID}
```

### Get account with a username
```http
GET /api/v1/bankAccountQuery/byHolder/{accountHolder}
```
