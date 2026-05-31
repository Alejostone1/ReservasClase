# ReservaT

Sistema de reservas FullStack — backend Spring Boot (`reservation-backend`) y frontend Angular (`reservation-frontend`).

## Requisitos

### Backend
- Java 25
- Maven (incluido `mvnw`)
- PostgreSQL

### Frontend
- Node.js 18+
- npm (incluido con Node.js)

## Configuración

### Backend

1. Crear la base de datos `reservation` en PostgreSQL:
```sql
CREATE DATABASE reservation;
```

2. Definir la contraseña de la base de datos:
```powershell
$env:DB_PASSWORD = "tu_contraseña"
```

3. Ajustar `reservation-backend/src/main/resources/application.properties` si cambias host, puerto o nombre de la base.

### Frontend

El frontend ya está configurado para conectarse al backend en `http://127.0.0.1:8080/api`. Si necesitas cambiar la URL, edita `reservation-frontend/src/environments/environment.ts`.

## Ejecutar todo con un solo comando

Desde la raíz del proyecto (`ReservaT`):

```powershell
npm install
npm start
```

Alternativa sin instalar dependencias extra:

```powershell
.\start.ps1
```

Eso levanta el backend en `http://localhost:8080` y el frontend en `http://localhost:4200`.

### Por separado


```powershell
cd reservation-backend
.\mvnw.cmd spring-boot:run
```

- Health: http://localhost:8080/actuator/health
- Swagger UI: http://localhost:8080/swagger-ui/index.html
- API Base: http://localhost:8080/api

### Frontend

```powershell
cd reservation-frontend
npm install
npm start
```

- Aplicación: http://localhost:4200

## API Endpoints

### Reservas

- `GET /api/reservas` - Listar todas las reservas
- `POST /api/reservas` - Crear una nueva reserva
- `DELETE /api/reservas/{id}` - Cancelar una reserva

## Estructura del Proyecto

```
ReservaT/
├── reservation-backend/          # API REST (Spring Boot 4, JPA, PostgreSQL)
│   ├── src/main/java/com/juniorstone/reservation_backend/
│   │   ├── config/               # Configuración (CORS, OpenAPI)
│   │   ├── controller/           # Controladores REST
│   │   ├── dto/                  # Data Transfer Objects
│   │   ├── entity/               # Entidades JPA
│   │   ├── exception/            # Excepciones personalizadas
│   │   ├── repository/           # Repositorios Spring Data JPA
│   │   └── service/              # Lógica de negocio
│   └── src/test/java/            # Tests unitarios
├── reservation-frontend/         # Frontend Angular 18+
│   ├── src/app/
│   │   ├── components/           # Componentes standalone
│   │   ├── models/               # Interfaces TypeScript
│   │   ├── services/             # Servicios HTTP
│   │   └── environments/         # Configuración de ambientes
│   └── src/test/                 # Tests (pendientes)
├── Instrucciones/                # Especificaciones del proyecto
│   ├── 1backend.md
│   └── 2frontend.md
└── .cursor/rules/                # Convenciones del proyecto para Cursor
    ├── springboot.mdc
    └── angular.mdc
```

## Características Implementadas

### Backend
- ✅ Arquitectura de capas (Controller, Service, Repository)
- ✅ Entidad Reservation con campos: id, nombreCliente, date, time, service, status
- ✅ Enum ReservationStatus (ACTIVA, CANCELADA)
- ✅ Validación de reglas de negocio (no duplicar reservas en misma fecha/hora)
- ✅ Configuración CORS para frontend
- ✅ Documentación OpenAPI/Swagger
- ✅ Manejo de excepciones global
- ✅ Tests unitarios (Service, Controller, Repository)

### Frontend
- ✅ Proyecto Angular 18+ con componentes standalone
- ✅ Servicio HTTP para comunicación con backend
- ✅ Componente de listado de reservas con tabla
- ✅ Componente de formulario reactivo para crear reservas
- ✅ Componente Toast para notificaciones de error
- ✅ Enrutamiento configurado
- ✅ Validación de formularios
- ✅ Interfaz moderna y responsiva

## Reglas de Negocio

1. No se pueden crear dos reservas para la misma fecha y hora (si no están canceladas)
2. Solo se pueden cancelar reservas con estado ACTIVA
3. Una reserva cancelada no puede ser cancelada nuevamente
4. Las nuevas reservas se crean con estado ACTIVA

## Tecnologías

### Backend
- Java 25
- Spring Boot 4.0.6
- Spring Data JPA
- PostgreSQL
- SpringDoc OpenAPI 3.0.2
- JUnit 5 + Mockito

### Frontend
- Angular 18+
- TypeScript
- HttpClient
- Reactive Forms
- SCSS

## Desarrollo

### Ejecutar tests del backend
```powershell
cd reservation-backend
.\mvnw.cmd test
```

### Ejecutar tests del frontend (pendientes)
```powershell
cd reservation-frontend
npm test
```

