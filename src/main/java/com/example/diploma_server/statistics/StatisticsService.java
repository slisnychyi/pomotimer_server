package com.example.diploma_server.statistics;

import com.example.diploma_server.tasks.Task;
import com.example.diploma_server.tasks.TaskData;
import com.example.diploma_server.tasks.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatisticsService {

  private final static int POMODORO_MINUTES = 25;

  private final TaskRepository taskRepository;

  public StatisticsData calculateStatistics(String userId) {
    List<Task> tasks = taskRepository.findByUserEmail(userId).stream()
        .map(TaskData::toTask).toList();

    return StatisticsData.builder()
        .completedTasks(getCompletedTasksCount(tasks))
        .completedPomodoros(getCompletedPomodorosCount(tasks))
        .totalWorkedHours(getTotalWorkedHours(tasks))
        .averageTasksPerDay(calculateAverageTasksPerDay(tasks))
        .averagePomodorosPerDay(calculateAveragePomodorosPerDay(tasks))
        .plannedToCompleteRatio(calculatePlannedToCompleteRatio(tasks))
        .build();
  }

  private int getCompletedTasksCount(List<Task> tasks) {
    return (int) tasks.stream()
        .filter(Task::isCompleted)
        .count();
  }

  private int getCompletedPomodorosCount(List<Task> tasks) {
    return tasks.stream()
        .filter(Task::isCompleted)
        .mapToInt(Task::getCompletedPomodoros)
        .sum();
  }

  private BigDecimal getTotalWorkedHours(List<Task> tasks) {
    int sum = tasks.stream()
        .filter(Task::isCompleted)
        .mapToInt(task -> task.getCompletedPomodoros() * POMODORO_MINUTES)
        .sum();
    return new BigDecimal(sum).divide(BigDecimal.valueOf(60), 2, RoundingMode.HALF_UP);
  }

  private BigDecimal calculateAverageTasksPerDay(List<Task> tasks) {
    Map<LocalDate, List<Task>> collect = tasks.stream()
        .collect(Collectors.groupingBy(Task::getDate));
    long taskCount = collect.values().stream().mapToLong(Collection::size).sum();
    return BigDecimal.valueOf(taskCount)
        .divide(BigDecimal.valueOf(collect.size()), 2, RoundingMode.HALF_UP);
  }

  private BigDecimal calculateAveragePomodorosPerDay(List<Task> tasks) {
    Map<LocalDate, List<Task>> collect = tasks.stream()
        .collect(Collectors.groupingBy(Task::getDate));
    int days = collect.size();
    int pomodoros = collect.values().stream()
        .flatMap(Collection::stream)
        .mapToInt(Task::getPomodoros)
        .sum();
    return BigDecimal.valueOf(pomodoros)
        .divide(BigDecimal.valueOf(days), 2, RoundingMode.HALF_UP);
  }

  private String calculatePlannedToCompleteRatio(List<Task> tasks) {
    Map<Boolean, List<Task>> tasksByStatus = tasks.stream()
        .collect(Collectors.groupingBy(Task::isCompleted));

    int completedPomodoros = tasksByStatus.get(true).stream()
        .mapToInt(Task::getPomodoros)
        .sum();

    int plannedPomodoros = tasksByStatus.get(false).stream()
        .mapToInt(Task::getPomodoros)
        .sum();

    return String.format("%s:%s", 1, BigDecimal.valueOf(plannedPomodoros)
        .divide(BigDecimal.valueOf(completedPomodoros), 1, RoundingMode.HALF_UP));
  }

}
