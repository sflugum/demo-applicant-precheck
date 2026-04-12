# Loan Precheck App

Full-stack web application built with **React (frontend)** and **Spring Boot (backend)**.  
This app allows users to submit basic information and receive a loan prequalification check.

---

## Tech Stack

**Frontend**
- React
- JavaScript
- HTML/CSS

**Backend**
- Java
- Spring Boot

---

## Project Structure

root/
frontend/ # React app
backend/ # Spring Boot app

---

## Getting Started

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

REACT_APP_API_URL=http://localhost:8080

---

## Future Improvements

- Add database integration
- Improve validation and error handling
- Add authentication

---

## Notes

- This project is intended as a portfolio piece demonstrating full-stack development
- Backend must be running before frontend for API calls to work

---

## Author

Silas Flugum
