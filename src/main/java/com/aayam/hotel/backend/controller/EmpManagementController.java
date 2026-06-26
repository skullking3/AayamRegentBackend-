package com.aayam.hotel.backend.controller;

import com.aayam.hotel.backend.model.Emp;
import com.aayam.hotel.backend.repository.EmpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/emp")
// FIX: allowedOrigins mein specific configurations specify karein, "*" hatayein jab allowCredentials enabled ho
@CrossOrigin(
        originPatterns = "*",
        allowedHeaders = "*",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE},
        allowCredentials = "true"
)
public class EmpManagementController {

    @Autowired
    private EmpRepository empRepository;

    @GetMapping("/all")
    public List<Emp> getAllEmployees() {
        return empRepository.findAll();
    }

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Emp> addEmployee(@RequestBody Emp emp) {
        try {
            Emp savedEmp = empRepository.save(emp);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedEmp);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable String id) {
        empRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Emp> updateEmployee(@PathVariable String id, @RequestBody Emp empDetails) {
        return empRepository.findById(id).map(emp -> {
            emp.setName(empDetails.getName());
            emp.setUsername(empDetails.getUsername());
            emp.setRole(empDetails.getRole());
            emp.setStatus(empDetails.getStatus());
            emp.setEmpId(empDetails.getEmpId());
            emp.setIdentityNo(empDetails.getIdentityNo());
            return ResponseEntity.ok(empRepository.save(emp));
        }).orElse(ResponseEntity.notFound().build());
    }
}