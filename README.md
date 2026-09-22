# JPMorgan Chase Midas – Transaction Processing Service

This project was completed as part of the **JPMorgan Chase Software Engineering Job Simulation on Forage**.

## About the Project

Midas is a Spring Boot-based financial transaction processing service. It receives transaction messages through Kafka, validates and processes transactions, applies incentives through an external REST API, and updates user balances in an H2 database.

## What I Worked On

- Integrated **Apache Kafka** with the Spring Boot application to consume transaction messages.
- Implemented **transaction consumption and deserialization** using Spring Kafka.
- Implemented **transaction validation and processing** logic.
- Used **Spring Data JPA** for database persistence.
- Used an **H2 SQL database** to store users, transactions, and balances.
- Integrated an external **Incentive REST API** using Spring's `RestTemplate`.
- Processed incentive responses as part of the transaction workflow.
- Implemented a **REST API** for querying user balances.
- Used debugging and testing to identify and resolve issues during development.

## Tech Stack

- **Java**
- **Spring Boot**
- **Spring Kafka**
- **Apache Kafka**
- **Spring Data JPA**
- **H2 Database**
- **REST API**
- **RestTemplate**
- **Maven**

## Architecture

Transaction Data  
↓  
Kafka  
↓  
`TransactionConsumer`  
↓  
`TransactionProcessor`  
↓  
`IncentivesClient` → External Incentive API  
↓  
`DatabaseConduit`  
↓  
H2 Database  

## Main Components

### `TransactionConsumer`
Consumes transaction messages from a Kafka topic and converts them into Java objects.

### `TransactionProcessor`
Validates and processes transactions and handles balance updates.

### `IncentivesClient`
Uses `RestTemplate` to communicate with the external Incentive API.

### `DatabaseConduit`
Handles database persistence using Spring Data JPA.

### `BalanceController`
Provides a REST endpoint for querying user balances.

## Certification

Completed the **JPMorgan Chase Software Engineering Job Simulation** on **Forage**.

The simulation provided practical experience with:

- Kafka-based message processing
- Spring Boot development
- Database persistence
- REST API integration
- Transaction processing
- Automated testing and debugging

## Running the Project

Run the application using:

```bash
./mvnw spring-boot:run
