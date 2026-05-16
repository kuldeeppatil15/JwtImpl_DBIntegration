# Spring Security JWT Authentication

Features
- User Registration
- JWT Authentication
- Role-Based Authorization
- Spring Security
- MySQL Integration
- Exception Handling


Tech Stack
| Technology      | Usage          |
| --------------- | -------------- |
| Spring Boot     | Backend        |
| Spring Security | Security       |
| JWT             | Authentication |
| MySQL           | Database       |
| JPA/Hibernate   | ORM            |


API Endpoints
| API                 | Description   |
| ------------------- | ------------- |
| POST /auth/register | Register user |
| POST /auth/login    | Login         |
| GET /profile        | Current user  |
| GET /admin          | Admin access  |


JWT Flow
- User logs in
- JWT generated
- Client stores token
- Token sent in Authorization header
- JWT filter validates token


What Next?
-PHASE 4: Advanced + Industry-Level Security