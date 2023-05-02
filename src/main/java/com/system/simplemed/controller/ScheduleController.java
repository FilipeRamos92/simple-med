package com.system.simplemed.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.system.simplemed.model.Schedule;
import com.system.simplemed.service.ScheduleService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/schedules")
public class ScheduleController {
    
    @Autowired
    private ScheduleService scheduleService;
   
    @GetMapping
    public ResponseEntity<List<Schedule>> getAllSchedules(){
        
        List<Schedule> scheduleList = scheduleService.getAllSchedules();
        
        return new ResponseEntity<List<Schedule>>(scheduleList, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteDoctor(@PathVariable Long id){
		
        Schedule schedule = scheduleService.getScheduleById(id);
		
        scheduleService.deleteSchedule(schedule.getId());

		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);

		return new ResponseEntity<Map<String,Boolean>>(response, HttpStatus.ACCEPTED);
	}
}
