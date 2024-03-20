package com.mysite.samteam.controller;

import com.mysite.samteam.service.FirebaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/firebase")
@RequiredArgsConstructor
public class FirebaseController {

    private final FirebaseService firebaseService;

    @PostMapping("/token")
    @Operation(summary = "Update Firebase Token", description = "Updates the Firebase token for a user.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Firebase token updated successfully"),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
            })
    public ResponseEntity<?> updateToken(@RequestParam String userId, @RequestParam String token, HttpSession session) {
        firebaseService.saveOrUpdateToken(userId, token);
        return ResponseEntity.ok().body("Firebase token updated successfully");
    }
}
