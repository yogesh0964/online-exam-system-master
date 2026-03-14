# 📚 EduExam — Online Examination System

<p align="center">
  <img src="https://img.shields.io/badge/Java-17-blue?style=for-the-badge&logo=java&logoColor=white"/>
  <img src="https://img.shields.io/badge/Spring%20Boot-3.2-brightgreen?style=for-the-badge&logo=springboot&logoColor=white"/>
  <img src="https://img.shields.io/badge/MySQL-8.0-orange?style=for-the-badge&logo=mysql&logoColor=white"/>
  <img src="https://img.shields.io/badge/JWT-Auth-purple?style=for-the-badge&logo=jsonwebtokens&logoColor=white"/>
  <img src="https://img.shields.io/badge/Deployed-Railway-black?style=for-the-badge&logo=railway&logoColor=white"/>
</p>

<p align="center">
  A full-stack <strong>Online Examination Platform</strong> built with Spring Boot 3.2 + MySQL,
  featuring JWT authentication, REST API, role-based access control, and a modern dark-themed UI.
</p>

<p align="center">
  🔗 <strong>Live Demo:</strong> <a href="https://online-exam.up.railway.app">your-app.railway.app</a>
</p>

---

## 📸 Screenshots

> Admin Dashboard
![Admin Dashboard](screenshots/admin-dashboard.png)

> Exam Page
![Exam Page](screenshots/exam-page.png)

---

## ✨ Features

### 👨‍🏫 Admin Panel
- Stats dashboard — total students, exams, questions, submissions (Chart.js)
- Full exam management — create, edit, delete with duration settings
- MCQ question bank — add/edit/delete with marks per question
- View all student submissions with scores, percentage, pass/fail
- Manage students — reset passwords, delete accounts
- Color-coded dark UI for all admin pages

### 👨‍🎓 Student Portal
- Secure registration and login
- Take timed exams — live countdown timer with auto-submit
- Question palette — jump to any question, answered/unanswered at a glance
- Instant results — score, percentage, pass/fail
- Detailed review — correct answers highlighted green, wrong answers red
- Profile management — update details, upload profile picture, change password
- Score history chart on dashboard

### 🔐 Security & API
- Spring Security with role-based access (`ROLE_ADMIN` / `ROLE_STUDENT`)
- **JWT Authentication** for all REST API endpoints
- BCrypt password hashing
- Session-based auth for Thymeleaf web UI

---

## 🌐 REST API Endpoints

All API endpoints require JWT token except `/api/auth/login`.

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| POST | `/api/auth/login` | Get JWT token | Public |
| GET | `/api/v1/exams` | List all exams | Bearer Token |
| GET | `/api/v1/exams/{id}` | Exam details | Bearer Token |
| GET | `/api/v1/me` | Logged-in user info | Bearer Token |

### Example — Get Token
```bash
curl -X POST https://your-app.railway.app/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin@gmail.com","password":"adminpass"}'
```

Response:
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "role": "ROLE_ADMIN",
  "username": "admin@gmail.com"
}
```

### Example — Use Token
```bash
curl -X GET https://your-app.railway.app/api/v1/exams \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9..."
```

---

## 🛠️ Tech Stack

| Layer | Technology |
|-------|------------|
| Language | Java 17 |
| Backend | Spring Boot 3.2 |
| Security | Spring Security 6 + JWT (jjwt 0.11.5) |
| Frontend | Thymeleaf + Bootstrap 5 + Chart.js |
| Database | MySQL 8.0 |
| ORM | Spring Data JPA / Hibernate |
| Build | Maven |
| Deployment | Railway.app |

---

## 🚀 Run Locally

### Prerequisites
- Java 17+
- MySQL 8+
- Maven 3.8+

### Steps

```bash
# 1. Clone karo
git clone https://github.com/your-username/online-exam-system.git
cd online-exam-system

# 2. MySQL database banao
mysql -u root -p -e "CREATE DATABASE examdb;"

# 3. application.properties update karo
# src/main/resources/application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/examdb
spring.datasource.username=root
spring.datasource.password=your_password
jwt.secret=404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970

# 4. Run karo
./mvnw spring-boot:run
```

App open hogi: **http://localhost:8080**

### Default Admin User (manually insert karo)
```sql
INSERT INTO users (username, password, role, full_name)
VALUES (
  'admin@gmail.com',
  '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi',
  'ROLE_ADMIN',
  'Admin'
);
-- Password: adminpass
```

---

## 📁 Project Structure

```
src/main/java/com/example/exam/
│
├── config/
│   ├── SecurityConfig.java          # Spring Security + JWT filter chain
│   ├── JwtUtil.java                 # Token generate & validate
│   ├── JwtFilter.java               # Per-request JWT verification
│   └── CustomAuthSuccessHandler.java
│
├── controller/
│   ├── AdminController.java         # Admin web pages
│   ├── StudentController.java       # Student web pages
│   ├── ExamController.java          # Exam taking + submit
│   ├── ReviewController.java        # Answer review
│   ├── JwtAuthController.java       # POST /api/auth/login
│   └── ExamApiController.java       # REST /api/v1/* endpoints
│
├── model/
│   ├── User.java
│   ├── Exam.java
│   ├── Question.java
│   ├── ExamResult.java
│   └── ExamAnswer.java
│
├── repository/                      # Spring Data JPA interfaces
│
└── service/
    ├── UserService.java
    ├── UserServiceImpl.java
    ├── CustomUserDetailsService.java
    └── FileStorageService.java

src/main/resources/
├── templates/
│   ├── admin/          # 9 admin pages (dark theme)
│   ├── student/        # 6 student pages (dark theme)
│   ├── fragments.html  # Navbar
│   ├── login.html
│   ├── register.html
│   └── index.html
└── application.properties
```

---

## 🗺️ Roadmap

- [x] Role-based authentication (Admin + Student)
- [x] Full exam + question management
- [x] Timed exam with auto-submit
- [x] JWT REST API
- [x] Dark theme UI redesign
- [ ] Question randomization (anti-cheating)
- [ ] Email notification on result
- [ ] PDF result download
- [ ] Exam scheduling (start/end date)
- [ ] Docker support

---

## 👨‍💻 Author

**Yogesh Rajput**
- 📧 yogirajput0964@gmail.com
- 💼 [LinkedIn](https://www.linkedin.com/in/yogesh-rajput0964)
- 🐙 [GitHub](https://github.com/yogesh0964)

---

## 📄 License

This project is open source under the [MIT License](LICENSE).

---

<p align="center">Made with ❤️ using Spring Boot</p>