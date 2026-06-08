# JournalApp (Spring Boot + MongoDB)

A production-ready Spring Boot REST API application designed as a secure journaling platform. The application leverages MongoDB for data persistence and incorporates enterprise-grade features such as authentication, role-based access control, Redis caching, automated email scheduling, external API integrations, transactional data handling, and comprehensive logging.

---

##  Features

###  Authentication & Authorization
- Secure user authentication using Spring Security.
- Role-Based Access Control (RBAC) with separate Admin and Public endpoints.
- Protected APIs ensuring users can only access and modify their own journal data.

###  Journal Management
- Create, Read, Update, and Delete (CRUD) journal entries.
- Associate journal entries with user accounts using MongoDB DBRef relationships.
- Transactional operations to maintain data consistency.

###  Weather API Integration
- Integrated third-party Weather API.
- Fetch real-time weather information for users.
- Weather insights available within the journaling ecosystem.

###  Redis Caching
- Implemented Redis caching for weather API responses.
- Reduced API latency and minimized external API requests.
- Improved scalability and performance.

###  Automated Email Scheduler
- Integrated Spring Mail support.
- Sends scheduled reminders and notifications to users.
- Background task execution using Spring Scheduling.

###  Logging & Monitoring
- Integrated SLF4J and Logback logging framework.
- Layer-wise logging across Controllers, Services, and Security components.
- Simplified debugging and production monitoring.

###  Database Transactions
- Applied `@Transactional` for multi-document operations.
- Ensures atomicity when creating journal entries and linking them to users.
- Prevents inconsistent data states during failures.

---

##  Project Structure

```text
src
├── main
│   ├── java
│   │   ├── controller
│   │   ├── service
│   │   ├── repository
│   │   ├── model
│   │   ├── config
│   │   └── scheduler
│   └── resources
│       └── application.properties
│
└── test
    ├── UserServiceTest
    └── JournalEntryControllerTest
```

---

##  Getting Started

### Prerequisites

- Java 17+
- Maven
- MongoDB
- Redis

### Clone Repository

```bash
git clone https://github.com/your-username/journal-app.git
cd journal-app
```

### Configure Application

Update `application.properties`:

```properties
spring.data.mongodb.uri=your_mongodb_connection_string

spring.redis.host=localhost
spring.redis.port=6379

spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your_email
spring.mail.password=your_app_password
```

### Build Project

```bash
mvn clean install
```

### Run Application

```bash
mvn spring-boot:run
```

### Run Tests

```bash
mvn test
```

---

## 🔑 Key Highlights

- Spring Security Authentication
- Role-Based Access Control (RBAC)
- MongoDB Integration
- Redis Caching
- External Weather API Integration
- Automated Email Scheduling
- Transaction Management
- Production-Grade Logging
- Unit & Integration Testing
- JWT Authentication

---

##  Frontend (React)
A dynamic user interface has been integrated into the project using **React** to offer a seamless user experience.

### Features
- Responsive Dashboard
- Interactive forms for managing Journal Entries
- Real-time weather display integrated with backend APIs
- Secure login and registration flows

---

##  Future Enhancements

- Docker Containerization
- CI/CD Pipeline Integration
- Kubernetes Deployment
- Swagger/OpenAPI Documentation
- Kafka Event Streaming

---

##  Author

**Sharvari**

Spring Boot | Java Backend Developer | MongoDB | Redis | REST APIs
