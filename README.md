# 📚 Library Management System

## 🚀 Overview
This project is a **Library Management System** built with **Java, Spring Boot, and PostgreSQL**.  
It was developed as part of the **CODE81 Mendix Developer Challenge** to demonstrate:

- 📂 Well-structured database design  
- 🌐 RESTful API development  
- 🔐 Role-based access control (RBAC) with secure authentication  
- 🏛️ Clean application architecture and design principles  

The system enables libraries to manage **books, authors, categories, publishers, members, staff users, and borrowing transactions**, with **audit logging** for administrative oversight.

---

## 🏗️ System Architecture
- **Backend:** Java 17, Spring Boot  
- **Security:** Spring Security + JWT Authentication  
- **Database:** PostgreSQL  
- **Build Tool:** Maven  
- **API Testing:** Postman Collection included  

---

## 📊 Database Schema

![Database ERD](https://github.com/user-attachments/assets/2049d7d7-5ddd-4663-9f51-f4050f7bbeee)

### Core Entities
- **Book** → extended metadata (title, ISBN, authors, publisher, categories, language, edition, publication year, summary, cover image)  
- **Author** → supports multiple authors per book  
- **Category** → hierarchical classification (e.g., *Fiction → Sci-Fi*)  
- **Publisher** → publisher information  
- **Member** → library members/borrowers  
- **System User** → administrators, librarians, and staff with RBAC  
- **Borrowing Transaction** → book borrowing & returning flow  
- **User Activity Log** → system actions auditing  

---

## 🔐 Security & Role-Based Access

### Roles
- **Administrator**
  - Full system access  
  - Manage users, books, members, and logs  
- **Librarian**
  - Manage books, authors, categories, publishers, members  
  - Borrow/return transactions  
- **Staff**
  - Issue and return books  
  - Search/view books  
- **Member**
  - View catalog and borrowing history  

✅ Passwords are securely stored using **bcrypt hashing**.

---

## 📡 REST API Endpoints

### Authentication & Users
- `POST /api/auth/register` → Register a new system user (Admin only)  
- `POST /api/auth/login` → Authenticate & receive JWT  
- `GET /api/users` → List all users (Admin only)  
- `GET /api/users/{id}` → Get user details  
- `PUT /api/users/{id}` → Update user (Admin only)  
- `DELETE /api/users/{id}` → Deactivate user (Admin only)  

### Books
- `POST /api/books` → Add a book (Librarian/Admin)  
- `GET /api/books` → List all books  
- `GET /api/books/{id}` → Book details  
- `PUT /api/books/{id}` → Update book  
- `DELETE /api/books/{id}` → Remove book (Admin only)  

### Borrowing
- `POST /api/transactions/borrow` → Borrow a book  
- `POST /api/transactions/return/{id}` → Return book  
- `GET /api/transactions` → List all transactions  
- `GET /api/transactions/member/{memberId}` → Member borrowing history  

### Logs
- `GET /api/logs` → List system user activity logs (Admin only)  

---

## 📌 Postman Collection
A full **Postman Collection** is included in:  
