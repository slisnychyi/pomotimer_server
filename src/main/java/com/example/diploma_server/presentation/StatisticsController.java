package com.example.diploma_server.presentation;

import com.example.diploma_server.statistics.StatisticsData;
import com.example.diploma_server.statistics.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/statistics")
@RequiredArgsConstructor
public class StatisticsController {

  private final StatisticsService statisticsService;

  @GetMapping
  public StatisticsData getStatistics(@RequestParam("userId") String userId) {
    return statisticsService.calculateStatistics(userId);
  }

}
