package com.system.simplemed.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.system.simplemed.exception.ResourceNotFoundException;
import com.system.simplemed.model.Doctor;
import com.system.simplemed.model.Schedule;
import com.system.simplemed.model.Speciality;
import com.system.simplemed.repository.DoctorRepository;
import com.system.simplemed.repository.ScheduleRepository;
import com.system.simplemed.service.impl.ScheduleServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ScheduleServiceTests {
    
    @Mock
    public ScheduleRepository scheduleRepository;

    @Mock
    public DoctorRepository doctorRepository;

    @InjectMocks
    public ScheduleServiceImpl scheduleService;

    public Speciality speciality;

    public Doctor doctor;
    
    public Schedule schedule;

    public LocalDateTime dateTime;

    @BeforeEach
    public void init() {
        
        speciality = Speciality.builder().name("Cardiologia").build();

        doctor = Doctor.builder().speciality(speciality).firstName("Filipe").build();

        dateTime = LocalDateTime.of(2023, 4, 17, 12, 0, 0);

        schedule = Schedule.builder()
            .id(1L)
            .dateTime(dateTime)
            .doctor(doctor).build();
    }

    @Test
    public void givenScheduleList_whenGetAllSchedules_thenReturnScheduleList() {
        
        Schedule schedule2 = Schedule.builder()
            .id(2L)
            .dateTime(dateTime)
            .doctor(doctor).build();

        given(scheduleRepository.findAll()).willReturn(Arrays.asList(schedule, schedule2));

        List<Schedule> scheduleList = scheduleService.getAllSchedules();

        assertEquals(2, scheduleList.size());
    }

    @Test
    public void givenScheduleList_whenGetAllSchedules_thenReturnEmptyScheduleList() {

        given(scheduleRepository.findAll()).willReturn(Collections.emptyList());

        List<Schedule> scheduleList = scheduleService.getAllSchedules();

        assertEquals(0, scheduleList.size());
    }

    @Test
    public void givenScheduleId_whenGetScheduleById_thenReturnScheduleObject() {

        given(scheduleRepository.findById(schedule.getId())).willReturn(Optional.of(schedule));

        Schedule savedSchedule = scheduleService.getScheduleById(schedule.getId());

        assertNotNull(savedSchedule);
    }

    @Test
    public void givenScheduleId_whenGetScheduleById_thenThrowsException() {

        assertThrows(ResourceNotFoundException.class, () -> {
            scheduleService.getScheduleById(schedule.getId());
        });
    }

    @Test
    public void givenScheduleObject_whenSaveSchedule_thenReturnScheduleObject() {
        
        given(scheduleRepository.save(schedule)).willReturn(schedule);

        Schedule saveSchedule = scheduleService.createSchedule(schedule);

        assertNotNull(saveSchedule);
    }

    @Test
    public void givenScheduleId_whenUpdateSchedule_thenReturnNewScheduleObject() {
        
        given(scheduleRepository.findById(schedule.getId())).willReturn(Optional.of(schedule));
        given(scheduleRepository.save(schedule)).willReturn(schedule);

        LocalDateTime newDateTime = LocalDateTime.of(2023, 4, 18, 12, 0, 0);
        Doctor newDoctor = Doctor.builder().speciality(speciality).firstName("Jéssica").build();

        schedule.setDateTime(newDateTime);
        schedule.setDoctor(newDoctor);

        Schedule updatedSchedule = scheduleService.updateSchedule(schedule.getId(), schedule);

        assertEquals(newDateTime, updatedSchedule.getDateTime());
        assertEquals(newDoctor, updatedSchedule.getDoctor());
    }

    @Test
    public void givenScheduleId_whenDeleteSchedule_thenReturnNothing() {

        long scheduleId = 1L;

        given(scheduleRepository.findById(scheduleId)).willReturn(Optional.of(schedule));

        scheduleService.deleteSchedule(scheduleId);

        verify(scheduleRepository, times(1)).delete(schedule);

    }

}
