# kyc_service

KYC microservice for the Airport Passenger Service Platform.

## Local run (PostgreSQL)

This service expects PostgreSQL settings via environment variables:

- `DB_HOST`
- `DB_PORT`
- `DB_NAME`
- `DB_USER`
- `DB_PASSWORD`

Example:

```bash
export DB_HOST=localhost
export DB_PORT=5432
export DB_NAME=airport
export DB_USER=appuser
export DB_PASSWORD=secret
./gradlew bootRun
```

Flyway migrations are loaded from:

- `classpath:db/migration`

JPA is configured with:

- `spring.jpa.hibernate.ddl-auto=validate` (schema must already match Flyway)

## Useful endpoints

- Swagger UI: `GET /swagger-ui.html` (or `GET /docs`)
- Actuator health: `GET /actuator/health`
- Passenger CRUD:
  - `GET /api/passengers`
  - `POST /api/passengers`
  - `GET /api/passengers/{id}`
  - `PUT /api/passengers/{id}`
  - `DELETE /api/passengers/{id}`
- KYC:
  - `GET /api/kyc/passengers/{passengerId}/status`
  - `PUT /api/kyc/passengers/{passengerId}/status`
  - `GET /api/kyc/passengers/{passengerId}/records`
