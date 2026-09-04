# Lawyer Backend System

Backend system built with Spring Boot for managing legal office operations, including clients, cases, appointments, tasks, court sessions, documents and billing.

## Overview

This project provides a REST API for a law firm or legal department to manage:

- Clients
- Legal cases
- Appointments and court sessions
- Tasks and case notes
- Financial flows such as invoices and payments
- Documents and signed files using cloud object storage
- Authentication and authorization
- Event processing via RabbitMQ
- Session management with Redis

## Tech Stack

- Java 17
- Spring Boot 4.1.0
- Spring Data JPA
- PostgreSQL
- Redis
- RabbitMQ
- MinIO / AWS S3-compatible storage
- JWT
- Maven
- Docker / Docker Compose

## Project Structure

```text
Lawyer_BackEnd_System/
├── src/
│   ├── main/
│   │   ├── java/com/web/lawyer_backend_system/
│   │   │   ├── controller/
│   │   │   ├── service/
│   │   │   ├── repository/
│   │   │   ├── entity/
│   │   │   ├── dto/
│   │   │   ├── config/
│   │   │   └── security/
│   │   └── resources/
│   │       └── application.properties
│   └── test/
├── Dockerfile
├── compose.yaml
├── pom.xml
├── mvnw
├── .gitignore
└── README.md
```

## Prerequisites

Before running the project, make sure you have installed:

- Java 17+
- Maven 3.9+
- Docker and Docker Compose
- Git

## Configuration

The main configuration is in:

- `src/main/resources/application.properties`

Default values used by the project:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/lawyer
spring.datasource.username=lawyer
spring.datasource.password=lawyerpass
jwt.secret=lawyer-local-secret-change-me
aws.s3.endpoint=http://localhost:9000
aws.s3.access-key=minioadmin
aws.s3.secret-key=minioadmin
aws.s3.region=us-east-1
```

You can override them with environment variables when running locally or through Docker.

## Run with Docker

From the project root:

```bash
docker compose up --build -d
```

This starts:

- Spring Boot application on `http://localhost:8080`
- PostgreSQL on `localhost:5432`
- Redis on `localhost:6379`
- RabbitMQ on `localhost:5672`
- RabbitMQ Management UI on `http://localhost:15672`
- MinIO on `http://localhost:9000`
- MinIO Console on `http://localhost:9001`

To stop the containers:

```bash
docker compose down
```

To view logs:

```bash
docker compose logs -f app
```

## Run Locally (without Docker)

1. Start PostgreSQL, Redis, RabbitMQ, and MinIO manually.
2. Update the configuration values in `application.properties` or set environment variables.
3. Run:

```bash
./mvnw clean install
./mvnw spring-boot:run
```

Or build the JAR:

```bash
./mvnw clean package
java -jar target/Lawyer_BackEnd_System-0.0.1-SNAPSHOT.jar
```

## Default Credentials

The application includes a default admin user for development/testing:

- Username: `admin`
- Password: `admin123`

## API Notes

The project exposes REST endpoints under the application context and uses security + JWT patterns. The backend is designed to support a front-end client or mobile app consuming the services.

## Environment Variables

You can override key settings using environment variables:

```bash
export SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/lawyer
export SPRING_DATASOURCE_USERNAME=lawyer
export SPRING_DATASOURCE_PASSWORD=lawyerpass
export JWT_SECRET=your-secret-key
export AWS_S3_ENDPOINT=http://localhost:9000
export AWS_S3_ACCESS_KEY=minioadmin
export AWS_S3_SECRET_KEY=minioadmin
export AWS_S3_BUCKET_NAME=lawyer-documents-bucket
```

## License

This project is for internal or educational use unless a separate license is provided by the repository owner.

## Author

Developed for a legal system / law firm management platform.

## Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.
