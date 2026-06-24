    package com.aayam.hotel.backend.controller;

    import com.aayam.hotel.backend.dto.StaffLoginRequest;
    import com.aayam.hotel.backend.dto.StaffLoginResponse;
    import com.aayam.hotel.backend.model.Staff;
    import com.aayam.hotel.backend.repository.StaffRepository;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;
    import java.util.Optional;

    @RestController
    @RequestMapping("/api/staff")
    public class StaffAuthController {

        @Autowired
        private StaffRepository staffRepository;

        @PostMapping("/login")
        public ResponseEntity<?> loginStaff(@RequestBody StaffLoginRequest loginRequest) {
            // 👈 Database ke 'username' field mein vishal@aayamregent.com check karega
            Optional<Staff> staffOpt = staffRepository.findByUsername(loginRequest.getUsername());

            if (staffOpt.isPresent()) {
                Staff staff = staffOpt.get();
                if (staff.getPassword().equals(loginRequest.getPassword())) {
                    return ResponseEntity.ok(new StaffLoginResponse(
                            true,
                            "Login Successful!",
                            staff.getUsername(),
                            staff.getRole()
                    ));
                }
            }
            return ResponseEntity.status(401).body(new StaffLoginResponse(
                    false,
                    "Invalid username or password!",
                    null,
                    null
            ));
        }
    }