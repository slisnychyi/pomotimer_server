package com.example.diploma_server.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  public Optional<User> loginUser(String email, String password) {
    return userRepository.findByEmail(email)
        .filter(e -> e.getPassword().equals(password));
  }

  public User signin(String email, String password) {
    return userRepository.save(User.builder().email(email).password(password).build());
  }

}
