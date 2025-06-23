# E-Commerce API (Work in progress)

A Spring Boot backend API for an e-commerce platform. Features include user registration/login with JWT authentication, product management, shopping cart, and Stripe payment integration.

## Features

- User registration and login (JWT-based authentication)
- Product CRUD (admin only for create/update/delete)
- Product search and listing
- Shopping cart (add/remove/view items)
- Stripe payment integration for checkout
- Admin endpoints for product/inventory management

## Prerequisites

- Java 21+
- Maven 3.9+
- (Optional) Docker (for running Postgres)
- Stripe account (for real payments; test key is NOT included)

## Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/yourusername/ecommerce-api.git
cd ecommerce-api
```

### 2. Configure the Database

By default, the app uses an in-memory H2 database.  
To use Postgres, update `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
spring.datasource.username=postgres
spring.datasource.password=password
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
```

You can start a Postgres instance with Docker:

```bash
docker-compose up -d
```

### 3. Stripe Configuration

Include your own stripe test key in `application.properties` to test payment.
Set this inside your `application.properties`
```
stripe.secretKey=[YOUR_STRIPE_SECRET_KEY] 
```
### 4. Build and Run

```bash
./mvnw spring-boot:run
```

The API will be available at `http://localhost:8080`.

### 5. API Endpoints

#### User Authentication

- `POST /api/user/register`  
  Register a new user.  
  Body: `{ "username": "...", "email": "...", "password": "..." }`

- `POST /api/user/login`  
  Login and receive user info.  
  Body: `{ "email": "...", "password": "..." }`

- `POST /api/user/generateToken`  
  Get JWT token for authentication.  
  Body: `{ "email": "...", "password": "..." }`  
  Response: JWT token string

#### Products

- `GET /api/products/allProducts`  
  List all products

- `GET /api/products/{id}`  
  Get product by ID

- `POST /api/products/create`  
  (Admin) Create a product

- `PUT /api/products/update`  
  (Admin) Update a product

- `DELETE /api/products/delete/{id}`  
  (Admin) Delete a product

- `GET /api/products/search?prefix=abc`  
  Search products by name prefix

#### Cart (Requires JWT)

Include `Authorization: Bearer <token>` header.

- `GET /api/cart/findAll`  
  View your cart

- `POST /api/cart/add/{productId}`  
  Add product to cart  
  Body: `{ "quantity": 2 }`

- `POST /api/cart/remove/{productId}`  
  Remove product from cart

#### Checkout

- `POST /api/checkout`  
  Initiate Stripe checkout  
  Body: `{ "amount": 1000, "quantity": 1, "name": "Product", "currency": "usd" }`  
  _Note: Integration of the user cart into the checkout body is still in progress. Currently, you must specify the product details manually in the request body._

## Testing

You can use [Postman](https://www.postman.com/) or [curl](https://curl.se/) to test the endpoints.

Example: Register, login, and get JWT token

```bash
curl -X POST http://localhost:8080/api/user/register \
  -H "Content-Type: application/json" \
  -d '{"username":"testuser","email":"test@example.com","password":"password"}'

curl -X POST http://localhost:8080/api/user/generateToken \
  -H "Content-Type: application/json" \
  -d '{"email":"test@example.com","password":"password"}'
```

Use the returned token for authenticated requests:

```bash
curl -H "Authorization: Bearer <token>" http://localhost:8080/api/cart/findAll
```

## H2 Database Console

Access at [http://localhost:8080/h2-console](http://localhost:8080/h2-console)  
JDBC URL: `jdbc:h2:mem:mydb`  
User: `sa`  
Password: (leave blank)

## Notes

- Only admin users should access product management endpoints.
- For production, use a persistent database and secure your Stripe keys.

---
