package com.example.diploma_server.presentation;

import com.example.diploma_server.tasks.Task;
import com.example.diploma_server.tasks.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {

  private final TaskService taskService;

  @GetMapping
  public List<Task> getTasks() {
    return taskService.getAllTasks();
  }

  @GetMapping("/date")
  public List<Task> getTaskForDate(
      @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
      @RequestParam("userId") String userId
  ) {
    return taskService.getTasks(date, userId);
  }

  @GetMapping("/nextdays")
  public List<Task> getTaskForNextDays(
      @RequestParam("userId") String userId
  ) {
    return taskService.getTasksForNextDays(userId);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Task> getTaskById(
      @PathVariable("id") String id,
      @RequestParam("userId") String userId
  ) {
    return taskService.getTaskById(userId, id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<Task> addTask(
      @RequestBody Task task,
      @RequestParam("userId") String userId
  ) {
    log.info("Received request for add new task [user = {}, task = {}]", userId, task);
    Task addedTask = taskService.addTask(task, userId);
    return ResponseEntity.status(HttpStatus.CREATED).body(addedTask);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Task> updateTask(
      @PathVariable("id") String id,
      @RequestParam("userId") String userId,
      @RequestBody Task task
  ) {
    Task updatedTask = taskService.updateTask(userId, id, task);
    if (updatedTask != null) {
      return ResponseEntity.ok(updatedTask);
    }
    else {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteTask(
      @PathVariable("id") String id, @RequestParam("userId") String userId
  ) {
    boolean deleted = taskService.deleteTask(userId, id);
    if (deleted) {
      return ResponseEntity.noContent().build();
    }
    else {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping("/completed")
  public List<Task> getCompletedTasks(@RequestParam("userId") String userId) {
    List<Task> completedTasks = taskService.getCompletedTasks(userId);
    log.info(completedTasks.toString());
    return completedTasks;
  }

}
