# Loan Precheck App

Full-stack web application built with **React (frontend)** and **Spring Boot (backend)**.  
This app allows users to submit basic financial information and receive a loan prequalification result.

---

## 🚀 Live Demo

Frontend (deployed):  
https://demo-applicant-precheck.vercel.app/

> ⚠️ Note: The backend is hosted on a free-tier service and may take 30–60 seconds to respond on the first request due to cold start behavior. Subsequent requests are fast.

---

## Tech Stack

### Frontend
- React
- JavaScript
- HTML/CSS

### Backend
- Java
- Spring Boot

---

## Project Structure

frontend/ → React client  
backend/ → Spring Boot REST API

---

## How It Works

- User enters **credit score** and **income**
- React frontend sends a **POST request** to the backend API
- Spring Boot processes the data and applies qualification logic
- A result is returned and displayed

### Possible Results
- Approved  
- Review  
- No Result  
---

## Deployment

- **Frontend:** Vercel  
- **Backend API:** Render  

The frontend communicates with the backend using an environment variable (`REACT_APP_API_URL`).

---

## Local Development Setup

### 1. Clone the repository

git clone https://github.com/sflugum/demo-applicant-precheck.git

cd demo-applicant-precheck

---

## Backend Setup (Spring Boot)

### Run the backend

From the `backend` folder:

./mvnw spring-boot:run

Or run directly from your IDE (Eclipse recommended for Java).

Backend runs on:

http://localhost:8080

---

## Frontend Setup (React)

### Install dependencies

From the `frontend` folder:

npm install

### Create environment file

Create a `.env` file in the `frontend` folder:

REACT_APP_API_URL=http://localhost:8080

### Start frontend

npm start

Frontend runs on:

http://localhost:3000

---

## How It Works

- React frontend sends requests to Spring Boot backend
- Backend processes input and returns loan precheck result
- Frontend displays the result to the user

---

## Environment Variables

### Frontend (`.env`)

**Local development:**

REACT_APP_API_URL=http://localhost:8080

---

**Production (Vercel):**
- Set `REACT_APP_API_URL` to your deployed backend URL (Render)

---

## Future Improvements

- Add database integration (persist applications)
- Enhance validation and user feedback
- Expand decision logic with more criteria
- Add authentication/user accounts

---

## Notes

- This project is a **portfolio demonstration of full-stack development**
- Includes:
  - API integration  
  - Environment configuration  
  - Deployment across separate services  
  - Basic error handling 

---

## Author

Silas Flugum
