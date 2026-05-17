# Planner Pal
A browser based to-do list application with support for multiple lists, subtasks and task completion tracking.

## About the Project
The project was built as part of our last programming course in the first year of a computer science program. Our focus was to consolidate our knowledge in Java and get an introduction to web development, with a focus on understanding the frontend-backend communication.

Given the course's AI policy, it was also a way to learn how to freely use generative AI as a partner, and reflect on the pros and cons of that process.


## Features
- Create one or multiple lists shown simultaneously
- Manage lists from settings menu
- Add, rename and delete lists and tasks
- Add infinite subtasks
- Mark tasks as completed
- Settings menu for task management
- Toggle completed task visibility

## Screenshots
![alt text](<Demo.png>)

## Technologies Used
### Backend
- Java
- Maven for dependency management and build configuration
- JSON for HTTP request/response formatting

### Frontend
- HTML
- CSS
- JavaScript

## Requirements
- Java installed
- Maven (usually built into IDE)
- A modern web browser

## Installation / Setup

Clone the repository:

```bash
git clone <repo-url>
cd <project-name>
```

Open the project root folder in your preferred IDE.

Examples:
- VS Code: `code .`
- IntelliJ IDEA: `Open Folder`

### Running the application

#### Run through IDE
1. Run `Server.java` through your IDE (not through the terminal). Note: Maven dependencies should be automatically detected and loaded by your IDE.
2. Open a web browser and paste the following link: `http://localhost:8000`


## How to use
1. Create a new list at the top of the screen
2. Add one or more tasks to the list you just created
3. Add subtasks to each task if desired
3. Once finished, mark tasks or subtasks as complete by clicking the task name or checkbox
4. Open settings menu for list/tasks to rename or delete outdated ones
5. Hide completed tasks if you wish

## Project Structure

```text
DD1340-ProjINDA/
├── src/
│   └── main/
│        ├── java/
│        │   └── backend/
│        │
│        └── resources/
│            └── frontend/
└── pom.xml
```

**Backend:** handles HTTP requests, data management, and server-side application logic

**Frontend:** contains the user interface and client-side interaction logic

## Future Improvements
- Move tasks between lists
- Add deadline to tasks
- Continue improving UI

## Contributors
- Simon Aksberg
- Hanna Zhang

## License
This project was created for educational purposes.