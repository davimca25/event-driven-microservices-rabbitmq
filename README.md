# Event-Driven Microservices with Spring Boot and RabbitMQ

A robust implementation of an asynchronous, event-driven microservices architecture using **Spring Boot**, **RabbitMQ**, and **PostgreSQL**, structured as a Monorepo.

## Architecture & Tech Stack

* **Java 25** & **Spring Boot 4.1.0** (Spring Data JPA, Spring AMQP)
* **RabbitMQ** (Message Broker for asynchronous communication via Exchanges, Queues, and Bindings)
* **PostgreSQL** (Isolated relational databases for each microservice)
* **Docker & Docker Compose** (Local infrastructure orchestration)

---

##  Microservices Overview

1. **`order` (Producer)**
   * **Role:** Exposes a REST API to create and list orders.
   * **Behavior:** Saves the order data into its isolated PostgreSQL database (`order-ms`) and publishes an asynchronous event (`order.created`) to RabbitMQ using a JSON message converter.

2. **`processing` (Consumer)**
   * **Role:** Background worker that handles incoming orders.
   * **Behavior:** Listens to the RabbitMQ queue (`order.processing.queue`) via `@RabbitListener`, automatically deserializes the incoming JSON event into a local DTO, and processes the business logic.

---

##  How to Run Locally

### Prerequisites
* Ensure you have **Docker** and **Docker Compose** installed.
* Ensure you have **Java 25** (or compatible JDK) installed.

### 1. Start Infrastructure (Database & RabbitMQ)
Open your terminal in the root directory where the `docker-compose.yml` file is located and run:
```bash
docker compose up -d
```

### 2. Run the Microservices
You can run both microservices directly from your favorite IDE (like IntelliJ IDEA):

* Run OrderApplication (Starts on port 8080).

* Run ProcessingApplication (Starts on port 8082).

---

### Testing the Integration
You can use Insomnia, Postman, or curl to send a POST request to create an order:

* Endpoint: POST http://localhost:8080/orders

* Body (JSON):

```JSON
{
  "name": "Order Test",
  "items": [
    {
      "name": "Product A",
      "quantity": 2
    }
  ]
}
```

Expected Result:

* The order is saved in the order-ms database.

* An event is dispatched to RabbitMQ via order.exchange.

* The processing microservice captures the event and prints the processed order details directly in its console log.

---

### Project Structure (Monorepo)
```Plaintext
├── order/                      # Producer microservice (REST API + DB)
├── processing/                 # Consumer microservice (Worker + DB)
├── docker-compose.yml          # Infrastructure setup (PostgreSQL instances & RabbitMQ)
└── README.md                   # Project documentation
