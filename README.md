
# contoller
Spring Boot Customer Data API

Overview

This project is a Spring Boot application that processes customer data from a multi-line string input and generates various statistics. It provides a RESTful API to retrieve these statistics, such as the number of unique customers per contract, unique customers per geo-zone, average build duration per geo-zone, and a list of unique customers per geo-zone.

The application follows SOLID principles, uses Basic Authentication with Role-Based Access Control (RBAC), and is designed for scalability with a focus on test-driven development.

---

Project Structure


springboot-customer-data-api
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── example
│   │   │           ├── SpringbootCustomerDataApiApplication.java      # Main Application Class
│   │   │           ├── controller                                     # REST Controllers
│   │   │           │   └── CustomerDataController.java                # Controller for API endpoints
│   │   │           ├── service                                        # Service layer
│   │   │           │   ├── CustomerService.java                       # Service interface
│   │   │           │   ├── CustomerServiceImpl.java                   # Service implementation
│   │   │           ├── parser                                         # Data Parsers
│   │   │           │   ├── DataParser.java                            # Interface for data parsers
│   │   │           │   ├── CsvDataParser.java                         # CSV parser implementation
│   │   │           │   └── DataParserFactory.java                     # Factory for selecting data parser
│   │   │           ├── strategy                                       # Strategy for generating reports
│   │   │           │   ├── ReportStrategy.java                        # Report strategy interface
│   │   │           │   └── GeoZoneReportStrategy.java                 # Geo-zone-specific report strategy
│   │   │           ├── model                                          # Data models
│   │   │           │   └── CustomerData.java                          # Customer data model
│   │   │           ├── config                                         # Configuration files
│   │   │           │   └── SecurityConfig.java                        # Security configuration (RBAC, Basic Auth)
│   │   │           ├── exception                                      # Custom exception handling
│   │   │           │   ├── GlobalExceptionHandler.java                # Global exception handler
│   │   │           │   └── InvalidInputException.java                 # Custom exception class for invalid input
│   ├── test
│   │       └── java
│   │           └── com
│   │               ├── service                                        # Unit tests
│   │               │   └── CustomerServiceTests.java                  # Tests for CustomerService
│   │               └── SpringbootCustomerDataApiApplicationTests.java  # Application context test
└── pom.xml                                                             # Maven build configuration

 Features

RESTful API:
  - POST request allows users to upload raw customer data and receive a statistical report.
  
Data Processing:
  - The API calculates:
    - Number of unique customer IDs per contract.
    - Number of unique customer IDs per geo-zone.
    - Average build duration per geo-zone.
    - List of unique customer IDs per geo-zone.

Security:
  - Uses Basic Authentication.
  - Role-Based Access Control (RBAC) limits access to certain endpoints to the `ADMIN` role.

- Custom Exception Handling:
  - Global exception handling with InvalidInputException to manage invalid input data.

- SOLID Principles and Design Patterns:
  - Implements Factory Pattern for data parsers.
  - Implements Strategy Pattern for generating reports.

---

Getting Started

 Prerequisites

- Java 17 or above
- Maven 3.x
- Any REST API client like Postman or Insomnia

Installation

1. Clone the repository:


   git clone https://github.com/Hksat4/productService.git
  

2. Build the project using Maven:

  
   mvn clean install


3. Run the application:

>>>>>>> cecea56ac41cb6d1a30e4fc18ceedd0fe38277f4
 
   mvn spring-boot:run
  

The application will start on `http://localhost:8080` by default.

---

Configuration

Security Configuration

The application uses Basic Authentication with the following default credentials:

- Username: `admin`
- Password: `admin_password`

These credentials are defined in the `SecurityConfig.java` file:

@Bean
public UserDetailsService userDetailsService() {
    UserDetails admin = User.withUsername("admin")
            .password(passwordEncoder().encode("admin_password"))
            .roles("ADMIN")
            .build();

    return new InMemoryUserDetailsManager(admin);
}

You can modify the username, password, and roles as per your requirements.

---

API Endpoints

POST `MyProduct/api/v1/customers/report`

This endpoint accepts a multi-line string of customer data and returns a JSON report based on the input.

- Authentication Required: Yes (Requires `ADMIN` role)
- Content-Type: `text/plain`

Request Body:
2343225,2345,us_east,RedTeam,ProjectApple,3445s
1223456,2345,us_west,BlueTeam,ProjectBanana,2211s
3244332,2346,eu_west,YellowTeam3,ProjectCarrot,4322s
1233456,2345,us_west,BlueTeam,ProjectDate,2221s
3244132,2346,eu_west,YellowTeam3,ProjectEgg,4122s

Example Response:

{
    "uniqueCustomersPerContract": {
        "2345": 3,
        "2346": 2
    },
    "uniqueCustomersPerGeozone": {
        "us_east": 1,
        "us_west": 2,
        "eu_west": 2
    },
    "averageBuildDurationPerGeozone": {
        "us_east": 3445.0,
        "us_west": 2216.0,
        "eu_west": 4222.0
    },
    "uniqueCustomersListPerGeozone": {
        "us_east": ["2343225"],
        "us_west": ["1223456", "1233456"],
        "eu_west": ["3244332", "3244132"]
    }
}
```

Authentication Example:

When using Insomnia, set the Basic Auth header with the following values:

- Username: `admin`
- Password: `admin_password`

---

Testing

The project uses JUnit 5 and Mockito for unit testing. To run the tests, use the following command:


mvn test


Unit tests are located in the `src/test/java` directory and include coverage for the `CustomerServiceImpl` class.


Error Handling

The API includes custom exception handling for invalid input data. When invalid input is provided, such as an empty string, the API returns a `400 Bad Request` with a detailed error message.

 Example Error Response:
json
{
    "timestamp": "2024-10-16T15:00:00",
    "status": 400,
    "error": "Bad Request",
    "message": "Input data cannot be null or empty"
}


---

Future Enhancements

- JWT Authentication**: Replace Basic Authentication with JWT tokens for enhanced security.
- Database Integration: Store customer data in a relational database (e.g., MySQL, PostgreSQL) for long-term storage.
- Swagger Documentation: Add Swagger UI for automatically documenting the API and providing a web-based interface for testing endpoints.


This `README.md` provides a complete guide for setting up, running, and using the Spring Boot Customer Data API. Let me know if you need any more information or modifications!
