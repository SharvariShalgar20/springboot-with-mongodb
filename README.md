# JournalApp - Spring Boot Architecture

A full-stack, secure Journaling Application built using **Spring Boot**, **MongoDB**, and **Redis**, featuring an integrated user management system, asynchronous email notifications via **Apache Kafka**, interactive API documentation with **Swagger**, and seamless containerization using **Docker**. It also includes a modern, responsive web user interface.

---

## What the Project Does

This application serves as a personal journaling platform where multiple users can register, authenticate securely, and manage their own private journal entries. 

*   **User Management & Security:** Secure registration and login flow using state-of-the-art authentication.
*   **Journal Entries (CRUD):** Users can create, read, update, and delete personalized journal logs.
*   **Asynchronous Processing:** Automatically triggers background events (like welcome notifications) upon user registration using an event-driven pattern.
*   **Fast Caching & Session Management:** Utilizes an in-memory data store to cache frequent database lookups or session tracking for optimal performance.
*   **API Exploration:** Developers can interact with and test all backend endpoints via an interactive UI.

---

## Architecture & Tech Stack

### Backend
*   **Framework:** Spring Boot (Java)
*   **Database:** MongoDB (NoSQL database for storing unstructured journal documents and user profiles)
*   **Caching & Session Storage:** Redis (In-memory data structure store)
*   **Security:** Spring Security with JSON Web Tokens (JWT) for stateless authentication.
*   **Messaging Broker:** Apache Kafka (Asynchronous user registration event processing)
*   **API Documentation:** Springdoc OpenAPI / Swagger UI

### Frontend
*   **Framework:** Vite-powered modern UI layer package for a lightweight, fast web interface.

### DevOps & Containerization
*   **Containerization:** Docker & Docker Compose

---

## Project Document Details & Structure

Your project repository includes dedicated configuration and documentation blueprints to ensure easy setup and thorough testing:

### 1. Containerization (`docker-compose.yml`)
The project is completely containerized, allowing you to spin up the entire ecosystem with a single command. It coordinates:
*   **MongoDB:** Stores user data and journal collections locally within a reliable network alias.
*   **Apache Kafka & Zookeeper:** Manages event streams and message topics for the asynchronous consumer-producer registration flow.
*   **Redis:** Acts as an ultra-fast data cache layer to support high-throughput operations, stores frequently used weather responses with name of city.

### 2. Testing Blueprint (`TESTING.md`)
A dedicated test-case document outlining QA methodologies, API compliance checks, and verifying edge-case scenarios for endpoint validation.

### 3. Postman Collection (`/postman`)
A centralized directory containing exported JSON formats of all API HTTP requests (GET, POST, PUT, DELETE). This allows developers to instantly import routes into Postman for rapid automated or manual API testing.

---

## Security Workflow

1. **Public Routes:** Anyone can access the Swagger documentation or hit the public endpoint (`/public`) to register or login.
2. **Authentication:** Logging in issues a secure **JSON Web Token (JWT)**.
3. **Protected Routes:** All user profile and journal management endpoints are protected by a custom `JwtFilter`. Subsequent requests must pass the token in the `Authorization: Bearer <token>` header.

---

## Asynchronous Event Flow (Kafka)

When a new user signs up:
1. `UserService` triggers a producer event (`UserRegisterProducer.java`).
2. A payload mapping (`UserRegisteredEvent.java`) is serialized into JSON via a configured `ObjectMapper` Bean.
3. The message is pushed to a Kafka topic.
4. `UserRegisterConsumer.java` asynchronously listens to the topic to process background tasks (e.g., preparing automated welcome alerts) without blocking the user's HTTP request thread.

---

## Getting Started

### Prerequisites
*   Java 17 or higher
*   Node.js & npm (for the frontend UI)
*   Docker & Docker Compose

### Running the App with Docker

1. Clone the repository and build the jar file.
2. Spin up the infrastructure environment:
   ```bash
   docker-compose up --build
3. Access the interactive Swagger API documentation at the whitelisted security endpoint to inspect parameters and try out endpoints live.
