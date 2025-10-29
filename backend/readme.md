## Project folder structure

```
com.example.demo/
├── configs/          ← General application configurations (Beans, CORS, etc.)
├── controller/       ← REST endpoints, coordinates communication with services
├── domain/           ← Domain entities and business objects (e.g., Car, ParkingSpot, Ticket)
├── exceptions/       ← Custom exceptions for business rules and error handling
├── infra/            ← Infrastructure layer (persistence, repositories, integrations)
├── shared/           ← Reusable classes and components shared across modules
├── services/         ← Implementation of business logic
├── utils/            ← Utility functions and helper classes
└── BackendApplication.java  ← Main entry point (Spring Boot Application)

```
