package com.mysite.samteam.controller;

import com.mysite.samteam.service.FirebaseService;
import com.mysite.samteam.service.TblWhgsUserInfoService;
import com.mysite.samteam.vo.VerifyRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/firebase")
@RequiredArgsConstructor
public class FirebaseController {

    private final FirebaseService firebaseService;
    private final TblWhgsUserInfoService userService;

    @PostMapping("/token")
    @Operation(summary = "Update Firebase Token", description = "Updates the Firebase token for a user.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Firebase token updated successfully"),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
            })
    public ResponseEntity<?> updateToken(@RequestBody VerifyRequest request) {
        System.out.println(request.getUserId() + " " + request.getToken());
        firebaseService.saveOrUpdateToken(request.getUserId(), request.getToken());
        // 토큰 저장 후 인증 요청 푸시 알림 전송
        firebaseService.sendAuthenticationRequest(request.getUserId());
        return ResponseEntity.ok().body(Map.of("message", "Firebase token updated successfully"));
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyUser(@RequestBody VerifyRequest request) {
        // 사용자 인증 처리 및 vrfyYn 상태 업데이트 로직
        boolean isUpdated = userService.updateUserVerification(request.getUserId(), "Y");
        if (isUpdated) {
            return ResponseEntity.ok().body(Map.of("message", "User verified successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "Failed to verify user"));
        }
    }

}
