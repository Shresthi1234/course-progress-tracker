# Course Progress Tracker

A full-stack course and remaining-work tracker using:
- Frontend: React + Vite (HTML/CSS/JavaScript)
- Backend: Java 21 + Spring Boot
- Database: MySQL (Aiven-compatible)
- Version control: Git + GitHub
- Backend deployment: Render Web Service with a multistage Dockerfile
- Frontend deployment: Render Static Site

## Features in this starter
- View courses and their completion progress
- Add courses
- Track work items/tasks for each course
- Mark tasks complete or incomplete
- Automatically calculate progress from completed tasks
- MySQL persistence in production; H2 in-memory database for local development when no MySQL environment variables are supplied

## Project structure
```text
course-progress-tracker/
├── frontend/
│   ├── public/
│   ├── src/
│   │   ├── components/
│   │   ├── services/
│   │   ├── App.jsx
│   │   ├── main.jsx
│   │   └── styles.css
│   ├── .env.example
│   ├── index.html
│   ├── package.json
│   └── vite.config.js
├── backend/
│   ├── src/main/java/com/example/tracker/
│   │   ├── config/
│   │   ├── controller/
│   │   ├── dto/
│   │   ├── model/
│   │   ├── repository/
│   │   ├── service/
│   │   └── CourseProgressTrackerApplication.java
│   ├── src/main/resources/application.properties
│   ├── Dockerfile
│   └── pom.xml
├── render.yaml
├── .gitignore
└── README.md
```

## Requirements
- Java 21
- Maven 3.9+ (or use the included Dockerfile)
- Node.js 20+
- MySQL, optional for local development

## Run locally

### 1. Start the backend
```bash
cd backend
mvn spring-boot:run
```
Backend: `http://localhost:8080`

If `DB_URL`, `DB_USERNAME`, and `DB_PASSWORD` are not set, the app uses a temporary H2 database locally.

### 2. Start the frontend
In a second terminal:
```bash
cd frontend
cp .env.example .env
npm install
npm run dev
```
Frontend: `http://localhost:5173`

The Vite dev server proxies `/api` requests to the backend.

## API endpoints
- `GET /api/courses` — list courses with progress and work items
- `POST /api/courses` — create a course
- `POST /api/courses/{courseId}/tasks` — add a work item
- `PATCH /api/courses/{courseId}/tasks/{taskId}` — update task title/completion
- `DELETE /api/courses/{courseId}/tasks/{taskId}` — delete a work item
- `GET /api/health` — health check

Example create course:
```json
{
  "name": "Java Fundamentals",
  "description": "Learn core Java",
  "targetDate": "2026-12-01"
}
```

## Aiven MySQL setup
Create a MySQL service in Aiven and copy its connection details. Configure the backend environment variables:
- `DB_URL` — JDBC URL, e.g. `jdbc:mysql://YOUR-HOST:YOUR-PORT/defaultdb?sslMode=REQUIRED`
- `DB_USERNAME`
- `DB_PASSWORD`

Do not commit real credentials. Use Render environment variables instead of putting secrets in source code.

## Deploy backend to Render
1. Push this project to GitHub.
2. Create a Render **Web Service** from the repository.
3. Set the root directory to `backend`.
4. Choose Docker as the runtime (Render detects `backend/Dockerfile`).
5. Add `DB_URL`, `DB_USERNAME`, and `DB_PASSWORD` in Render's environment settings.
6. Render provides `PORT`; the app reads it automatically.

## Deploy frontend to Render Static Site
1. Create a Render **Static Site** from the same repository.
2. Set root directory to `frontend`.
3. Build command: `npm install && npm run build`
4. Publish directory: `dist`
5. Set `VITE_API_BASE_URL` to your backend URL, for example `https://your-backend.onrender.com`.
6. Add a rewrite rule from `/*` to `/index.html` with status `200` if needed.

## GitHub
```bash
git init
git add .
git commit -m "Initial course progress tracker"
git branch -M main
git remote add origin https://github.com/YOUR-USERNAME/course-progress-tracker.git
git push -u origin main
```

## Notes
- This is a starter project, not a production-hardened application. Add authentication, validation, migrations, and backups before using it with private or important data.
