# 🧠 Brain Tumor Detection System (AI + Spring Boot)

An AI-powered web backend that detects brain tumors from MRI images using a deep learning model.
This project integrates **Spring Boot (Java)** with a **Python-based AI model (TensorFlow/Keras)** for real-time predictions.

---

## 🚀 Features

* 📤 Upload MRI image via API
* 🤖 AI model predicts tumor presence
* ⚡ Fast integration between Java & Python
* 🔄 Real-time prediction response
* 🧩 Clean modular architecture (Controller, Service, etc.)

---

## 🛠️ Tech Stack

### Backend

* Java (Spring Boot)
* REST API
* Multipart File Upload

### AI / ML

* Python
* TensorFlow / Keras
* OpenCV
* NumPy

### Tools

* Postman (API Testing)
* IntelliJ IDEA
* Git & GitHub

---

## 📁 Project Structure

```
brain-tumor-backend/
│
├── src/main/java/com/brain/tumor/
│   ├── controller/
│   ├── service/
│   ├── model/
│   ├── repository/
│   └── Application.java
│
├── ai-model/
│   ├── model.h5
│   ├── test_model.py
│   └── test.jpg
│
└── README.md
```

---

## ⚙️ How It Works

1. User uploads an MRI image via API
2. Spring Boot saves the file
3. Java executes Python script using `ProcessBuilder`
4. Python loads trained model (`model.h5`)
5. Model predicts:

    * `TUMOR`
    * `NO_TUMOR`
6. Result is returned to API response

---

## 📡 API Endpoint

### 🔹 Predict Tumor

```
POST /api/predict
```

### 📥 Request (form-data)

| Key  | Type | Value     |
| ---- | ---- | --------- |
| file | File | image.jpg |

---

### 📤 Response

```
TUMOR
```

or

```
NO_TUMOR
```

---

## ▶️ How to Run

### 1. Clone Repository

```
git clone https://github.com/Mayank-wind/brain-tumor-detection-system.git
cd brain-tumor-detection-system
```

---

### 2. Run Spring Boot

Run `Application.java`

---

### 3. Run API (Postman)

* URL: `http://localhost:8080/api/predict`
* Method: `POST`
* Body: `form-data`
* Key: `file` → upload image

---

## 🧪 Example

Upload MRI image → API returns:

```
TUMOR
```

---

## API Testing

This project provides a REST API to detect brain tumors from MRI images.

### Endpoint
POST /api/predict

### Request
- Method: POST
- Content-Type: multipart/form-data
- Body: Image file

### Example (Postman)

![API Result](screenshots/api-prediction-result.png)

### Response
- TUMOR → Tumor detected
- NO_TUMOR → No tumor detected

## ⚠️ Notes

* Ensure Python is installed
* Update Python path in `ProcessBuilder`
* Model file (`model.h5`) must exist in `ai-model/`

---

## 🔥 Future Improvements

* Add frontend (React / Angular)
* Return confidence score
* Deploy on cloud (AWS / Render)
* Convert to microservices
* Add authentication

---

## 👨‍💻 Author

**Mayank Kumar Singh**

---

## ⭐ If you like this project

Give it a ⭐ on GitHub!
