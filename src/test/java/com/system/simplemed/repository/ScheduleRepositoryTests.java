package com.system.simplemed.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.system.simplemed.model.Doctor;
import com.system.simplemed.model.Schedule;
import com.system.simplemed.model.ScheduleStatus;
import com.system.simplemed.model.Speciality;

@DataJpaTest
public class ScheduleRepositoryTests {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    private Speciality speciality;
    private Doctor doctor;
    private LocalDate date;
    private LocalTime time;
    private Schedule schedule;

    @BeforeEach
    public void init() {
        date = LocalDate.parse("2022-05-02");
        time = LocalTime.parse("13:00");
        
        speciality = Speciality.builder().name("Cardiologia").occupation("Cardiologista").build();

        doctor = Doctor.builder()
            .firstName("Filipe")
            .speciality(speciality)
            .schedules(Arrays.asList(schedule)).build();

        doctorRepository.save(doctor);

        schedule = Schedule.builder()
        .date(date)
        .time(time)
        .status(ScheduleStatus.DISPONIVEL)
        .doctor(doctor).build();
    }

    @Test
    public void ScheduleRepository_SaveAll_ReturnSavedSchedule() {
        
        Schedule scheduleSaved = scheduleRepository.save(schedule);
        
        assertNotNull(scheduleSaved);
    }

    @Test
    public void ScheduleRepository_FindAll_ReturnMoreThanOneSchedule() {
        Schedule schedule = Schedule.builder()
        .doctor(doctor)
        .date(date).build();

        Schedule schedule2 = Schedule.builder()
        .doctor(doctor)
        .date(date).build();

        scheduleRepository.save(schedule);
        scheduleRepository.save(schedule2);

        List<Schedule> scheduleList = scheduleRepository.findAll();

        assertEquals(2, scheduleList.size());
    }

    @Test
    public void ScheduleRepository_FindById_ReturnScheduleIsNotNull() {
        Schedule schedule = Schedule.builder()
        .doctor(doctor)
        .date(date).build();

        scheduleRepository.save(schedule);

        Schedule scheduleSaved = scheduleRepository.findById(schedule.getId()).get();

        assertNotNull(scheduleSaved);
    }

    @Test
    public void ScheduleRepository_UpdateSchedule_ReturnScheduleIsNotNull() {
        Schedule schedule = Schedule.builder()
        .doctor(doctor)
        .date(date).build();

        LocalDate newDate = LocalDate.parse("2022-04-28");
        Doctor newDoctor = Doctor.builder()
            .firstName("Zedinho")
            .speciality(speciality).build();

        scheduleRepository.save(schedule);
        Schedule scheduleSaved = scheduleRepository.findById(schedule.getId()).get();
        scheduleSaved.setDate(newDate);
        scheduleSaved.setDoctor(newDoctor);

        Schedule scheduleUpdated = scheduleRepository.save(scheduleSaved);

        assertNotNull(scheduleUpdated);
        assertEquals("Zedinho", scheduleSaved.getDoctor().getFirstName());
        assertEquals(28, scheduleUpdated.getDate().getDayOfMonth());
    }

    @Test
    public void ScheduleRepository_DeleteSchedule_ReturnScheduleEmpty() {
        Schedule schedule = Schedule.builder()
        .doctor(doctor)
        .date(date).build();

        scheduleRepository.save(schedule);
        scheduleRepository.deleteById(schedule.getId());

        Optional<Schedule> scheduleReturn = scheduleRepository.findById(schedule.getId());

        assertFalse(scheduleReturn.isPresent());
    }
}
