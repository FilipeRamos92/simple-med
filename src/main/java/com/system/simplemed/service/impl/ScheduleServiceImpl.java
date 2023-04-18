package com.system.simplemed.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.system.simplemed.exception.ResourceNotFoundException;
import com.system.simplemed.model.Schedule;
import com.system.simplemed.repository.ScheduleRepository;
import com.system.simplemed.service.ScheduleService;

@Service
public class ScheduleServiceImpl implements ScheduleService {
    
    private final ScheduleRepository scheduleRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        super();
        this.scheduleRepository = scheduleRepository;
    }

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    public Schedule getScheduleById(long id) {
        
        Schedule schedule = scheduleRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Schedule not exist with id: " + id));

        return schedule;
    }

    public Schedule createSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    public void deleteSchedule(long id) {
        Schedule schedule = scheduleRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Schedule not exist with id: " + id));

        scheduleRepository.delete(schedule);
    }

    public Schedule updateSchedule(long id, Schedule scheduleRequest) {

        Schedule schedule = scheduleRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Schedule not exist with id: " + id));

        schedule.setDateTime(scheduleRequest.getDateTime());
        schedule.setDoctor(scheduleRequest.getDoctor());

        return scheduleRepository.save(schedule);
    }

}
