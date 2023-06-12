package com.example.diploma_server.tasks;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class StabTaskRepository {

  private final List<Task> tasks;

  public StabTaskRepository(List<Task> tasks) {
    this.tasks = new ArrayList<>(List.of(
        new Task("1", "task1", LocalDate.now(), false, 10, 1, "some task note"),
        new Task("2", "task2", LocalDate.now(), false, 1, 2, "some task note2"),
        new Task("2a", "task2a", LocalDate.now(), true, 1, 2, "some task note2a"),
        new Task("3a", "task3a", LocalDate.now().minusDays(1), true, 3, 2, "some task note3a"),
        new Task("4a", "task4a", LocalDate.now().minusDays(1), true, 4, 2, "some task note4a"),
        new Task("3", "task3", LocalDate.now().plusDays(1), false, 1, 2, "some task note2"),
        new Task("4", "task4", LocalDate.now().plusDays(5), false, 1, 2, "some task note4")

    ));
  }

  public List<Task> getByDateRange(LocalDate startDay, LocalDate endDate) {
    return tasks.stream()
        .filter(e -> e.getDate().isAfter(startDay) && e.getDate().isBefore(endDate))
        .toList();
  }

  public List<Task> getTasks() {
    return tasks;
  }

  public Optional<Task> getTask(String id) {
    return tasks.stream().filter(e -> e.getId().equals(id)).findFirst();
  }

  public void addTask(Task task) {
    tasks.add(task);
  }

  public boolean deleteTask(String id) {
    return getTask(id)
        .map(tasks::remove)
        .orElseGet(() -> false);
  }

  public List<Task> getCompletedTasks(LocalDate lastDays) {
    return tasks.stream()
        .filter(Task::isCompleted)
        .filter(e -> e.getDate().isAfter(lastDays))
        .toList();
  }

}
