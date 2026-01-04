## How to Run the Project

1. Install the dependencies:
	```sh
	./mvnw clean install
	```
2. Run the API profile (serves HTTP on port 8080):
	```sh
	./mvnw spring-boot:run -Dspring-boot.run.profiles=backend
	```
3. Run the worker profile (background jobs):
	```sh
	./mvnw spring-boot:run -Dspring-boot.run.profiles=worker
	```
4. Run using docker compose (brings up db, minio, rabbitmq, api, worker):
	```sh
	docker compose up api worker
	```

## Project Folder Structure

```
/backend
├── Dockerfile
├── pom.xml
├── mvnw
├── src
│   ├── main
│   │   ├── java/com/example/demo
│   │   │   ├── configs        # Application configs (beans, swagger, etc.)
│   │   │   ├── controller     # REST endpoints
│   │   │   ├── domain         # Models and enums
│   │   │   ├── exceptions     # Custom exceptions and handlers
│   │   │   ├── infra          # Integrations and repositories
│   │   │   ├── security       # Security and JWT
│   │   │   ├── services       # Business logic
│   │   │   ├── shared         # DTOs, mappers, utilities
│   │   │   └── BackendApplication.java
│   │   └── resources
│   │       ├── application.properties
│   │       ├── application-backend.properties
│   │       ├── application-worker.properties
│   │       └── db/migration   # Flyway migrations
│   └── test/java/com/example/demo  # Tests
```
