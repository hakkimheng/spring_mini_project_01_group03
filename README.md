# 📝 Mini Project 001

A simple **Article Management API** built with Spring Boot that supports
authentication, user management, articles, comments, and categories.

------------------------------------------------------------------------

## 👥 Team Members

-   **Heng Chakriya**   →   (**Category-controller**)
-   **Keo Kimleang**    →   (**Auth-controller**)
-   **Hak Kimheng**     →   (**Comment-controller**)
-   **Oeng Sikeat**     →   (**Article-controller**)
-   **Sroun Davit**     →   (**User-controller**)
-   **Y Chanphyrat**    →   (**Bookmark-controller**)

------------------------------------------------------------------------

## 🚀 Features

### 🔐 Authentication

-   `POST /api/v1/auth/register` → Register a new user
-   `POST /api/v1/auth/login` → Login with email and password
-   `POST /api/v1/auth/refresh` → Refresh access token

------------------------------------------------------------------------

### 👤 User Information

-   `GET /api/v1/users` → Get current user (all roles)
-   `PUT /api/v1/users` → Update current user (all roles)

------------------------------------------------------------------------

### 💬 Comment

-   `GET /api/v1/comment/{comment-id}` → Get your own comment by ID
-   `PUT /api/v1/comment/{comment-id}` → Update your own comment
-   `DELETE /api/v1/comment/{comment-id}` → Delete your own comment

------------------------------------------------------------------------

### 🔖 Bookmark

-   `GET /api/v1/bookmarks` → Get all articles which has added bookmark by current user id
-   `POST /api/v1/bookmarks/{article-id}` → Add bookmark on article
-   `DELETE /api/v1/bookmarks/{article-id}` → Delete bookmark on article

------------------------------------------------------------------------

### 📂 Category (AUTHOR only)

-   `GET /api/v1/categories/{categoryId}` → Get category by ID
-   `PUT /api/v1/categories/{categoryId}` → Update category
-   `DELETE /api/v1/categories/{categoryId}` → Delete category
-   `GET /api/v1/categories` → Get all categories
-   `POST /api/v1/categories` → Create new category

------------------------------------------------------------------------

### 📰 Article

-   `GET /api/v1/articles/{articleId}` → Get article by ID (all roles)
-   `PUT /api/v1/articles/{articleId}` → Update article (AUTHOR only)
-   `DELETE /api/v1/articles/{articleId}` → Delete article (AUTHOR
    only)\
-   `GET /api/v1/articles` → Get all articles (all roles)
-   `POST /api/v1/articles` → Create new article (AUTHOR only)
-   `GET /api/v1/articles/{article-id}/comments` → Get comments for
    article (all roles)\
-   `POST /api/v1/articles/{article-id}/comments` → Add comment to
    article (all roles)

------------------------------------------------------------------------

## 🛠️ Tech Stack

-   **Java Spring Boot** (Backend)
-   **Spring Security + JWT** (Authentication & Authorization)
-   **redis** (Database catching)
-   **docker compose** (Containerization)
-   **RESTful API** 

------------------------------------------------------------------------

## 📦 Installation & Run

``` bash
# Clone repository
git clone https://github.com/hakkimheng/spring_mini_project_01_group03
cd spring_mini_project_01_group03
#open docker desktop
docker compose up -d
# Run the application
./gradlew bootRun
```

------------------------------------------------------------------------

## 🔑 Roles & Access Control

-   **AUTHOR**: Can manage categories and articles.\
-   **USER**: Can read articles and post comments.

------------------------------------------------------------------------

## 📌 Submission Notes

✅ Completed and submitted on time by **Group 03**.

---

