package com.mysite.samteam;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public ResponseEntity<String> loginUser(@RequestParam String userId, @RequestParam String password) {
        Optional<TblWhgsUserInfo> userOptional = userService.loginUser(userId, password);
        if (userOptional.isPresent()) {
            return ResponseEntity.ok("로그인 성공");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패");
        }
    }

    @PutMapping("/{userId}")
    @Operation(summary = "사용자 정보 수정", description = "특정 사용자의 정보를 수정합니다.")
    @ApiResponse(responseCode = "200", description = "사용자 정보 수정 성공")
    @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음")
    public ResponseEntity<String> updateUser(@PathVariable String userId, @RequestBody TblWhgsUserInfo userInfo) {
        userInfo.setUserId(userId); // Ensure userId is set correctly
        userService.updateUser(userInfo);
        return ResponseEntity.ok("User updated successfully.");
    }

    @DeleteMapping("/{userId}")
    @Operation(summary = "사용자 삭제", description = "특정 사용자를 삭제합니다.")
    @ApiResponse(responseCode = "200", description = "사용자 삭제 성공")
    @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음")
    public ResponseEntity<String> deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok("User deleted successfully.");
    }

    @GetMapping("/all")
    @Operation(summary = "전체 사용자 조회", description = "시스템에 등록된 모든 사용자의 정보를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    public ResponseEntity<List<TblWhgsUserInfo>> getAllUsers() {
        List<TblWhgsUserInfo> userList = userService.getAllUsers();
        return ResponseEntity.ok(userList);
    }
}
