# Hnam Courseware - Backend API for Online Learning

This is a personal project I created to show my backend development skills using **Java** and **Spring Boot**. It is a RESTful API for an online learning platform.

The system supports many types of users (Students, Teachers, and Admins), has a smart search system, and follows good coding practices.

---

## Main Features

* **Login and User Roles:**
  * Users can register and log in safely using **JWT (JSON Web Tokens)**.
  * Each user role (USER, INSTRUCTOR, ADMIN) has different permissions, managed by **Spring Security**.

* **Course Management:**
  * Teachers can create, update, and delete their courses.
  * There is a **powerful search API** that lets users search courses by title, level, price, or rating using **JPA Specification**.
  * The system calculates and stores course ratings and review counts automatically to improve speed.

* **Enroll and Learn:**
  * Students can enroll in a course, leave a course, and update their learning progress.
  * Students and teachers can see different enrollment data using special APIs:
    * `/api/enrollments/me` (for students)
    * `/api/instructor/courses/{id}/enrollments` (for teachers)

* **Review and Rating:**
  * Only students who are enrolled can write reviews and give ratings.
  * Anyone can view course reviews with pagination and filters.

* **Clean Code Structure:**
  * The project uses a clear structure: Controller → Service → Repository.
  * All errors are handled in one place using `@ControllerAdvice` to keep responses consistent.
  * It uses DTOs (Data Transfer Objects) to make sure the API is clean and safe.

---

## Technologies Used

* **Backend:** Java 17, Spring Boot 3  
* **Database:** PostgreSQL (via Docker Compose)  
* **Data Access:** Spring Data JPA, Hibernate  
* **Security:** Spring Security 6, JWT  
* **Advanced Search:** JPA Specification  
* **Documentation:** Swagger/OpenAPI 3  
* **Other Tools:** Lombok, ModelMapper  
* **Build Tool:** Maven  

---

## How to Run

### What You Need

* JDK 17 or newer  
* Docker and Docker Compose  

### Steps

1. **Clone the project:**

```bash
git clone https://github.com/lamphnam/HoangNam_Course_BE.git
cd HoangNam_Course_BE
```

2. **Start PostgreSQL database:**

```bash
docker-compose up -d
```

3. **Run the application:**

```bash
cd hnam-courseware
./mvnw spring-boot:run
```

The API will run at: `http://localhost:8080`

---

## API Documentation

After the application is running, you can open the Swagger UI to test the APIs easily.

**Open this link in your browser:**  
[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---
