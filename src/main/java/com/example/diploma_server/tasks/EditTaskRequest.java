package com.example.diploma_server.tasks;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class EditTaskRequest {

  private String name;
  private LocalDate date;
  private int pomodoros;

}
