package com.example.diploma_server.tasks;

import com.example.diploma_server.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {

  private static final long[] NEXT_DAYS_RANGE = new long[] { 2, 5 };
  private static final long LAST_DAYS = 3;

  private final StabTaskRepository stabTaskRepository;
  private final TaskRepository taskRepository;
  private final UserRepository userRepository;

  public List<Task> getAllTasks() {
    return stabTaskRepository.getTasks();
  }

  public List<Task> getTasks(LocalDate date, String userId) {
    return taskRepository.findByUserEmail(userId).stream()
        .filter(e -> e.getDate().equals(date))
        .map(TaskData::toTask)
        .toList();
  }

  public List<Task> getTasksForNextDays(String userId) {
    LocalDate startDate = LocalDate.now().plusDays(NEXT_DAYS_RANGE[0]);
    LocalDate endDate = LocalDate.now().plusDays(NEXT_DAYS_RANGE[1]);
    return taskRepository.findByUserEmailAndDateBetween(userId, startDate, endDate).stream()
        .map(TaskData::toTask).toList();
  }

  public Optional<Task> getTaskById(String userId, String taskId) {
    return taskRepository.findByUserEmailAndTaskId(userId, taskId).map(TaskData::toTask);
  }

  public Task addTask(Task task, String userId) {
    return userRepository.findByEmail(userId)
        .map(e -> new TaskData().fromTask(task, e))
        .map(taskRepository::save)
        .map(TaskData::toTask)
        .orElseThrow(() -> new IllegalStateException("no such user"));
  }

  public boolean deleteTask(String userId, String taskId) {
    return taskRepository.findByUserEmailAndTaskId(userId, taskId)
        .map(TaskData::getId)
        .map(e -> {
          taskRepository.deleteById(e);
          return true;
        })
        .orElseGet(() -> false);
  }

  public Task updateTask(String userId, String id, Task task) {
    deleteTask(userId, id);
    return addTask(task, userId);
  }

  public List<Task> getCompletedTasks(String userId) {
    LocalDate dateRange = LocalDate.now().minusDays(LAST_DAYS);
    return taskRepository.findByUserEmailAndCompletedIsTrueAndDateAfter(userId, dateRange).stream()
        .map(TaskData::toTask).toList();
  }

}
