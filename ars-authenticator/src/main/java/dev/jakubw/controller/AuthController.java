package dev.jakubw.controller;

import dev.jakubw.dto.SignInRequest;
import dev.jakubw.dto.SignUpRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<String> login(
            @RequestBody SignInRequest request,
            HttpServletResponse response
    ){
        String token = authService.login(request);
        response.addCookie(createCookie(token));
        return ResponseEntity.ok("Login successful");
    }
    @PostMapping("/register")
    public ResponseEntity<String> register(
            @RequestBody SignUpRequest request,
            HttpServletResponse response
    ){
        String token = authService.register(request);
        response.addCookie(createCookie(token));
        return ResponseEntity.ok("Registration successful");
    }

    private Cookie createCookie(String token){
        Cookie cookie = new Cookie("SHToken", token);
        cookie.setMaxAge(3600 * 24);
        cookie.setSecure(false);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        return cookie;
    }

}