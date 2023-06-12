package com.example.diploma_server.presentation;

import com.example.diploma_server.contact.ContactRequest;
import com.example.diploma_server.contact.ContactService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/contact")
@RequiredArgsConstructor
public class ContactController {

  private final ContactService contactService;

  @PostMapping
  public void submitContactForm(@RequestBody ContactRequest contactRequest) {
    log.info("Received contact request. {}", contactRequest);
    contactService.saveContact(contactRequest);
  }

}
