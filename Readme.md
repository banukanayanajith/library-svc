```markdown
# Library Management System 

The Library Management System is a Spring Boot application designed to streamline library operations by managing book inventory and borrowing processes. It provides RESTful APIs for interacting with the library's database, enabling users to:
- Register borrowers (**API endpoint:** `/api/v1/borrower`)
- Add new books (**API endpoint:** `/api/v1/books`)
- Borrow books (**API endpoint:** `/api/v1/library/borrower/{borrowerId}}/borrow/{bookId}}`)
- Return books (**API endpoint:** `api/v1/library/borrower/return/{bookId}`)
- Retrieve information about books and borrowers (**API endpoints:** `/api/v1/books`)

## Project Overview 

This system aims to enhance library efficiency through a digital platform for resource management. Users can interact with the system via a web interface or by integrating with other applications using the exposed APIs.

## Key Features 

- **Borrower Registration:** Users can register by providing personal details like name, email, and contact information.
- **Book Addition:** Librarians can add new books to the library's inventory, specifying title, author, ISBN, and availability status.
- **Book Borrowing:** Borrowers can borrow books by indicating the desired book's ID and their borrower ID.
- **Book Return:** Borrowers can return books when finished reading them.

## Getting Started 
### Prerequisites 

Before running the application, ensure you have the following installed:
- Java Development Kit (JDK) 8 or higher
- Apache Maven
- Docker (optional)

### Installation 

1. Build the project using Maven:
   ```bash
   mvn clean package
   ```
2. Run the application:
   ```bash
   java -jar target/library-svc.jar
   ```

## API Documentation

**Detailed API documentation is available (refer to your specific implementation). Here's a general structure for common library management APIs:**

# Register a New Book
## Successful Request

```bash
curl -X POST http://localhost:8080/api/v1/books \
-H "Content-Type: application/json" \
-d '{
  "isbn": "9781234567891",
  "title": "Effective Javax",
  "author": "Joshua Blochx"
}'
```

# Missing ISBN (Failing Scenario)

```bash
curl -X POST http://localhost:8080/api/v1/books \
-H "Content-Type: application/json" \
-d '{
  "title": "Effective Java",
  "author": "Joshua Bloch"
}'
```

# Invalid ISBN Format (Failing Scenario)

```bash
curl -X POST http://localhost:8080/api/v1/books \
-H "Content-Type: application/json" \
-d '{
  "isbn": "12345",
  "title": "Effective Java",
  "author": "Joshua Bloch"
}'
```

# Missing Title (Failing Scenario)

```bash
curl -X POST http://localhost:8080/api/v1/books \
-H "Content-Type: application/json" \
-d '{
  "isbn": "9781234567890",
  "author": "Joshua Bloch"
}'
```

# Missing Author (Failing Scenario)

```bash
curl -X POST http://localhost:8080/api/v1/books \
-H "Content-Type: application/json" \
-d '{
  "isbn": "9781234567890",
  "title": "Effective Java"
}'
```

# Get a List of All Books
## Successful Request

```bash
curl -X GET http://localhost:8080/api/v1/books?page=0&size=10
```

# Register a Borrower
Register a New Borrower
## Successful Request

```bash
curl -X POST http://localhost:8080/api/v1/borrower \
-H "Content-Type: application/json" \
-d '{
  "name": "John Doe",
  "email": "john.doe@example.com"
}'
```

# Missing Name (Failing Scenario)

```bash
curl -X POST http://localhost:8080/api/v1/borrower \
-H "Content-Type: application/json" \
-d '{
  "email": "john.doe@example.com"
}'
```

# Invalid Email Format (Failing Scenario)

```bash
curl -X POST http://localhost:8080/api/v1/borrower \
-H "Content-Type: application/json" \
-d '{
  "name": "John Doe",
  "email": "john.doeexample.com"
}'
```

# Missing Email (Failing Scenario)

```bash
curl -X POST http://localhost:8080/api/v1/borrower \
-H "Content-Type: application/json" \
-d '{
  "name": "John Doe"
}'
```

# Borrow a Book
## Successful Request

```bash
curl -X POST http://localhost:8080/api/v1/library/borrower/1/borrow/1
```

# Borrowing a Non-Existent Book (Failing Scenario)

```bash
curl -X POST http://localhost:8080/api/v1/library/borrower/1/borrow/999
```

# Borrowing an Already Borrowed Book (Failing Scenario)

```bash
curl -X POST http://localhost:8080/api/v1/library/borrower/2/borrow/1
```

# Return a Book
## Successful Request

```bash
curl -X POST http://localhost:8080/api/v1/library/borrower/return/1
```

# Returning a Non-Existent Book (Failing Scenario)

```bash
curl -X POST http://localhost:8080/api/v1/library/borrower/return/999
```

# Returning a Book Not Borrowed by the Borrower (Failing Scenario)

```bash
curl -X POST http://localhost:8080/api/v1/library/borrower/return/2
```

## Database Setup
The application utilizes a MariaDB database to store borrower and book information. You can set up the database by following these steps:
1. Install MariaDB: Follow the installation instructions for your operating system to install MariaDB.
2. Create a Database: Use a tool like mysql command-line client or a graphical database management tool to create a new database for the application.

**SQL Script (For Reference):**
Your provided SQL script for creating tables:
```sql
create table library_db.book
(
    id          bigint auto_increment
        primary key,
    isbn        varchar(13)          not null,
    title       varchar(255)         not null,
    author      varchar(255)         not null,
    borrowed    tinyint(1) default 0 null,
    borrower_id bigint               null,
    constraint fk_borrower
        foreign key (borrower_id) references library_db.borrower (id)
);

create table library_db.borrower
(
    id    bigint auto_increment
        primary key,
    name  varchar(255) not null,
    email varchar(255) not null
);


```

**Important Note:** Use these SQL scripts with caution. Make sure you back up your existing data before running them.

## Configuration
The application can be configured using the following methods:
1. **Environment Variables:** You can set environment variables to configure certain aspects of the application. Refer to the application documentation for specific environment variables supported.
2. **Application.properties:** The application reads configuration settings from the `application.properties` file located in the `src/main/resources` directory. This file allows you to configure options like database connection details, server port, and logging settings.

## Deployment
The application can be run in two ways:
### 1. Running Without Docker
Follow the installation instructions provided earlier (if applicable) to set up the application without Docker. This typically involves building the project using a build tool like Maven and running the generated executable.

### 2. Running with Docker
The project repository includes Dockerfile and docker-compose.yml files for easy Docker deployment. These files define the steps to build a Docker image containing the application and its dependencies, as well as how to run the application within a Docker container.

**Build the image:**
```bash
docker build -t library-svc:1.0 .
```

**Run the container:**
```bash
docker run -p 8080:8080 library-svc:1.0
```
```