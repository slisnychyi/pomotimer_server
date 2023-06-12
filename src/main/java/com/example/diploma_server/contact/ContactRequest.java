package com.example.diploma_server.contact;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ContactRequest {

  private String name;
  private String email;
  private String message;

}
