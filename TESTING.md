# API Testing Documentation

## Overview

This document covers manual API testing performed using **Postman** against the containerized application stack.

**Infrastructure:** Docker (3 services — MongoDB, Redis, Kafka)  
**Auth:** JWT (Bearer Token)  
**Testing Date:** 2025-06-26  
**Tested By:** Sharvari Shalgar

---

## Environment Setup

### Running the Stack

```bash
docker-compose up -d
```

### Services

| Service  | Port  | Purpose              |
|----------|-------|----------------------|
| MongoDB  | 27017 | Primary data store   |
| Redis    | 6379  | Caching / sessions   |
| Kafka    | 9092  | Event streaming      |
| App      | 8080  | REST API             |

### Postman Environment Variables

| Variable    | Value                   |
|-------------|-------------------------|
| `BASE_URL`  | `http://localhost:8080` |
| `JWT_TOKEN` | *(set after login)*     |

---

## Test Coverage — June 26, 2025

###  Public Controller

| # | Endpoint         | Method | Description              | Auth       | Status |
|---|------------------|--------|--------------------------|------------|-------|
| 1 | `/public/signup` | POST   | Register new user        | not needed |  Pass |
| 2 | `/public/login`  | POST   | Login & receive JWT      | not needed |  Pass |

---

###  JournalEntryController Controller

| # | Endpoint                | Method | Description                     | Auth   | Status |
|---|-------------------------|--------|---------------------------------|--------|--------|
| 1 | `/journal`              | POST   | Create journal entry            | needed |  Pass  |

---

### Collections Checked

**`users` collection**
```json
{
  "_id": "ObjectId(...)",
  "username": "testuser",
  "email": "test@example.com",
  "passwordHash": "...",
  "createdAt": "2025-06-26T..."
}
```

**`journalentries` collection**
```json
{
  "_id": "ObjectId(...)",
  "userId": "ObjectId(...)",
  "title": "My First Entry",
  "content": "...",
  "createdAt": "2025-06-26T..."
}
```

### How to Verify in MongoDB Shell

```bash
# Connect to MongoDB inside container
docker exec -it <mongo-container-name> mongosh

# Switch to your DB
use journaldb

# Verify users
db.users.find().pretty()

# Verify journal entries
db.journal_entries.find().pretty()

# Verify user-journal relationship
db.journal_entries.find({ userId: ObjectId("...") }).pretty()
```

---

## JWT Authentication Flow

1. **Register** → `POST /public/signup` with `{ username, email, password }`
2. **Login** → `POST /public/login` → response contains `{ token: "eyJ..." }`
3. **Set token** in Postman: `Authorization: Bearer <token>`
4. **Access protected routes** with the token in the header

### What Was Tested

- ✅ Valid JWT → returns correct user data
- ✅ Missing JWT → returns `401 Unauthorized`
- ✅ Invalid/expired JWT → returns `401 Unauthorized`
- ✅ JWT contains correct `userId` — journal entries scoped to that user

---