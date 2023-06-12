package com.example.diploma_server.statistics;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class StatisticsData {

  private int completedTasks;
  private int completedPomodoros;
  private BigDecimal totalWorkedHours;
  private BigDecimal averageTasksPerDay;
  private BigDecimal averagePomodorosPerDay;
  private String plannedToCompleteRatio;

}
