package com.system.simplemed.repository;

import org.springframework.data.repository.CrudRepository;

import com.system.simplemed.model.Schedule;

public interface ScheduleRepository extends CrudRepository<Schedule, Long> {
    
}
