package com.example.exam.controller;

import com.example.exam.model.Exam;
import com.example.exam.repository.ExamRepository;
import com.example.exam.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class ExamApiController {

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private UserRepository userRepository;

    // GET /api/v1/exams — saare exams
    @GetMapping("/exams")
    public ResponseEntity<?> getAllExams() {
        List<Exam> exams = examRepository.findAll();

        List<Map<String, Object>> result = exams.stream().map(e -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", e.getId());
            map.put("title", e.getTitle());
            map.put("duration", e.getDurationInMinutes());
            map.put("questionCount", e.getQuestions().size());
            return map;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }

    // GET /api/v1/exams/{id} — ek exam ki details
    @GetMapping("/exams/{id}")
    public ResponseEntity<?> getExam(@PathVariable Long id) {
        return examRepository.findById(id)
                .map(e -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", e.getId());
                    map.put("title", e.getTitle());
                    map.put("duration", e.getDurationInMinutes());
                    map.put("questions", e.getQuestions().size());
                    return ResponseEntity.ok((Object) map);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/v1/me — logged-in user ki info (JWT se)
    @GetMapping("/me")
    public ResponseEntity<?> getMyInfo(Authentication authentication) {
        String username = authentication.getName();
        return userRepository.findByUsername(username)
                .map(u -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", u.getId());
                    map.put("name", u.getFullName() != null ? u.getFullName() : "");
                    map.put("username", u.getUsername());
                    map.put("role", u.getRole());
                    return ResponseEntity.ok((Object) map);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}