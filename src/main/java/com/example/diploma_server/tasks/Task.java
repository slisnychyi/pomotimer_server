package com.example.diploma_server.tasks;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
public class Task {

  private String id;
  private String name;
  private LocalDate date;
  private boolean completed;
  private int pomodoros;
  private int completedPomodoros;
  private String taskNotes;

}
