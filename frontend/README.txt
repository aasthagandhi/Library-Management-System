LIBRARY MANAGEMENT FRONTEND

Files (place these directly inside your existing frontend folder):
- index.html
- style.css
- script.js

Backend URL configured in script.js:
http://localhost:8081

Run:
1. Start your Spring Boot backend on port 8081.
2. Open the frontend folder in VS Code.
3. Open index.html with Live Server.
4. If browser requests are blocked by CORS, configure CORS in the Spring Boot backend to allow requests from the frontend's Live Server URL.
Notes:
- Uses the endpoints supplied from BookController, StudentController and IssueController.
- Issue request body: { "student": { "id": 1 }, "book": { "id": 1, "available": true } }.
- Return uses PUT /issues/{id}/return with no request body.
