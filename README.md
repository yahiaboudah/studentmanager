# Student Manager Application

## Overview

The Student Manager Application is a Java-based web application that allows users to manage students, their choices, and specifications (specs) for courses. It provides functionalities to create, read, update, and delete student records, choices, and specs. The application is built using Spring Boot, JPA, and Hibernate for data persistence, and it follows RESTful principles for API design.

## Features

- **Student Management**: Create, read, update, and delete student records.
- **Choice Management**: Manage choices made by students for different specs.
- **Spec Management**: Create, read, update, and delete specifications for courses.
- **Choice Assignment**: Automatically assign specs to students based on their choices and available places.
- **Database Seeding**: Pre-populate the database with sample data for testing.

## Technologies Used

- **Java**: Programming language used for the application.
- **Spring Boot**: Framework for building the application.
- **JPA (Java Persistence API)**: For data access and manipulation.
- **Hibernate**: ORM (Object-Relational Mapping) tool for database interactions.
- **PostgreSQL**: Database used for storing application data.
- **Maven**: Build automation tool for managing project dependencies.

## Getting Started

### Prerequisites

- Java 23
- Maven
- PostgreSQL
- An IDE (VSCODE will do)

### Installation

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/yahiaboudah/studentmanager.git
   cd student-manager
   ```

2. **Set Up PostgreSQL**:
   - Create a new database for the application.
   - Update the `application.properties` file with your database credentials.

3. **Build the Application**:
   ```bash
   mvn clean install
   ```

4. **Run the Application**:
   ```bash
   mvn spring-boot:run
   ```

5. **Access the API**:
   - The application will run on `http://localhost:8081`.
   - Use tools like Postman or cURL to interact with the API.

## API Endpoints

### Student Endpoints
- **Get All Students**: 
  - `GET /api/student`
- **Get Student by ID**: 
  - `GET /api/student/{id}`
- **Create Student**: 
  - `POST /api/student`
- **Update Student**: 
  - `PUT /api/student/{id}`
- **Delete Student**: 
  - `DELETE /api/student/{id}`
- **Search Students**: 
  - `GET /api/student/search?firstName={firstName}&lastName={lastName}`
- **Full Search Students**: 
  - `GET /api/student/fullSearch?firstName={firstName}&lastName={lastName}&number={number}`
- **Search by Student Number**: 
  - `GET /api/student/number?num={num}`

### Choice Endpoints
- **Get All Choices**: 
  - `GET /api/choices`
- **Get Choice by ID**: 
  - `GET /api/choices/{id}`
- **Create Choice**: 
  - `POST /api/choices`
- **Update Choice**: 
  - `PUT /api/choices/{id}`
- **Delete Choice**: 
  - `DELETE /api/choices/{id}`
- **Delete All Choices by Student ID**: 
  - `DELETE /api/choices/student/{studentId}`
- **Get All Choices by Student ID**: 
  - `GET /api/choices/student/{studentId}`
- **Assign Specs**: 
  - `GET /api/choices/assign`
- **Refresh Choices**: 
  - `GET /api/choices/refresh`

### Spec Endpoints
- **Get All Specs**: 
  - `GET /api/spec`
- **Get Spec by ID**: 
  - `GET /api/spec/{id}`
- **Create Spec**: 
  - `POST /api/spec`
- **Update Spec**: 
  - `PUT /api/spec/{id}`
- **Delete Spec**: 
  - `DELETE /api/spec/{id}`
- **Search Specs**: 
  - `GET /api/spec/search?name={name}`

## Database Seeder

The application includes a `DatabaseSeeder` class that populates the database with initial data. This can be useful for testing and development purposes.

## Contributing

Contributions are welcome! If you have suggestions for improvements or new features, please open an issue or submit a pull request.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments

- To the teacher who assigned this.
