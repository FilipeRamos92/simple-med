package com.system.simplemed.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.system.simplemed.model.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    
}
