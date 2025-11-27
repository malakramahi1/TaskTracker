package com.tasktrackr.tasktrackr.controller;

import com.tasktrackr.tasktrackr.model.Task;
import com.tasktrackr.tasktrackr.model.Status;
import com.tasktrackr.tasktrackr.service.TaskService;

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

    /**
     * List tasks, with optional filtering by status.
     * Matches: /tasks?status=TODO
     */
    @GetMapping
    public String list(
            @RequestParam(required = false) String status,
            Model model
    ) {
        List<Task> tasks;

        if (status != null && !status.isBlank()) {
            try {
                Status s = Status.valueOf(status);
                tasks = service.getByStatus(s);
            } catch (Exception e) {
                tasks = service.getAll(); // invalid status → return all
            }
        } else {
            tasks = service.getAll();
        }

        model.addAttribute("tasks", tasks);
        return "tasks_list"; // your new tasks.html
    }

    /**
     * Show empty form for creating a task
     */
    @GetMapping("/new")
    public String form(Model model) {
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
    public String createOrUpdate(Task task) {
        service.save(task);
        return "redirect:/tasks";
    }

    /**
     * Show form for editing an existing task
     * Matches /tasks/{id}/edit
     */
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        Task task = service.getById(id);
        model.addAttribute("task", task);
        return "task_form";
    }

    @PostMapping("/{id}/edit")
    public String update(@PathVariable Long id, Task task) {
        task.setId(id);
        service.save(task);
        return "redirect:/tasks";
    }


    /**
     * Delete a task
     * Matches POST /tasks/{id}/delete
     */
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        service.deleteById(id);
        return "redirect:/tasks";
    }


}
