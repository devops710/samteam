package com.mysite.samteam;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class TblWhgsUserInfoController {

    private final TblWhgsUserInfoService userService;

    @PostMapping("/register")
    @Operation(summary = "사용자 등록", description = "새로운 사용자를 등록합니다.")
    @ApiResponse(responseCode = "200", description = "등록 성공")
    public ResponseEntity<String> registerUser(@RequestBody TblWhgsUserInfo userInfo) {
        userService.registerUser(userInfo);
        return ResponseEntity.ok("User registered successfully.");
    }

    @PostMapping("/login")
    @Operation(summary = "로그인", description = "사용자 로그인을 진행합니다.")
    @ApiResponse(responseCode = "200", description = "로그인 성공")
    @ApiResponse(responseCode = "401", description = "로그인 실패")
    public ResponseEntity<String> loginUser(@RequestParam String userId, @RequestParam String password, HttpSession session) {
        Optional<TblWhgsUserInfo> userOptional = userService.loginUser(userId, password);
        if (userOptional.isPresent()) {
            session.setAttribute("user", userOptional.get()); // 로그인 성공한 사용자 정보를 세션에 저장
            return ResponseEntity.ok("Login successful.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed.");
        }
    }

    @PutMapping("/{userId}")
    @Operation(summary = "사용자 정보 수정", description = "로그인 상태에서 사용자의 정보(비밀번호, 전화번호, 회사 등)를 수정합니다.")
    @ApiResponse(responseCode = "200", description = "사용자 정보 수정 성공")
    @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음")
    @ApiResponse(responseCode = "401", description = "수정 권한 없음")
    public ResponseEntity<String> updateUser(@PathVariable String userId, @RequestBody TblWhgsUserInfo userInfo, HttpSession session) {
        if (!userId.equals(session.getAttribute("user"))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }
        boolean isUpdated = userService.updateUser(userId, userInfo);
        if (isUpdated) {
            return ResponseEntity.ok("User updated successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{userId}")
    @Operation(summary = "사용자 삭제", description = "로그인 상태에서 특정 사용자를 삭제합니다.")
    @ApiResponse(responseCode = "200", description = "사용자 삭제 성공")
    @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음")
    @ApiResponse(responseCode = "401", description = "삭제 권한 없음")
    public ResponseEntity<String> deleteUser(@PathVariable String userId, HttpSession session) {
        if (!userId.equals(session.getAttribute("user"))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }
        userService.deleteUser(userId);
        return ResponseEntity.ok("User deleted successfully.");
    }

    @PostMapping("/logout")
    @Operation(summary = "로그아웃", description = "사용자를 로그아웃 상태로 만듭니다.")
    @ApiResponse(responseCode = "200", description = "로그아웃 성공")
    public ResponseEntity<String> logoutUser(HttpSession session) {
        session.removeAttribute("user"); // 세션에서 사용자 정보 제거
        session.invalidate(); // 세션 무효화
        return ResponseEntity.ok("Logout successful.");
    }
}
