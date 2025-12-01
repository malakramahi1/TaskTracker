# TaskTracker — Final Project Deliverable (Dec 1)

This document describes the final state of our TaskTracker project for the CS 480 course. It includes a link to our demo recording, the final ER diagram, and the relational schema used by our implementation. The earlier requirement analysis for the Nov 18 deliverable is included at the end as an appendix.

---

## 1. Demo Video Link


> **Video link: https://youtu.be/hf3Zq6UORI0** 

The demo shows how to:
- View the main task list page
- Create a new task (title, description, due date, status)
- Filter tasks by status (Any / To Do / In Progress / Done)
- Update or delete an existing task

---

## 2. Final ER Diagram

Our final ER diagram has three entities: **User**, **Task**, and **Status**.

![alt text](er_diagram.png)

- Each **User** can be assigned many **Tasks**.
- Each **Status** value (To Do, In Progress, Done) can be used by many **Tasks**.
- Each **Task** references exactly one **User** (its assignee) and one **Status**.



**Key relationships:**

- `Task.Assignee_ID` → `User.User_ID` (who the task is assigned to)  
- `Task.Status_ID` → `Status.Status_ID` (current status of the task)

This matches the way TaskTracker behaves: every task has an owner and a status, and the UI allows filtering by status.

---

## 3. Relational Schema

The relational schema corresponding to the ER diagram is:

```text
User(
    User_ID       INT PRIMARY KEY,
    username      VARCHAR(20) NOT NULL,
    password_hash VARCHAR(20) NOT NULL,
    email         VARCHAR(50) NOT NULL UNIQUE,
    Name          VARCHAR(20) NOT NULL,
    created_at    DATE NOT NULL
)

Status(
    Status_ID INT PRIMARY KEY,
    Name      VARCHAR(20) NOT NULL
)

Task(
    Task_ID     INT PRIMARY KEY,
    Assignee_ID INT NOT NULL,
    Title       VARCHAR(20) NOT NULL,
    Description VARCHAR(100),
    Due_date    DATE,
    Status_ID   INT NOT NULL,
    Created_at  DATE NOT NULL,
    FOREIGN KEY (Assignee_ID) REFERENCES User(User_ID),
    FOREIGN KEY (Status_ID)   REFERENCES Status(Status_ID)
)
