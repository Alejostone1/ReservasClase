# ReservasClase

Sistema de reservas — backend Spring Boot (`reservation-backend`).

## Requisitos

- Java 25
- Maven (incluido `mvnw`)
- PostgreSQL

## Configuración

1. Crear la base de datos `g5reservation` en PostgreSQL.
2. Definir la contraseña de la base de datos:

```powershell
$env:DB_PASSWORD = "tu_contraseña"
```

3. Ajustar `reservation-backend/src/main/resources/application.properties` si cambias host, puerto o nombre de la base.

## Ejecutar

```powershell
cd reservation-backend
.\mvnw.cmd spring-boot:run
```

- Health: http://localhost:8080/actuator/health
- Swagger UI: http://localhost:8080/swagger-ui/index.html

## Estructura

- `reservation-backend/` — API REST (Spring Boot 4, JPA, PostgreSQL)
- `.cursor/rules/` — convenciones del proyecto para Cursor
