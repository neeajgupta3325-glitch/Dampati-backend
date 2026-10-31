# Dampati Backend

Backend API for **Dampati**, a matrimonial web application built using Java and Spring Boot.

## 🚀 Project Overview

Dampati is a matrimonial platform where users can create profiles, 
log in securely, manage their profile information, and find potential matches.

This repository contains the backend REST APIs and business logic of the application.

## 🛠️ Technologies Used

- Java 21
- Spring Boot
- Spring Web
- Spring Data JPA
- Hibernate
- Spring Security
- JWT Authentication
- BCrypt Password Encryption
- MySQL
- Maven
- Postman

## 📁 Main Backend Structure

```text
src/main/java/org/dampati/backend
│
├── config
├── controller
├── entity
├── repository
├── security
└── service
```

## 🔐 Security

The backend uses:

- Spring Security
- JWT-based authentication
- BCrypt password encryption
- CORS configuration

User passwords are encrypted before being stored in the database.

## 👤 Current Features

- User Registration
- User Login
- JWT Token Generation
- Password Encryption
- Get User Profile
- Edit User Profile
- Reset Password
- MySQL Database Integration
- REST API Testing with Postman

## 🔗 API Endpoints

### Register User

```http
POST /api/users
```

### Login
```http
POST /api/users/login
```
### Update User Profile

```http request
GET /api/users/{id}
```

```http
PUT /api/users/{id}
```
### Reset Password
```http request
PUT /api/users/reset-password/{id}
```
### 🗄️ Database
The backend uses MySQL with Spring Data JPA and Hibernate.
```http request
dampati
```
Database credentials are kept in the local application.properties file and are not committed to GitHub.
## ▶️ Running the Backend

### 1. Clone the repository

```bash
git clone https://github.com/neeajgupta3325-glitch/Dampati-backend.git
```
# Dampati — Backend Service

A Java Full Stack Matrimonial Project built with Spring Boot, MySQL, and integrated with a React frontend.

---

## 🛠️ Tech Stack

- **Language:** Java
- **Framework:** Spring Boot
- **Database:** MySQL
- **Frontend:** React.js
- **API Testing:** Postman

---

## ▶️ Running the Backend

### 1. Clone the repository
```bash
git clone https://github.com/neeajgupta3325-glitch/Dampati-backend.git
```

### 2. Open the project
Open the project root directory in **IntelliJ IDEA** (or your preferred IDE).

### 3. Create the database
Create a MySQL database named:
```sql
CREATE DATABASE dampati;
```

### 4. Configure database credentials
Update your local database configuration inside:
`src/main/resources/application.properties`

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/dampati?useSSL=false&serverTimezone=UTC
spring.datasource.username=your_mysql_username
spring.datasource.password=your_mysql_password
```

### 5. Run the application
Run the Spring Boot main application class from IntelliJ IDEA or via terminal:
```bash
./mvnw spring-boot:run
```

**Backend Base URL:**
```text
http://localhost:8080
```

---

## 🧪 API Testing

The backend REST APIs are documented and tested using **Postman**.

---

## 🔮 Future Features

- [ ] OTP Verification
- [ ] Partner Preferences
- [ ] Profile Search & Advanced Filters
- [ ] Matchmaking Algorithm
- [ ] Like / Express Interest
- [ ] Shortlist Profiles
- [ ] Real-time Chat
- [ ] Notifications System
- [ ] Subscription & Payment Gateway Integration
- [ ] Admin Dashboard
- [ ] Report / Block User
- [ ] User Profile Verification
- [ ] Cloud Image Storage & Upload

---

## 👨‍💻 Developer

**Dampati — Java Full Stack Matrimonial Project**  
Built using Java, Spring Boot, React, and MySQL.
