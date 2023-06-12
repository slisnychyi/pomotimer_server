package com.example.diploma_server.presentation;

import com.example.diploma_server.user.LoginRequest;
import com.example.diploma_server.user.User;
import com.example.diploma_server.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @PostMapping("/login")
  public ResponseEntity<String> loginUser(@RequestBody LoginRequest loginRequest) {
    return userService.loginUser(loginRequest.getEmail(), loginRequest.getPassword())
        .map(e -> ResponseEntity.ok(e.getEmail()))
        .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password"));
  }

  @PostMapping("/signin")
  public ResponseEntity<User> signin(@RequestBody LoginRequest signinRequest) {
    User user = userService.signin(signinRequest.getEmail(), signinRequest.getPassword());
    return ResponseEntity.ok(user);
  }

}
