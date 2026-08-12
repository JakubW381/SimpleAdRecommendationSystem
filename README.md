# ARS – Advertisement Recommendation System


<p align="center">

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.1-green)
![Kafka](https://img.shields.io/badge/Kafka-3_Brokers-black)
![Redis](https://img.shields.io/badge/Redis-Cache-red)
![gRPC](https://img.shields.io/badge/gRPC-RPC-blue)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Database-blue)
![OpenTelemetry](https://img.shields.io/badge/OpenTelemetry-Tracing-purple)
![Jaeger](https://img.shields.io/badge/Jaeger-Observability-yellow)
![Prometheus](https://img.shields.io/badge/Prometheus-Metrics-e6522c)
![Grafana](https://img.shields.io/badge/Grafana-Dashboards-F46800)

</p>

ARS is a microservice-based advertisement recommendation platform built with Spring Boot, gRPC, Kafka, Redis and PostgreSQL.

The project focuses on modern backend architecture patterns and distributed systems concepts, including:

* Microservices
* Hexagonal Architecture (Ports & Adapters)
* Domain-Driven Design (DDD)
* CQRS
* Event-Driven Architecture
* gRPC communication
* Kafka messaging
* Redis caching
* OpenTelemetry
* Jaeger distributed tracing
* Prometheus & Grafana observability
* JWT authentication and authorization
* Resilience4j Retry

---

# Monitoring & Observability

## Jaeger (Distributed Tracing)

All traces are available through Jaeger:

```text
http://localhost:16686
```

The project is instrumented with OpenTelemetry and traces:

* HTTP requests
* Gateway routing
* gRPC communication

---

## Prometheus (Metrics Collection)

Prometheus scrapes application and JVM metrics from all microservices via Spring Boot Actuator endpoints:

```text
http://localhost:9090
```

Prometheus is configured to collect metrics from:

* `ars-gateway`
* `ars-authenticator`
* `ars-user-service`
* `ars-ad-service`

Scrape target endpoints across services:

```text
GET /actuator/prometheus
```

---

## Grafana (Metrics Visualization)

Pre-configured Grafana dashboards for system and application monitoring:

```text
http://localhost:3000
```

* **Default credentials:** `admin` / `admin`
* **Datasource:** Prometheus (`http://prometheus:9090`)
* **Dashboards:** JVM statistics, HTTP/gRPC throughput, response latencies, memory usage, and thread pools across all 4 microservices.

---

## Swagger UI

Swagger UI is available at:

```text
http://localhost:8080/swagger-ui/index.html
```

Available API groups:

* auth
* user
* ads
* provider
* impressions

---

# Technology Stack

## Global Dependencies

```text
Spring Boot 4.1.0
Google Protobuf Plugin 0.10.0
```

---

# Services

## ars-gateway

API Gateway responsible for routing and authentication propagation.

### Dependencies

* spring-cloud-starter-gateway-server-webflux
* spring-boot-starter-security
* spring-boot-starter-security-oauth2-resource-server
* springdoc-openapi-starter-webflux-ui
* spring-boot-starter-actuator
* micrometer-registry-prometheus
* spring-boot-starter-opentelemetry

### Responsibilities

* Request routing
* JWT validation
* Security enforcement
* User identity propagation
* Gateway metric exposition

### Generated Headers

When a JWT token is successfully validated:

#### User

```text
X-User-Id
```

is generated from JWT claims.

#### Provider

```text
X-Provider-Id
```

is generated from JWT claims.

> Currently some endpoints still expose userId/providerId as request parameters for testing purposes. These will be replaced entirely by gateway-generated headers.

---

## ars-authenticator

Authentication and authorization service built using a classic layered architecture.

### Dependencies

* spring-boot-starter-grpc-client
* protobuf-java
* grpc-stub
* grpc-protobuf
* resilience4j-spring-boot4
* springdoc-openapi-starter-webmvc-ui
* spring-boot-starter-webmvc
* spring-boot-starter-data-jpa
* spring-boot-starter-security
* spring-boot-starter-validation
* nimbus-jose-jwt
* spring-boot-starter-actuator
* micrometer-registry-prometheus
* spring-boot-starter-opentelemetry
* lombok

### Responsibilities

* User registration
* User login
* Provider registration
* Provider login
* JWT generation
* JWK exposure

### Endpoints

### Authentication

| Method | Endpoint | Description |
| --- | --- | --- |
| POST | `/api/auth/user/register` | Register new user |
| POST | `/api/auth/user/login` | User login |
| POST | `/api/auth/provider/register` | Register new provider |
| POST | `/api/auth/provider/login` | Provider login |
| GET | `/.well-known/jwks.json` | Public RSA JWK set |

### Registration Flow

#### User Registration

```text
Gateway
   |
Authenticator
   |
gRPC
   |
User Service
```

#### Provider Registration

```text
Gateway
   |
Authenticator
   |
gRPC
   |
Ad Service
```

Registration is synchronous and Authenticator persists authentication data only after successful persistence in the target service.

### Reliability

Resilience4j Retry is used for gRPC registration communication.

---

## ars-user-service

User management and recommendation orchestration service.

Architecture:

* Hexagonal Architecture
* DDD
* CQRS

### Dependencies

* spring-boot-starter-grpc-server
* spring-boot-starter-grpc-client
* protobuf-java
* grpc-stub
* grpc-protobuf
* spring-boot-starter-webmvc
* spring-boot-starter-data-jpa
* springdoc-openapi-starter-webmvc-ui
* spring-boot-starter-actuator
* micrometer-registry-prometheus
* spring-boot-starter-opentelemetry
* lombok

### Responsibilities

* User management
* User preference storage
* Recommendation requests

### Endpoints

### User API

| Method | Endpoint | Description |
| --- | --- | --- |
| GET | `/api/user/{userId}` | Get user details |
| GET | `/api/user/recommendation/{strategy}/{count}` | Generate recommendations |

### Recommendation Flow

```text
User Service
     |
    gRPC
     |
Ad Service
```

User interests are sent to Ad Service which calculates and returns recommendations.

---

## ars-ad-service

Advertisement management and recommendation engine.

Architecture:

* Hexagonal Architecture
* DDD
* CQRS

### Dependencies

* spring-boot-starter-grpc-server
* protobuf-java
* grpc-stub
* grpc-protobuf
* spring-boot-starter-webmvc
* spring-boot-starter-kafka
* spring-boot-starter-data-redis
* spring-boot-starter-data-jpa
* spring-boot-starter-validation
* springdoc-openapi-starter-webmvc-ui
* spring-boot-starter-actuator
* micrometer-registry-prometheus
* spring-boot-starter-opentelemetry
* lombok

### Responsibilities

* Advertisement management
* Provider management
* Recommendation generation
* Impression processing

### Endpoints

### Advertisement API

| Method | Endpoint | Description |
| --- | --- | --- |
| GET | `/api/ad/ads` | Get advertisements |
| POST | `/api/ad/ads/add` | Create advertisement |
| GET | `/api/ad/impressions/{adId}` | Get advertisement impressions |
| GET | `/api/ad/provider/{providerId}` | Get provider advertisements |
| POST | `/api/ad/provider/register` | Register provider (testing only) |

> Provider registration endpoint exists only for testing purposes.

### gRPC Services

* Provider Registration Service
* Advertisement Recommendation Service

---

# Recommendation Engine

Recommendations are generated using the Strategy Pattern.

Current implementation:

### Weighted Recommendation Strategy

Advertisements are ranked using:

* Matching advertisement tags
* Remaining daily impression capacity

The engine returns the highest-scoring advertisements.

The architecture allows introducing additional recommendation strategies without modifying existing application flows.

---

# Impression Processing Pipeline

Advertisement impressions are processed asynchronously.

### Flow

```text
Recommendation Returned
          |
Kafka Event
          |
Kafka Listener
          |
Redis Cache
          |
Scheduler
          |
PostgreSQL
```

### Details

1. Advertisement recommendation is returned.
2. Impression event is published to Kafka.
3. Ad Service consumes the event.
4. Impression counters are aggregated in Redis.
5. Scheduler periodically flushes aggregated counters.
6. Daily impression statistics are persisted in PostgreSQL.

### Benefits

* Reduced database write pressure
* High throughput event processing
* Eventual consistency
* Better scalability

---

# Infrastructure

The project uses:

### PostgreSQL

Separate databases for:

* Authentication
* Users
* Advertisements

### Redis

Used for temporary impression aggregation before persistence.

### Kafka

Three-node Kafka cluster used for event-driven communication.

Current event:

```text
ImpressionRegisterEvent
```

### Observability Stack

* **Jaeger:** Centralized distributed tracing.
* **OpenTelemetry:** Trace collection across all services.
* **Prometheus:** Time-series metric aggregation via Actuator `/actuator/prometheus`.
* **Grafana:** Visual dashboards for system performance, latency, and JVM state.

---

# Repository Structure

```text
root
├── ars-gateway
├── ars-authenticator
├── ars-user-service
├── ars-ad-service
├── prometheus.yml
└── docker-compose.yml
```

---

# Running the Project

## Start Infrastructure

```bash
docker compose up -d
```

This starts:

* PostgreSQL instances
* Redis
* Kafka cluster
* Jaeger (`http://localhost:16686`)
* Prometheus (`http://localhost:9090`)
* Grafana (`http://localhost:3000`)

## Start Services

Run:

```text
ars-gateway
ars-authenticator
ars-user-service
ars-ad-service
```

---

# Future Improvements

* Replace userId/providerId request parameters with gateway-generated headers
* Additional recommendation strategies
* Click tracking pipeline
* Campaign analytics
* Circuit Breaker integration
* Role-based authorization improvements
* Centralized logging pipeline (Grafana Loki)

---

# Purpose

The goal of this project is to demonstrate practical usage of:

* Spring Boot 4
* Microservices
* DDD
* CQRS
* Hexagonal Architecture
* Kafka
* Redis
* gRPC
* OpenTelemetry
* Jaeger
* Prometheus & Grafana
* JWT Authentication
* Resilience Patterns
* Clean Architecture
