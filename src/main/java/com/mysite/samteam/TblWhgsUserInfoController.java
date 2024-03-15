package com.mysite.samteam;

import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<String> registerUser(@RequestBody TblWhgsUserInfo userInfo) {
        userService.registerUser(userInfo);
        return ResponseEntity.ok("User registered successfully.");
    }

    @GetMapping("/{userId}")
    public ResponseEntity<TblWhgsUserInfo> getUserById(@PathVariable String userId) {
        Optional<TblWhgsUserInfo> userOptional = userService.getUserById(userId);
        return userOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{userId}")
    public ResponseEntity<String> updateUser(@PathVariable String userId, @RequestBody TblWhgsUserInfo userInfo) {
        userInfo.setUserId(userId); // Ensure userId is set
        userService.updateUser(userInfo);
        return ResponseEntity.ok("User updated successfully.");
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok("User deleted successfully.");
    }

    @GetMapping("/all")
    public ResponseEntity<List<TblWhgsUserInfo>> getAllUsers() {
        List<TblWhgsUserInfo> userList = userService.getAllUsers();
        return ResponseEntity.ok(userList);
    }
}

