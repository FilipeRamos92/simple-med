package com.system.simplemed.service;

import java.util.List;

import com.system.simplemed.model.Schedule;

public interface ScheduleService {
    
    public List<Schedule> getAllSchedules();

    public Schedule getScheduleById(long id);

    public Schedule createSchedule(Schedule schedule);

    public Schedule updateSchedule(long id, Schedule schedule);

    public void deleteSchedule(long id);
}
