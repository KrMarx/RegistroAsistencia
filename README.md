# Employee Attendance Management System

Sistema de gestión de empleados y registro de entrada/salida con soporte de geolocalización, construido con Spring WebFlux, R2DBC y PostgreSQL siguiendo Arquitectura Hexagonal.

## 🏗️ Arquitectura

El proyecto implementa **Arquitectura Hexagonal (Ports and Adapters)** con las siguientes capas:

### Domain Layer (Núcleo del negocio)
- **Model**: Entidades del dominio (`Employee`, `AttendanceRecord`)
- **Ports Inbound**: Interfaces de casos de uso (`EmployeeServicePort`, `AttendanceServicePort`)
- **Ports Outbound**: Interfaces de repositorios (`EmployeeRepositoryPort`, `AttendanceRepositoryPort`)

### Application Layer (Casos de uso)
- **Services**: Implementación de la lógica de negocio (`EmployeeService`, `AttendanceService`)

### Infrastructure Layer (Adaptadores)
- **Inbound Adapters**: Controllers REST, DTOs, Mappers
- **Outbound Adapters**: Implementaciones de repositorios R2DBC
- **Configuration**: Configuración de OpenAPI/Swagger

## 🚀 Tecnologías

- **Java 21**
- **Spring Boot 4.0.1**
- **Spring WebFlux** (Programación reactiva)
- **Spring Data R2DBC** (Acceso reactivo a base de datos)
- **PostgreSQL** (Base de datos)
- **SpringDoc OpenAPI** (Documentación Swagger)
- **Gradle** (Gestión de dependencias)

## 📋 Requisitos Previos

- Java 21 o superior
- PostgreSQL 12 o superior
- Gradle 8.x (o usar el wrapper incluido)

## 🔧 Configuración de la Base de Datos

1. **Crear la base de datos en PostgreSQL:**

```sql
CREATE DATABASE employee_db;
```

2. **Ejecutar el script SQL de inicialización:**

El script está ubicado en `src/main/resources/schema.sql`. Puedes ejecutarlo manualmente o la aplicación lo ejecutará automáticamente al iniciar.

```bash
psql -U postgres -d employee_db -f src/main/resources/schema.sql
```

3. **Configurar las credenciales:**

Editar `src/main/resources/application.properties` si es necesario:

```properties
spring.r2dbc.url=r2dbc:postgresql://localhost:5432/employee_db
spring.r2dbc.username=postgres
spring.r2dbc.password=postgres
```

## 🏃 Ejecutar la Aplicación

### Usando Gradle Wrapper (Recomendado)

**Windows:**
```powershell
.\gradlew.bat bootRun
```

**Linux/Mac:**
```bash
./gradlew bootRun
```

### Usando Gradle instalado

```bash
gradle bootRun
```

La aplicación estará disponible en: `http://localhost:8080`

## 📚 Documentación API (Swagger)

Una vez iniciada la aplicación, accede a:

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/api-docs

## 🔌 Endpoints Disponibles

### Gestión de Empleados

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/api/employees` | Crear nuevo empleado |
| GET | `/api/employees` | Obtener todos los empleados |
| GET | `/api/employees/{id}` | Obtener empleado por ID |
| PUT | `/api/employees/{id}` | Actualizar empleado |
| DELETE | `/api/employees/{id}` | Eliminar empleado |

### Gestión de Asistencia

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/api/attendance/entrance` | Registrar entrada |
| POST | `/api/attendance/exit` | Registrar salida |
| GET | `/api/attendance` | Obtener todos los registros |
| GET | `/api/attendance/{id}` | Obtener registro por ID |
| GET | `/api/attendance/employee/{employeeId}` | Obtener registros de un empleado |
| DELETE | `/api/attendance/{id}` | Eliminar registro |

## 📝 Ejemplos de Uso

### Crear un Empleado

```bash
curl -X POST http://localhost:8080/api/employees \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Juan",
    "lastName": "Pérez",
    "email": "juan.perez@company.com",
    "phoneNumber": "+52123456789",
    "position": "Software Engineer"
  }'
```

### Registrar Entrada

```bash
curl -X POST http://localhost:8080/api/attendance/entrance \
  -H "Content-Type: application/json" \
  -d '{
    "employeeId": 1,
    "latitude": 40.7128,
    "longitude": -74.0060
  }'
```

### Registrar Salida

```bash
curl -X POST http://localhost:8080/api/attendance/exit \
  -H "Content-Type: application/json" \
  -d '{
    "employeeId": 1,
    "latitude": 40.7128,
    "longitude": -74.0060
  }'
```

### Obtener Registros de un Empleado

```bash
curl http://localhost:8080/api/attendance/employee/1
```

## 🧪 Ejecutar Tests

```bash
.\gradlew.bat test
```

## 📦 Compilar la Aplicación

```bash
.\gradlew.bat build
```

El archivo JAR se generará en: `build/libs/demo-spring-webflux-0.0.1-SNAPSHOT.jar`

## 🐳 Docker (Opcional)

Puedes ejecutar PostgreSQL usando Docker:

```bash
docker run --name postgres-employee-db \
  -e POSTGRES_PASSWORD=postgres \
  -e POSTGRES_DB=employee_db \
  -p 5432:5432 \
  -d postgres:15
```

## 📂 Estructura del Proyecto

```
src/main/java/com/softtek/demo_spring_webflux/
├── domain/
│   ├── model/                      # Entidades del dominio
│   │   ├── Employee.java
│   │   └── AttendanceRecord.java
│   └── port/
│       ├── inbound/                # Interfaces de casos de uso
│       │   ├── EmployeeServicePort.java
│       │   └── AttendanceServicePort.java
│       └── outbound/               # Interfaces de repositorios
│           ├── EmployeeRepositoryPort.java
│           └── AttendanceRepositoryPort.java
├── application/
│   └── service/                    # Implementación de casos de uso
│       ├── EmployeeService.java
│       └── AttendanceService.java
└── infrastructure/
    ├── adapter/
    │   ├── inbound/
    │   │   └── rest/
    │   │       ├── controller/     # Controllers REST
    │   │       ├── dto/            # DTOs Request/Response
    │   │       ├── mapper/         # Mappers DTO <-> Domain
    │   │       └── exception/      # Exception handlers
    │   └── outbound/
    │       └── persistence/        # Implementaciones R2DBC
    └── config/                     # Configuraciones
        └── OpenApiConfig.java
```

## 🔐 Validaciones

Los endpoints incluyen validaciones automáticas:
- Email válido
- Campos requeridos no nulos
- Tipos de datos correctos

## ⚡ Características Reactivas

- Operaciones no bloqueantes usando Reactor (Mono/Flux)
- Alto rendimiento y escalabilidad
- Manejo eficiente de recursos

## 📄 Licencia

Este proyecto es parte de una demostración técnica para entrevista.

## 👥 Autor

Demo Spring WebFlux