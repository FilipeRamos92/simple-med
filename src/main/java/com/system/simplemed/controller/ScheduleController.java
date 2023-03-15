package com.system.simplemed.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.system.simplemed.exception.ResourceNotFoundException;
import com.system.simplemed.model.Doctor;
import com.system.simplemed.model.Schedule;
import com.system.simplemed.repository.DoctorRepository;
import com.system.simplemed.repository.ScheduleRepository;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/")
public class ScheduleController {
    
    @Autowired
    private ScheduleRepository scheduleRepository;
   
    @Autowired
    private DoctorRepository doctorRepository;

    @GetMapping("schedules")
    public Iterable<Schedule> getAllSchedules(){
        return scheduleRepository.findAll();
    }

    @PostMapping("/schedules")
    public Schedule createSchedule(@RequestBody Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    @PutMapping("/doctors/{id}/schedules")
	public Schedule updateDoctor(@PathVariable Long id, @RequestBody Schedule schedule){
		Doctor doctor = doctorRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Doctor not exist with id: " + id));
        
        schedule.setDoctor(doctor);

		return scheduleRepository.save(schedule);
	}

    @DeleteMapping("/schedules/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteDoctor(@PathVariable Long id){
		Schedule schedule = scheduleRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Schedule not exist with id :" + id));
		
                scheduleRepository.delete(schedule);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
}
