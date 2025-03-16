# **URL Shortener Service**  
A Spring Boot-based URL Shortener Service similar to **Bit.ly** that allows users to shorten long URLs, store them in **Oracle DB**, cache results in **Redis**, and secure API access using **OAuth2 and JWT**. The service also integrates with the **ELK stack** for logging and monitoring.  

---

## **🚀 Features**  
✔ **Generate short URLs** like `https://short.ly/abc123`  
✔ **Store URLs in Oracle DB** with caching in Redis  
✔ **OAuth2 + JWT security** for API protection  
✔ **ELK Stack integration** for centralized logging  
✔ **Swagger UI** for API testing  

---

## **🛠️ Tech Stack**  
- **Spring Boot 3**  
- **Redis** (for caching)  
- **Oracle DB** (persistent storage)  
- **JWT & OAuth2** (authentication)  
- **ELK Stack** (logging & monitoring)  
- **Swagger** (API documentation)  
- **Docker & Docker Compose** (containerized environment)  

---

## **📂 Project Structure**
```
📦 url-shortener-service
 ┣ 📂 src
 ┃ ┣ 📂 main
 ┃ ┃ ┣ 📂 java/com/example/urlshortener
 ┃ ┃ ┃ ┣ 📂 controller  # REST Controllers
 ┃ ┃ ┃ ┣ 📂 service     # Business Logic
 ┃ ┃ ┃ ┣ 📂 repository  # Database Layer
 ┃ ┃ ┃ ┣ 📂 model       # Entity Models
 ┃ ┃ ┃ ┣ 📂 config      # Security, Redis, and Swagger Config
 ┣ 📜 pom.xml
 ┣ 📜 application.yml
 ┣ 📜 docker-compose.yml
 ┣ 📜 Dockerfile (Optional)
 ┣ 📜 README.md
```

---

## **🖥️ Setup & Installation**

### **1️⃣ Prerequisites**  
Ensure you have installed:  
✅ **JDK 17**  
✅ **Maven**  
✅ **Oracle DB (Local installation, not Docker)**  
✅ **Docker & Docker Compose**  

### **2️⃣ Clone the Repository**
```sh
git clone https://github.com/averma-akash/url-shortener-application.git
cd url-shortener-service
```
## **🚀 Running the Application**

### **1️⃣ Start Required Services (Redis & ELK) using Docker**
```sh
docker-compose up -d
```
- **Redis** → Runs on `localhost:6379`  
- **Elasticsearch** → Runs on `localhost:9200`  
- **Kibana** → Runs on `localhost:5601`  

Verify running containers:  
```sh
docker ps
```

### **2️⃣ Start the Spring Boot Application**
```sh
mvn spring-boot:run
```

### **3️⃣ Verify the Service**
**Swagger UI:** [`http://localhost:8080/swagger-ui.html`](http://localhost:8080/swagger-ui.html)  
**API Health Check:** [`http://localhost:8080/actuator/health`](http://localhost:8080/actuator/health)  

---

## **📜 API Endpoints**

| HTTP Method | Endpoint           | Description               | Authentication |
|------------|--------------------|---------------------------|---------------|
| **POST**   | `/api/shorten`      | Shorten a long URL        | 🔒 OAuth2 + JWT |
| **GET**    | `/api/{shortCode}`  | Retrieve the original URL | 🔓 Public |
| **GET**    | `/swagger-ui.html`  | Open API Documentation    | 🔓 Public |

Example request for **shortening a URL**:
```json
POST /api/shorten
Content-Type: application/json
{
    "longUrl": "https://www.example.com/some-long-url"
}
```
Example response:
```json
{
    "shortUrl": "https://short.ly/abc123"
}
```

---

## **🛠️ Stopping & Removing Containers**
```sh
docker-compose down
```

---

## **📌 Additional Commands**
### **🔹 Check Redis**
```sh
docker exec -it redis-container redis-cli
PING
```
✅ Response should be `PONG`

### **🔹 Check ELK Stack**
- **Elasticsearch:** [`http://localhost:9200`](http://localhost:9200)  
- **Kibana Dashboard:** [`http://localhost:5601`](http://localhost:5601)  
---
