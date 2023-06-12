package com.example.diploma_server.contact;

import com.example.diploma_server.contact.ContactData;
import com.example.diploma_server.contact.ContactRepository;
import com.example.diploma_server.contact.ContactRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ContactService {

  private final ContactRepository contactRepository;

  public void saveContact(ContactRequest contactRequest) {
    ContactData contactData = ContactData.builder()
        .name(contactRequest.getName())
        .email(contactRequest.getEmail())
        .message(contactRequest.getMessage())
        .createdAt(LocalDateTime.now()).build();
    contactRepository.save(contactData);
  }

}
