package com.mysite.samteam.controller;

import com.mysite.samteam.service.TblWhgsUserInfoService;
import com.mysite.samteam.vo.TblWhgsUserInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class WebController {
    private final TblWhgsUserInfoService userService;

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("/register") // 테스트를 위해 사용 안함
    public String showRegistrationPage() {
        return "register";
    }

    @PostMapping("/users/login")
    @Operation(summary = "로그인", description = "사용자 로그인을 진행")
    @ApiResponse(responseCode = "200", description = "로그인 성공")
    @ApiResponse(responseCode = "401", description = "로그인 실패")
    public String loginUser(@RequestParam String userId, @RequestParam String password, HttpSession session) {
        Optional<TblWhgsUserInfo> userOptional = userService.loginUser(userId, password);
        if (userOptional.isPresent()) {
            TblWhgsUserInfo user = userOptional.get();
            if ("N".equals(user.getVrfyYn())) {
                return "redirect:/auth.html?userId=" + userId;
                // 클라이언트 사이드에 웹 푸시 알림 구독 요청을 알림
                //return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Please complete the verification process.");
            }
            session.setAttribute("user", user); // 로그인 성공한 사용자 정보를 세션에 저장
            return "home";
            //return ResponseEntity.ok("Login successful.");
        } else {
//            redirectAttributes.addFlashAttribute("loginError", "Invalid userId or password.");
            System.out.println("실패");
            return "login"; // login.html로 다시 리다이렉션
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed.");
        }
    }
}

