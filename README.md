# 🎥 Movie Insight API

## Overview
Movie Insight is a RESTful API designed to manage a movies. It supports CRUD operations, user authentication, pagination, sorting, and file uploads for movie posters.

## Postman Collection
Explore the API endpoints using the [Movie Insight Postman Collection](https://elements.getpostman.com/redirect?entityId=33641536-d71b116a-d14c-4978-9ec6-326d44604598&entityType=collection). Fork the collection to start testing the API in your own workspace.

## Features
- **Authentication:** Secure login and registration endpoints with JWT-based authorization.
- **Movie Management:** Add, update, delete, and retrieve movies.
- **Pagination & Sorting:** Efficiently fetch and sort movie records.
- **Poster Uploads:** Upload movie posters to the server.

## API Endpoints

### Authentication
- **Login:** `POST /api/v1/auth/login`
- **Register:** `POST /api/v1/auth/register`

### Movie Management
- **Get All Movies:** `GET /api/v1/movie/all`
- **Get Movie by ID:** `GET /api/v1/movie/{id}`
- **Add Movie:** `POST /api/v1/movie/add-movie`
- **Update Movie:** `PUT /api/v1/movie/update/{id}`
- **Delete Movie:** `DELETE /api/v1/movie/delete/{id}`
- **Add Movie List:** `POST /api/v1/movie/add-movielist`

### Pagination & Sorting
- **Fetch Movie Pages:** `GET /api/v1/movie/allMoviePages?pageNumber={number}&pageSize={size}`
- **Sort Movies:** `GET /api/v1/movie/allMoviePagesSort?sortBy={field}&pageSize={size}&dir={asc|dsc}`

## Setup Instructions
1. Clone the repository and set up the Spring Boot application.
4. Navigate to the project directory.
5. Run `mvn clean install`.
6. Use `mvn spring-boot:run` to start the application.
7. Access the API via `http://localhost:8185` or
8. Import the Postman Collection linked above to explore and test the API.
9. Run the application and interact with the API through Postman or any HTTP client.

## Tech Stack
- **Java**
- **Spring Boot**
- **JWT Authentication**
- **Hibernate**
- **MySQL/H2 Database**

## Contributing
Feel free to fork the project, create a new branch, and submit a pull request!

