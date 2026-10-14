# SnippetShare

A web-based code snippet management platform built with Java, Spring Boot, and PostgreSQL. SnippetShare allows users to securely store, organize, edit, and share code snippets online.

## 🚀 Features

- **User Authentication** — Register, log in, and log out securely.
- **Snippet Management** — Create, view, edit, and delete code snippets.
- **Private Snippets** — Manage your own snippets through an authenticated account.
- **Public Sharing** — Generate shareable links for snippets and revoke access when needed.
- **Version History** — Track previous versions of your snippets.
- **Search and Filter** — Find snippets easily.
- **Syntax Highlighting** — View code with improved readability.
- **Persistent Storage** — Store application data in PostgreSQL.

## 🛠️ Technologies Used

- **Backend:** Java, Spring Boot
- **Database:** PostgreSQL
- **Build Tool:** Maven
- **Deployment:** Render
- **Version Control:** Git and GitHub

## 🌐 Live Demo

[Open SnippetShare](https://snippetshare-k1j8.onrender.com)

## 💻 Run Locally

### Prerequisites

- Java 21 or compatible JDK
- Maven
- PostgreSQL

### Setup

1. Clone the repository:

   ```bash
   git clone https://github.com/KJ-tech7/snippetshare.git
   cd snippetshare
   ```

2. Create a PostgreSQL database named `snippetshare`.

3. Configure your database environment variables:

   - `DB_URL` — JDBC connection URL for PostgreSQL
   - `DB_USERNAME` — PostgreSQL username
   - `DB_PASSWORD` — PostgreSQL password

   For a local database, the URL typically looks like:

   `jdbc:postgresql://localhost:5432/snippetshare`

4. Build the application:

   ```bash
   mvn clean package
   ```

5. Run the application:

   ```bash
   java -jar target/*.jar
   ```

6. Open `http://localhost:8080` in your browser.

## 📦 Deployment

The project includes a `Dockerfile` and is deployed using Render with a PostgreSQL database.

## 👨‍💻 Author

**KJ-tech7**

GitHub: [KJ-tech7](https://github.com/KJ-tech7)

---

If you find this project useful, consider giving the repository a star!
