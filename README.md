# 🚀 Distributed Commerce Platform

## 📌 Overview

The **Distributed Commerce Platform** is a microservices-based backend system built using modern cloud-native architecture principles.  
It demonstrates real-world distributed system patterns such as:

- Service Discovery  
- API Gateway Routing  
- Centralized Authentication & Authorization  
- Inter-service Communication  
- Scalable and Modular Design  

Each service in the platform is **independently deployable, scalable, and loosely coupled**.

---

## 🧩 Architecture Summary
Client
↓
API Gateway
↓
Eureka Service Discovery
↓
Business Microservices
-Auth Service
-Order Service
-(Future: Payment, Inventory, Notification)
↓
-Database


---

# 🛠 Implemented Components

### 1️⃣ Eureka Server – `discovery-service`

**Purpose**
- Central service registry for all microservices  
- Enables dynamic service discovery  
- Maintains health information of registered services  

**Key Features**
- Independent infrastructure service  
- Real-time service monitoring dashboard  
- Heartbeat-based registration  
- Dynamic lookup of running instances  

---

### 2️⃣ API Gateway – `api-gateway`

**Purpose**
- Single entry point for all client requests  
- Centralized authentication and authorization  
- Routing and traffic management  

**Key Features Implemented**
- JWT authentication at gateway level  
- Role-Based Access Control (RBAC)  
- Global request filtering  
- Structured SLF4J logging  
- Forwarding authenticated user context  
- Public route exclusion  
- Eureka-based dynamic routing  

**Public Endpoints**
/auth/login
/auth/register
/auth/refresh-token
/actuator/**


🔒 All other endpoints require a valid JWT token.

---

### 3️⃣ Auth Service – `auth-service` ✅ (Completed)

**Purpose**
- Dedicated service for authentication and authorization  

**Key Features**
- User registration  
- User login  
- JWT token generation  
- Role and permission management  
- Refresh token support  
- Fully integrated with API Gateway  

**Status**
✔ Auth service is fully implemented and working seamlessly with all other services.

---

### 4️⃣ Order Service – `order-service`

**Purpose**
- Core business microservice managing orders  

**Key Features**
- Clean layered architecture  
- JPA-based database integration  
- REST APIs for order operations  
- Eureka client integration  
- Fully routed through API Gateway  

---

# 🎯 What We Have Achieved So Far

### ✔ Platform Milestones

- Microservices successfully communicating via Eureka  
- API Gateway properly routing to Order Service  
- Global filters for logging and security  
- Database integration working  
- End-to-end testing completed with Postman  

### ✔ Security Layer

- Centralized JWT authentication  
- RBAC enforced at gateway level  
- Token validation using HS256  
- Secure request propagation to services  
- Proper 401 / 403 handling  

### ✔ Infrastructure Stability

- Independent services  
- No hardcoded URLs  
- Dynamic service discovery  
- Scalable design  
- Production-style architecture  

---

# 🧰 Technology Stack

| Layer | Technology |
|------|-----------|
| Language | Java 17 |
| Framework | Spring Boot |
| Gateway | Spring Cloud Gateway |
| Discovery | Eureka Server |
| Security | JWT + Spring Security |
| Database | PostgreSQL |
| Build Tool | Maven |
| Communication | REST |
| Testing | Postman |

---

# 🚀 Running the Platform

### Prerequisites

- Java 17  
- Maven  
- PostgreSQL  
- Postman (for testing)

---

### 🔄 Startup Order

Services must be started in the following sequence:

1. **discovery-service** (Eureka Server)  
2. **api-gateway**  
3. **auth-service**  
4. **order-service**

---

### 🔧 Build All Services

Run inside each service directory:

```bash
mvn clean install

🌐 Access Points
| Service                     | URL                                                              |
| --------------------------- | ---------------------------------------------------------------- |
| Eureka Dashboard            | [http://localhost:8761](http://localhost:8761)                   |
| API Gateway                 | [http://localhost:8080](http://localhost:8080)                   |
| Order Service (via gateway) | [http://localhost:8080/orders/](http://localhost:8080/orders/)** |


🔁 End-to-End Flow

Client Request  
   ↓  
API Gateway  
   ↓ (JWT validation + RBAC)  
Order Service  
   ↓  
Database  
   ↓  
Response

📌 Current Status

The platform currently supports:

Centralized routing
Service discovery
Secure communication
Authentication & Authorization via Auth Service
Order management
Structured logging
Real end-to-end microservice interaction

💡 This project now represents a working enterprise-style backend system.

🗺 Next Planned Phases
Phase 1 – Security Enhancements

OAuth2 and OpenID Connect support
Advanced permission management
API key-based authentication
Admin portal for role management

Phase 2 – Additional Microservices

payment-service
inventory-service
notification-service

Phase 3 – Resilience & Observability

Circuit breakers
Rate limiting
Distributed tracing
Metrics dashboards

Phase 4 – DevOps & Cloud 🚀

Dockerization of all services
Kubernetes deployment
CI/CD pipeline
Deployment on AWS (Planned Next Step)

📁 Project Structure

distributed-commerce-platform
│
├── discovery-service
├── api-gateway
├── order-service
└── auth-service


🧪 Testing

End-to-end flows have been validated using:

Postman
Gateway routing
Eureka service discovery
Database verification

🤝 Contribution Guidelines

Follow layered architecture
Use service discovery for communication
No hardcoded URLs
All APIs must go through API Gateway

Follow JWT security standards

👨‍💻 Maintainer

Asif Azam
Java Backend Developer

Primary Developer and Architect of the Distributed Commerce Platform
Focused on building scalable, cloud-native microservices systems

Contact Details
📧 Email: asif95azam@gmail.com
🐙 GitHub: https://github.com/asifazam008
💼 LinkedIn: https://www.linkedin.com/in/asifazam008

This project is being developed as a practical learning initiative to master enterprise backend architecture, microservices, and distributed systems.

⭐ If you like this project, feel free to give it a star on GitHub!
