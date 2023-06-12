package com.example.diploma_server.tasks;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<TaskData, Long> {

  List<TaskData> findByUserEmail(String userEmail);

  List<TaskData> findByUserEmailAndDateBetween(String userEmail, LocalDate startDay, LocalDate endDate);

  Optional<TaskData> findByUserEmailAndTaskId(String userEmail, String taskId);

  List<TaskData> findByUserEmailAndCompletedIsTrueAndDateAfter(String userEmail, LocalDate lastDays);

}
