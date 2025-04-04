Authentication Controller

**Base URL:** `/auth`

### POST `/register/trainer`

Registers a new trainer.

**Request Body:** `RegisterTrainerRequestDTO`  
**Response:** `RegisterTrainerResponseDTO`

---

### POST `/register/trainee`

Registers a new trainee.

**Request Body:** `RegisterTraineeRequestDTO`  
**Response:** `RegisterTraineeResponseDTO`

---

### POST `/authenticate`

Authenticates a trainee.

**Request Body:** `AuthenticateRequestDTO`  
**Response:** `200 OK` (with username/password cookie)

---

### PUT `/changePassword`

Changes a trainee's password.

**Request Body:** `ChangePasswordRequestDTO`  
**Response:** `200 OK`

---

## 👤 Trainee Controller

**Base URL:** `/trainees`

All routes require authentication.

### GET `/{username}`

Returns trainee details by username.

**Path Param:** `username`  
**Response:** `TraineeResponseDTO`

---

### GET `/{username}/trainings`

Returns training sessions of a trainee.

**Query Params (optional):**  
- `periodFrom`  
- `periodTo`  
- `trainerName`  
- `trainingType`

**Response:** `List<TrainingResponseDTO>`

---

### PUT `/update/{username}`

Updates trainee profile information.

**Query Params (required):**  
- `firstname`  
- `lastname`  

**Query Params (optional):**  
- `dateOfBirth`  
- `address`  
- `isActive`

**Response:** `TraineeResponseDTO`

---

### DELETE `/delete/{username}`

Deletes a trainee.

**Response:** `200 OK`

---

### PATCH `/status/{username}`

Updates the active status of a trainee.

**Query Param:** `isActive` (boolean)  
**Response:** `200 OK`

---

## 🧑‍🏫 Trainer Controller

**Base URL:** `/trainers`

All routes require authentication.

### GET `/{username}`

Returns trainer profile.

**Response:** `TrainerResponseDTO`

---

### PUT `/update/{username}`

Updates trainer details.

**Request Body:** `TrainerRequestDTO`  
**Response:** `TrainerResponseDTO`

---

### GET `/{username}/trainings`

Returns trainer's training sessions.

**Query Params (optional):**  
- `periodFrom`  
- `periodTo`  
- `traineeName`

**Response:** `List<TrainingResponseDTO>`

---

### PATCH `/status/{username}`

Update trainer's active status.

**Query Param:** `isActive` (boolean)  
**Response:** `200 OK`

---

## 🔁 Trainee-Trainer Controller

**Base URL:** `/trainee-trainer`

All routes require authentication.

### GET `/{username}/not-assigned-trainers`

Returns a list of trainers not assigned to the given trainee.

**Response:** `List<TrainerResponseDTO>`

---

### PUT `/update/{username}`

Assigns or updates trainer list for a trainee.

**Query Param:** `trainers` (List of trainer usernames)  
**Response:** `List<TrainerResponseDTO>`

---

## 🏋️ Training Controller

**Base URL:** `/trainings`

All routes require authentication.

### POST `/`

Creates a new training session.

**Request Body:** `TrainingRequestDTO`  
**Response:** `TrainingResponseDTO`

---

## 🧾 Training Types Controller

**Base URL:** `/training-types`

All routes require authentication.

### GET `/`

Returns list of available training types.

**Response:** `List<TrainingTypeResponseDTO>`

---

## ✅ Security

All endpoints (except authentication and registration) are protected via a custom `@Authenticated` annotation.

---

## 📦 Tech Stack

- Java 17+
- Spring 6.x
- Lombok
- Jakarta Validation
- Maven

---

## 🧪 Testing

Use tools like Postman or Swagger UI to test the endpoints. Make sure to handle authentication cookies for secure endpoints.

---

## 🚀 Getting Started

1. Clone the repo
2. Run the Spring core application
3. Access endpoints at `http://localhost:8080/api/v1/`

---


