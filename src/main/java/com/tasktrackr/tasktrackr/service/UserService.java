package com.tasktrackr.tasktrackr.service;

import com.tasktrackr.tasktrackr.model.User;
import com.tasktrackr.tasktrackr.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepo;

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public User register(String username, String email, String rawPassword, String name) {
        if (userRepo.findByUsername(username) != null) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepo.findByEmail(email) != null) {
            throw new RuntimeException("Email already registered");
        }

        User u = new User();
        u.setUsername(username);
        u.setEmail(email);
        u.setPassword(rawPassword);
        u.setName(name);
        //u.setCreatedAt();
        return userRepo.save(u);
    }

    public User login(String usernameOrEmail, String rawPassword) {
        User u = userRepo.findByUsername(usernameOrEmail);
        if (u == null) {
            u = userRepo.findByEmail(usernameOrEmail);
        }
        if (u == null) return null;
        if (!u.getPassword().equals(rawPassword)) return null;
        return u;
    }

    public User getById(Integer id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found: " + id));
    }
}