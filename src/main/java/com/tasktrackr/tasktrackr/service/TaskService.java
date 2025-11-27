package com.tasktrackr.tasktrackr.service;

import com.tasktrackr.tasktrackr.model.Status;
import com.tasktrackr.tasktrackr.model.Task;
import com.tasktrackr.tasktrackr.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository repo;

    public List<Task> getAll() {
        return repo.findAll();
    }

    public List<Task> getByStatus(Status status) {
        return repo.findByStatus(status);
    }

    public Task getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Task not found: " + id));
    }

    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    public void save(Task task) {
        repo.save(task);
    }
}
