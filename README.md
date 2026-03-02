# ecolink-api-java

## Project Overview
EcoLink API is a Spring Boot-based RESTful API service that provides Maps and Content Management System (CMS) functionality. This MVP is designed to support location-based services integrated with content management capabilities, making it ideal for environmental or eco-friendly applications that need to manage geographic data alongside multimedia content.

## Technology Stack
- **Java**: 17
- **Spring Boot**: 3.2.2
- **Database**: MongoDB
- **Build Tool**: Maven

## Project Structure
```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── ecolink/
│   │           └── api/
│   │               ├── config/       # Configuration classes (security, database, etc.)
│   │               ├── controller/   # REST API endpoints
│   │               ├── dto/          # Data Transfer Objects
│   │               ├── model/        # MongoDB entity models
│   │               ├── repository/   # MongoDB repositories
│   │               └── service/      # Business logic layer
│   └── resources/
│       └── application.properties    # Application configuration
└── test/
    └── java/
        └── com/
            └── ecolink/
                └── api/              # Test classes
```

## Recommended Libraries & Frameworks

### 1. Google Maps Integration
- **Google Maps Platform Java Client** (`com.google.maps:google-maps-services`)
  - Official Java client for Google Maps APIs (Geocoding, Places, Directions, Distance Matrix)
  - Version: Latest stable (e.g., 2.2.0+)
  - Maven: `com.google.maps:google-maps-services:2.2.0`
  
- **Alternative**: OpenStreetMap with **Nominatim** for geocoding (open-source alternative)

### 2. Cloud File Storage (Images/Videos)
- **AWS S3** with Spring Cloud AWS
  - `io.awspring.cloud:spring-cloud-aws-starter-s3`
  - Industry standard, highly scalable
  
- **Google Cloud Storage**
  - `com.google.cloud:google-cloud-storage`
  - Integrates well with Google Maps Platform
  
- **Azure Blob Storage**
  - `com.azure:azure-storage-blob`
  
- **Cloudinary** (Recommended for media optimization)
  - `com.cloudinary:cloudinary-http44:1.36.0`
  - Built-in image/video transformation and optimization

### 3. Database - MongoDB
- **Spring Data MongoDB** (Already included)
  - `spring-boot-starter-data-mongodb`
  - Provides repository abstraction and MongoDB integration
  
- **MongoDB Atlas** (Recommended cloud hosting)
  - Fully managed MongoDB service
  - Built-in backups, monitoring, and scaling

### 4. Security
- **Spring Security** with JWT
  - `spring-boot-starter-security`
  - `io.jsonwebtoken:jjwt-api:0.12.3`
  - `io.jsonwebtoken:jjwt-impl:0.12.3`
  - `io.jsonwebtoken:jjwt-jackson:0.12.3`
  
- **OAuth2 Resource Server** (for third-party authentication)
  - `spring-boot-starter-oauth2-resource-server`
  
- **Spring Security MongoDB** (for user storage in MongoDB)
  - Custom UserDetailsService implementation with MongoDB

- **BCrypt** (password encryption, included with Spring Security)

### 5. API Documentation
- **SpringDoc OpenAPI (Swagger)** (Recommended)
  - `org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0`
  - Automatic OpenAPI 3 specification generation
  - Interactive Swagger UI at `/swagger-ui.html`
  
- **Spring REST Docs** (Alternative for test-driven documentation)
  - `spring-restdocs-mockmvc`

### 6. Additional Recommended Libraries

#### Validation
- **Hibernate Validator** (Already included via spring-boot-starter-validation)
  - Bean validation with annotations

#### Utilities
- **Lombok** (Already included)
  - Reduces boilerplate code with annotations
  
- **MapStruct** (for DTO mapping)
  - `org.mapstruct:mapstruct:1.5.5.Final`
  - Compile-time type-safe mapping

#### Monitoring & Observability
- **Spring Boot Actuator**
  - `spring-boot-starter-actuator`
  - Health checks, metrics, monitoring endpoints

#### Testing
- **Spring Boot Test** (Already included)
- **Testcontainers** (for MongoDB integration tests)
  - `org.testcontainers:mongodb:1.19.3`

## Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- MongoDB 4.4+ (local or MongoDB Atlas)

### Building the Project
```bash
mvn clean install
```

### Running the Application
```bash
mvn spring-boot:run
```

The API will be available at `http://localhost:8080`

### Running Tests
```bash
mvn test
```

## Configuration
Configure your MongoDB connection and other settings in `src/main/resources/application.properties`:
```properties
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
spring.data.mongodb.database=ecolink_db
```

For production or cloud deployment, use environment variables or `application-prod.properties`.

## API Documentation
Once SpringDoc OpenAPI is added, access the interactive API documentation at:
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

## Project Roadmap
- [ ] Implement Google Maps integration services
- [ ] Setup cloud file storage for media management
- [ ] Implement authentication and authorization
- [ ] Create core CMS entities and repositories
- [ ] Develop RESTful API endpoints
- [ ] Add comprehensive API documentation
- [ ] Implement integration tests
- [ ] Setup CI/CD pipeline

## License
TBD

## Contributors
TBD
