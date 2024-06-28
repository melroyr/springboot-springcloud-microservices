# Microservices Architecture with Spring Boot and Spring Cloud

This project showcases a microservices architecture built with Spring Boot and Spring Cloud. It comprises five distinct services designed to work together seamlessly. Each service is independently deployable and interacts with others through RESTful APIs, with service discovery, load balancing, and security features provided by Spring Cloud components.

## Overview of Services

### 1. Discovery Server
- **Function**: A central registry for service discovery.
- **Technology**: Eureka Server.
- **Responsibilities**: Registers all services and enables dynamic discovery of services, allowing for load balancing and failover.

- ### 2. API Gateway
- **Function**: Acts as a single entry point for all external requests.
- **Technology**: Spring Cloud Gateway.
- **Responsibilities**: Routes requests to appropriate services, providing security and load balancing.

### 3. Product Service
- **Function**: Manages product information.
- **Technology**: Spring Boot, MongoDB.
- **Responsibilities**: Provides CRUD operations for product data.

### 4. Order Service
- **Function**: Handles order creation, processing, and persistence.
- **Technology**: Spring Boot, PostgreSQL.
- **Responsibilities**: Manages orders and interacts with the Store Service to check product availability.

### 5. Store Service
- **Function**: Tracks product stock levels.
- **Technology**: Spring Boot, PostgreSQL.
- **Responsibilities**: Manages stock levels for products, ensuring orders can be fulfilled.

## Project Structure

The project is organized into multiple modules, each representing a specific service:

- **discovery-server**: Eureka Server configuration and security setup.
- **api-gateway**: API Gateway configuration including routing rules and security settings.
- **product-services**: Product Service implementation including controllers, models, repository, and service logic.
- **order-services**: Order Service implementation including controllers, models, repository, and service logic. Includes a WebClientConfig class for communication with the Store Service.
- **store-services**: Store Service implementation including controllers, models, repository, and service logic.

## Setup and Execution

### Prerequisites
- **Java**: 21
- **Maven**: 3.x
- **MongoDB**: 4.x
- **PostgreSQL**: 15.x

### Clone the Repository
```sh
git clone https://github.com/your-username/microservices.git
```

### Build and Run
Navigate to each module directory and run:
```sh
mvn spring-boot:run
```

#### Order of Starting Services
1. Discovery Server (`discovery-server` module)
2. API Gateway (`api-gateway` module)
3. Product Service (`product-services` module)
4. Store Service (`store-services` module)
5. Order Service (`order-services` module)

## Features

- **Service Discovery**: Utilizes Eureka Server for service registration and discovery.
- **Security**: Discovery Server secured with basic authentication.
- **Load Balancing**: Provided by the API Gateway for routing requests to backend services.
- **Database Integration**: Services use MongoDB or PostgreSQL for data storage.
- **RESTful APIs**: Expose RESTful endpoints for interaction.

## Usage

### Access API Gateway
Interact with the API Gateway through the defined endpoints once all services are running.

#### Product Service
- **Create Product**:
  ```sh
  curl -X POST -H "Content-Type: application/json" -d '{ "name": "iPhone 14 Pro", "description": "Apple's latest flagship phone", "price": 1199.99 }' http://localhost:8080/api/product
  ```

- **Get All Products**:
  ```sh
  curl -X GET http://localhost:8080/api/product
  ```

#### Store Service
- **Check Stock**:
  ```sh
  curl -X GET http://localhost:8080/api/store?skuCode=iphone_13&skuCode=iphone_13_red
  ```

#### Order Service
- **Place Order**:
  ```sh
  curl -X POST -H "Content-Type: application/json" -d '{ "orderLineItemsList": [{ "skuCode": "iphone_13", "price": 1000.00, "quantity": 1 }] }' http://localhost:8080/api/order
  ```

## Future Enhancements

- **Authentication and Authorization**: Implement robust security measures like OAuth2.
- **Circuit Breaker**: Introduce resilience patterns like Hystrix to handle service failures.
- **Messaging**: Utilize message queues for asynchronous communication between services.
- **Monitoring and Logging**: Implement centralized logging and monitoring systems for improved observability.
- **Testing**: Enhance test coverage with integration and end-to-end tests.

## Conclusion

This microservices project demonstrates a foundational setup for building distributed applications with Spring Boot and Spring Cloud. It provides essential features for service discovery, load balancing, database integration, and API Gateway configuration. The architecture is modular and extensible, allowing for the addition of more advanced features and adaptation to specific application requirements.

Feel free to explore the project and extend its capabilities to suit your needs.
