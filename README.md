🧠 Brain Tumor Detection & AI Medical Assistant

«🚀 An AI-powered full-stack web application that detects brain tumors from MRI scans and provides intelligent medical explanations via an integrated chatbot.»

---

🌟 Overview

This project combines AI + Full Stack Development to solve a real-world healthcare problem.

Users can upload MRI scans, get instant tumor predictions with confidence scores, and interact with an AI assistant to understand the results in simple medical terms.

---

✨ Key Highlights

- 🧠 Deep Learning-based tumor detection
- 🤖 AI chatbot for medical explanation
- 📊 Confidence-based prediction system
- 📂 Smart history tracking with delete functionality
- 📝 Custom test naming (like real medical reports)
- ⚡ Smooth and responsive UI with sidebar interactions
- 🔄 Real-time API communication

---

🧩 System Architecture

[Frontend (HTML/CSS/JS)]
↓
[Spring Boot Backend (REST API)]
↓
[AI Model (Python)]
↓
[Database (MySQL)]

---

🛠️ Tech Stack

🔹 Frontend

- HTML5
- CSS3 (Custom UI, Sidebar, Glassmorphism effects)
- JavaScript (Fetch API, Async/Await)

🔹 Backend

- Java (Spring Boot)
- REST API Architecture
- MVC Design Pattern

🔹 Database

- MySQL

🔹 AI Model

- Python (MRI classification model)

---

## 📸 Screenshots

### 🏠 Main UI
![Main UI](./screenshots/main.png)

### 🧠 Prediction Result
![Prediction](./screenshots/result.png)

### 🤖 AI Chatbot
![Chatbot](./screenshots/chat.png)

### 🕓 History Panel
![History](./screenshots/history.png)

---

⚙️ Features Breakdown

🧠 Tumor Detection

- Upload MRI scan
- Predict tumor presence
- Display confidence score

🤖 AI Assistant

- Explains results in simple language
- Provides severity insights
- Answers follow-up questions

📂 History System

- Stores all predictions
- Custom naming support
- Delete individual records
- Smooth scroll with isolation

🎨 UI/UX Enhancements

- Sidebar navigation
- Scroll isolation fix
- Interactive buttons & animations

---

🔌 API Endpoints

Method| Endpoint| Description
POST| "/api/predict"| Upload MRI and get prediction
GET| "/api/history"| Fetch all saved reports
DELETE| "/api/history/{id}"| Delete a specific record
POST| "/api/chat"| AI chatbot interaction

---

▶️ Getting Started

🔹 Clone Repository

git clone https://github.com/your-username/brain-tumor-detection.git
cd brain-tumor-detection

---

🔹 Run Backend (Spring Boot)

cd backend
mvn spring-boot:run

---

🔹 Run Frontend

Open:

frontend/index.html

---

🧠 AI Assistant Logic

The chatbot dynamically uses:

- Prediction result
- Confidence score
- User queries

To generate:

- Tumor explanation
- Severity level
- General insights

⚠️ Note: This system does NOT prescribe medicines.

---

📊 Real-World Impact

- 🏥 Helps in early-stage tumor awareness
- 🧑‍⚕️ Simplifies medical reports for non-experts
- 💡 Demonstrates AI in healthcare

---

🚧 Challenges Solved

- 🔄 Frontend ↔ Backend integration
- 📡 Async API handling
- 🧠 AI response structuring
- 🖱️ UI bugs (scroll conflict, event bubbling fix)
- 🗂️ Data persistence and management

---

🚀 Future Enhancements

- 📱 Mobile responsive UI
- ☁️ Cloud deployment (AWS / Render)
- 🔐 Authentication system
- 📈 Advanced ML model
- 🧾 Downloadable medical reports (PDF)

---

📁 Project Structure

brain-tumor-detection/
│
├── backend/
│   ├── controller/
│   ├── service/
│   ├── repository/
│   ├── model/
│   └── Application.java
│
├── frontend/
│   └── index.html
│
├── screenshots/
│
└── README.md

---

👨‍💻 Author

Mayank Kumar Singh

- 💻 Full Stack Developer (Java + AI)
- 🚀 Passionate about building real-world applications

---

⭐ Support

If you like this project:

👉 Give it a ⭐ on GitHub
👉 Share it with others

---

💡 Recruiter Note

This project demonstrates:

- Full Stack Development (Frontend + Backend)
- AI Integration in production-like system
- REST API design
- UI/UX problem solving
- Real-world application thinking

---