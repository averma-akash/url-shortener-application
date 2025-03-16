# url-shortener-application
A Spring Boot-based URL Shortener Service similar to Bit.ly that allows users to shorten long URLs, store them in Oracle DB, cache results in Redis, and secure API access using OAuth2 and JWT. The service also integrates with the ELK stack for logging and monitoring.
## 🚀 Features
✔ Generate short URLs like https://short.ly/abc123<br />
✔ Store URLs in Oracle DB with caching in Redis<br />
✔ OAuth2 + JWT security for API protection<br />
✔ ELK Stack integration for centralized logging<br />
✔ Swagger UI for API testing<br />
## 🛠️ Tech Stack
- Spring Boot 3<br />
- Redis (for caching)<br />
- Oracle DB (persistent storage)<br />
- JWT & OAuth2 (authentication)<br />
- ELK Stack (logging & monitoring)<br />
- Swagger (API documentation)<br />
- Docker & Docker Compose (containerized environment)<br />
# 🖥️ Setup & Installation
## 1️⃣ Prerequisites
- Ensure you have installed:
- ✅ JDK 17
- ✅ Maven
- ✅ Oracle DB (Local installation, not Docker)
- ✅ Docker & Docker Compose

## 2️⃣ Clone the Repository
- git clone https://github.com/your-username/url-shortener-service.git
- cd url-shortener-service
