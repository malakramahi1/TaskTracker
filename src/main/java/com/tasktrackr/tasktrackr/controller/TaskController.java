package com.tasktrackr.tasktrackr.controller;

import com.tasktrackr.tasktrackr.model.*;
import com.tasktrackr.tasktrackr.model.*;
import com.tasktrackr.tasktrackr.service.*;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService service;

    @Autowired
    private UserService userService;

    private Integer requireLogin(HttpSession session) {
        return (Integer) session.getAttribute("userId");
    }

    /**
     * List tasks, with optional filtering by status.
     * Matches: /tasks?status=TODO
     */
    @GetMapping
    public String list(
            @RequestParam(required = false) String status,
            Model model,
            HttpSession session
    ) {
        Integer userId = requireLogin(session);
        if (userId == null) {
            return "redirect:/login";
        }

        List<Task> tasks;

        if (status != null && !status.isBlank()) {
            try {
                Status s = Status.valueOf(status);
                tasks = service.getByUserAndStatus(userId, s);
            } catch (Exception e) {
                tasks = service.getByUser(userId); // invalid status → return all
            }
        } else {
            tasks = service.getByUser(userId);
        }

        model.addAttribute("tasks", tasks);
        model.addAttribute("username",session.getAttribute("username") );
        return "tasks_list"; // your new tasks.html
    }

    /**
     * Show empty form for creating a task
     */
    @GetMapping("/new")
    public String form(Model model, HttpSession session) {
        Integer userId = requireLogin(session);
        if (userId == null) {
            return "redirect:/login";
        }

        Task task = new Task();
        task.setStatus(Status.TODO);  // default
        model.addAttribute("task", task);
        return "task_form";
    }

    /**
     * Save (create or update)
     * Matches POST /tasks
     */
    @PostMapping
    public String createOrUpdate(Task task, HttpSession session) {
        Integer userId = requireLogin(session);
        if (userId == null) {
            return "redirect:/login";
        }
        User currentUser = userService.getById(userId);

        task.setAssignee(currentUser);
        service.save(task);
        return "redirect:/tasks";
    }

    /**
     * Show form for editing an existing task
     * Matches /tasks/{id}/edit
     */
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model model, HttpSession session) {
        Integer userId = requireLogin(session);
        if (userId == null) {
            return "redirect:/login";
        }

        Task task = service.getById(id);
        if (task.getAssignee() == null ||
                !task.getAssignee().getUserId().equals(userId)) {
            return "redirect:/tasks";
        }
        model.addAttribute("task", task);
        return "task_form";
    }

    @PostMapping("/{id}/edit")
    public String update(@PathVariable Long id, Task task, HttpSession session) {

        Integer userId = requireLogin(session);
        if (userId == null) {
            return "redirect:/login";
        }
        User currentUser = userService.getById(userId);
        task.setId(id);
        task.setAssignee(currentUser);
        service.save(task);
        return "redirect:/tasks";
    }

    /**
     * Delete a task
     * Matches POST /tasks/{id}/delete
     */
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, HttpSession session) {
        Integer userId = requireLogin(session);
        if (userId == null) {
            return "redirect:/login";
        }
        Task task = service.getById(id);
        if (task.getAssignee() != null &&
                task.getAssignee().getUserId().equals(userId)) {
            service.deleteById(id);
        }
        return "redirect:/tasks";
    }


}
