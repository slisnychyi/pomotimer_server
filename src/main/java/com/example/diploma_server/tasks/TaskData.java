package com.example.diploma_server.tasks;

import com.example.diploma_server.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "tasks")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TaskData {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String taskId;
  private String name;
  private LocalDate date;
  private boolean completed;
  private int pomodoros;
  private int completedPomodoros;
  private String taskNotes;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  public TaskData fromTask(Task task, User user){
    return TaskData.builder()
        .taskId(task.getId())
        .name(task.getName())
        .date(task.getDate())
        .completed(task.isCompleted())
        .pomodoros(task.getPomodoros())
        .completedPomodoros(task.getCompletedPomodoros())
        .taskNotes(task.getTaskNotes())
        .user(user)
        .build();
  }

  public Task toTask(){
    return Task.builder()
        .id(taskId)
        .name(name)
        .date(date)
        .completed(completed)
        .pomodoros(pomodoros)
        .completedPomodoros(completedPomodoros)
        .taskNotes(taskNotes)
        .build();
  }

}
