package com.system.simplemed.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.system.simplemed.model.Doctor;
import com.system.simplemed.model.Schedule;
import com.system.simplemed.model.Speciality;

@DataJpaTest
public class ScheduleRepositoryTests {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    private Speciality speciality;
    private Doctor doctor;
    private LocalDateTime dateTime;

    @BeforeEach
    public void init() {
        dateTime = LocalDateTime.of(2023, 4, 13, 12, 0, 0);
        
        speciality = Speciality.builder().name("Cardiologia").build();

        doctor = Doctor.builder()
            .firstName("Filipe")
            .speciality(speciality).build();

        doctorRepository.save(doctor);
    }

    @Test
    public void ScheduleRepository_SaveAll_ReturnSavedSchedule() {
        
        Schedule schedule = Schedule.builder()
            .doctor(doctor)
            .dateTime(dateTime).build();
        
        Schedule scheduleSaved = scheduleRepository.save(schedule);
        
        assertNotNull(scheduleSaved);
    }

    @Test
    public void ScheduleRepository_FindAll_ReturnMoreThanOneSchedule() {
        Schedule schedule = Schedule.builder()
        .doctor(doctor)
        .dateTime(dateTime).build();

        Schedule schedule2 = Schedule.builder()
        .doctor(doctor)
        .dateTime(dateTime.plusHours(1)).build();

        scheduleRepository.save(schedule);
        scheduleRepository.save(schedule2);

        List<Schedule> scheduleList = scheduleRepository.findAll();

        assertEquals(2, scheduleList.size());
    }

    @Test
    public void ScheduleRepository_FindById_ReturnScheduleIsNotNull() {
        Schedule schedule = Schedule.builder()
        .doctor(doctor)
        .dateTime(dateTime).build();

        scheduleRepository.save(schedule);

        Schedule scheduleSaved = scheduleRepository.findById(schedule.getId()).get();

        assertNotNull(scheduleSaved);
    }

    @Test
    public void ScheduleRepository_UpdateSchedule_ReturnScheduleIsNotNull() {
        Schedule schedule = Schedule.builder()
        .doctor(doctor)
        .dateTime(dateTime).build();

        LocalDateTime newDate = LocalDateTime.of(2023, 4, 12, 14, 0, 0);
        Doctor newDoctor = Doctor.builder()
            .firstName("Zedinho")
            .speciality(speciality).build();

        scheduleRepository.save(schedule);
        Schedule scheduleSaved = scheduleRepository.findById(schedule.getId()).get();
        scheduleSaved.setDateTime(newDate);
        scheduleSaved.setDoctor(newDoctor);

        Schedule scheduleUpdated = scheduleRepository.save(scheduleSaved);

        assertNotNull(scheduleUpdated);
        assertEquals("Zedinho", scheduleSaved.getDoctor().getFirstName());
        assertEquals(12, scheduleUpdated.getDateTime().getDayOfMonth());
    }

    @Test
    public void ScheduleRepository_DeleteSchedule_ReturnScheduleEmpty() {
        Schedule schedule = Schedule.builder()
        .doctor(doctor)
        .dateTime(dateTime).build();

        scheduleRepository.save(schedule);
        scheduleRepository.deleteById(schedule.getId());

        Optional<Schedule> scheduleReturn = scheduleRepository.findById(schedule.getId());

        assertFalse(scheduleReturn.isPresent());
    }
}
