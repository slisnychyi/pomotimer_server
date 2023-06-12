package com.example.diploma_server.user;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class LoginRequest {

  private String email;
  private String password;

}
