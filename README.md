# School Management System - Backend

A Spring Boot REST API for managing school operations including students, teachers, courses, enrollments, grades, and admissions.

## Technology Stack

- **Framework**: Spring Boot 3.2.1
- **Language**: Java 17
- **Database**: PostgreSQL
- **Security**: Spring Security with JWT
- **ORM**: Spring Data JPA
- **Migration**: Flyway
- **Mapping**: MapStruct
- **Build Tool**: Maven

## Features

- JWT-based authentication and authorization
- Role-based access control (ADMIN, TEACHER, STUDENT)
- RESTful API endpoints for all entities
- Global exception handling
- Database migrations with Flyway
- Entity-DTO mapping with MapStruct
- Comprehensive validation

## Project Structure

```
src/
├── main/
│   ├── java/com/example/school/
│   │   ├── config/           # Security and application configuration
│   │   ├── controller/       # REST controllers
│   │   ├── dto/             # Data Transfer Objects
│   │   ├── exception/       # Global exception handling
│   │   ├── mapper/          # MapStruct mappers
│   │   ├── model/           # JPA entities
│   │   ├── repository/      # Data repositories
│   │   ├── security/        # Security components (JWT, UserPrincipal)
│   │   └── service/         # Business logic services
│   └── resources/
│       ├── application.yml  # Application configuration
│       └── db/migration/    # Flyway migration scripts
└── test/                    # Test classes
```

## Setup Instructions

### Prerequisites

- Java 17 or higher
- Maven 3.6+
- PostgreSQL 12+

### Database Setup

1. Create a PostgreSQL database:
```sql
CREATE DATABASE school_management;
```

2. Update `src/main/resources/application.yml` with your database credentials:
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/school_management
    username: your_username
    password: your_password
```

### Running the Application

1. Clone the repository and navigate to the backend folder
2. Install dependencies:
```bash
mvn clean install
```

3. Run the application:
```bash
mvn spring-boot:run
```

The API will be available at `http://localhost:8080/api`

### Default Users

The application comes with pre-seeded users:

- **Admin**: username: `admin`, password: `password`
- **Teacher**: username: `teacher1`, password: `password`
- **Student**: username: `student1`, password: `password`

## API Endpoints

### Authentication
- `POST /api/auth/login` - User login
- `POST /api/auth/register` - User registration

### Students (Admin/Teacher access)
- `GET /api/students` - Get all students
- `GET /api/students/{id}` - Get student by ID
- `POST /api/students` - Create new student (Admin only)
- `PUT /api/students/{id}` - Update student (Admin only)
- `DELETE /api/students/{id}` - Delete student (Admin only)

### Teachers (Admin access)
- `GET /api/teachers` - Get all teachers
- `GET /api/teachers/{id}` - Get teacher by ID
- `POST /api/teachers` - Create new teacher
- `PUT /api/teachers/{id}` - Update teacher
- `DELETE /api/teachers/{id}` - Delete teacher

### Courses
- `GET /api/courses` - Get all courses
- `GET /api/courses/{id}` - Get course by ID
- `POST /api/courses` - Create new course (Admin/Teacher)
- `PUT /api/courses/{id}` - Update course (Admin/Teacher)
- `DELETE /api/courses/{id}` - Delete course (Admin)

### Enrollments
- `GET /api/enrollments` - Get all enrollments
- `GET /api/enrollments/{id}` - Get enrollment by ID
- `POST /api/enrollments` - Create new enrollment (Admin)
- `PUT /api/enrollments/{id}` - Update enrollment (Admin)
- `DELETE /api/enrollments/{id}` - Delete enrollment (Admin)

### Grades
- `GET /api/grades` - Get all grades
- `GET /api/grades/{id}` - Get grade by ID
- `POST /api/grades` - Create new grade (Teacher/Admin)
- `PUT /api/grades/{id}` - Update grade (Teacher/Admin)

### Admissions (Admin access)
- `GET /api/admissions` - Get all admissions
- `GET /api/admissions/{id}` - Get admission by ID
- `POST /api/admissions` - Create new admission
- `PUT /api/admissions/{id}` - Update admission

## Security

The application uses JWT tokens for authentication. Include the token in the Authorization header:

```
Authorization: Bearer <your-jwt-token>
```

## Role-Based Access

- **ADMIN**: Full access to all endpoints
- **TEACHER**: Can view students, manage grades for their courses
- **STUDENT**: Can view their own information only

## Configuration

Key configuration properties in `application.yml`:

```yaml
jwt:
  secret: your-secret-key
  expiration: 86400000  # 24 hours

spring:
  jpa:
    hibernate:
      ddl-auto: validate  # Use Flyway for schema management
  
  flyway:
    baseline-on-migrate: true
    validate-on-migrate: true
```

## Development

### Adding New Entities

1. Create the entity in `model/` package
2. Create DTOs in `dto/` package
3. Create repository interface in `repository/` package
4. Create mapper interface in `mapper/` package
5. Create service class in `service/` package
6. Create controller in `controller/` package
7. Add Flyway migration script

### Testing

Run tests with:
```bash
mvn test
```

## Deployment

For production deployment:

1. Update `application.yml` with production database settings
2. Set JWT secret as environment variable
3. Build the application: `mvn clean package`
4. Run the JAR file: `java -jar target/school-management-1.0.0.jar`