# TaskTracker — Requirement Analysis (Due Nov 18)

This document outlines the requirement analysis, application needs, data structure, and technology stack for our TaskTracker project. TaskTracker is a lightweight system designed to help teams create, assign, and track tasks with clear status indicators and due dates. The ER diagram is included at the end.

---

## 1. Overview

TaskTracker is a simple web application that allows users to manage personal or group tasks in an organized and efficient way. Users can create tasks, assign them to team members, define due dates, and update the task’s status as work progresses. The purpose of the system is to provide a clear overview of what needs to be done, who is responsible for each task, and which tasks are overdue. The application follows a standard client–server design: a Flask backend handles requests and database operations, while a clean HTML/CSS/JavaScript frontend displays all information to the user.

---

## 2. Data Requirements

TaskTracker requires storing information about users, tasks, and task statuses. The following data entities and attributes are needed:

### **Entities & Attributes**
- **User**
  - `user_id` (Primary Key)
  - `name`
  - `email` (unique)
  - `password_hash`
  - `created_at`

- **Status**
  - `status_id` (Primary Key)
  - `status_name` (e.g., “To-Do”, “In Progress”, “Done”)

- **Task**
  - `task_id` (Primary Key)
  - `title`
  - `description`
  - `due_date`
  - `status_id` (Foreign Key → Status)
  - `assignee_id` (Foreign Key → User)
  - `created_at`

### **Relationships**
- A **User** can be assigned multiple **Tasks** (one-to-many)
- A **Status** type can apply to multiple **Tasks** (one-to-many)

These relationships ensure that every task has exactly one assigned user and one status category.

---

## 3. Application Requirements

### **Functional Requirements**
- Users can create a new task by entering a title, description, assignee, due date, and status.
- Users can edit existing tasks or delete them.
- Users can update a task’s completion status (e.g., move from To-Do → In Progress → Done).
- The system displays all tasks in a list or table view.
- Users can filter tasks by assignee or status.
- The system highlights or marks overdue tasks based on `due_date`.

### **Non-Functional Requirements**
- Pages should load within approximately 2 seconds under normal usage.
- The interface should be easy to navigate and intuitive for first-time users.
- Should work in all modern desktop browsers (Chrome, Safari, Firefox, Edge).
- All data must be stored persistently in a MySQL database.
- The backend should follow clean MVC-style organization for clarity and maintainability.

---

## 4. Web Technologies

TaskTracker will be built using the following tools and technologies:

- **Backend:** Python Flask  
- **Frontend:** HTML, CSS, JavaScript  
- **Database:** MySQL  
- **Version Control:** Git + GitHub  
- **Development Tools:** VS Code or PyCharm, diagrams.net for ER diagram  
- **Optional Deployment:** Render, Fly.io, or a local Flask server  

These technologies ensure the project remains lightweight, easy to maintain, and simple for multiple teammates to work on simultaneously.

---

## 5. ER Diagram

*In Respitroy 
