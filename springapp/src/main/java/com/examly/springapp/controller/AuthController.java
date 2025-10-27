package com.examly.springapp.controller;

import com.examly.springapp.model.User;
import com.examly.springapp.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");
        
        System.out.println("Login attempt: " + username + "/" + password);
        
        var userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()) {
            System.out.println("User not found: " + username);
            return ResponseEntity.ok(Map.of("success", false, "message", "User not found"));
        }
        
        User user = userOpt.get();
        System.out.println("Found user: " + user.getUsername() + ", stored password: " + user.getPassword());
        
        if (!user.getPassword().equals(password)) {
            System.out.println("Password mismatch");
            return ResponseEntity.ok(Map.of("success", false, "message", "Invalid password"));
        }
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("role", user.getRole());
        response.put("username", user.getUsername());
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/users")
    public ResponseEntity<Object> getUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }
    
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Backend is working");
    }
    
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody Map<String, String> userData) {
        String username = userData.get("username");
        String password = userData.get("password");
        
        // Check if user already exists
        if (userRepository.findByUsername(username).isPresent()) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Username already exists");
            return ResponseEntity.status(400).body(response);
        }
        
        // Create new customer account
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.setRole("CUSTOMER");
        userRepository.save(newUser);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("role", newUser.getRole());
        response.put("username", newUser.getUsername());
        response.put("message", "Account created successfully");
        return ResponseEntity.ok(response);
    }
}