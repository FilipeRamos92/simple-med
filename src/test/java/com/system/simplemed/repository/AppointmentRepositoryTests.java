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

import com.system.simplemed.model.Appointment;
import com.system.simplemed.model.Doctor;
import com.system.simplemed.model.Patient;
import com.system.simplemed.model.Speciality;

@DataJpaTest
public class AppointmentRepositoryTests {
    
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    private Patient patient;
    private Speciality speciality;
    private Doctor doctor;
    private LocalDateTime dateTime;

    @BeforeEach
    public void init() {
        dateTime = LocalDateTime.of(2023, 4, 13, 12, 0, 0);

        patient = Patient.builder().firstName("Zed").build();
        patientRepository.save(patient);

        speciality = Speciality.builder().name("Cardiologia").occupation("Cardiologista").build();

        doctor = Doctor.builder()
            .firstName("Filipe")
            .speciality(speciality).build();
        doctorRepository.save(doctor);
    }

    @Test
    public void AppointmentRepository_SaveAll_ReturnAppointmentSaved() {
        Appointment appointment = Appointment.builder()
            .dateTime(dateTime)
            .patient(patient)
            .doctor(doctor).build();
        Appointment appointmentSaved = appointmentRepository.save(appointment);

        assertNotNull(appointmentSaved);
    }

    @Test
    public void AppointmentRepository_FindAll_ReturnMoreThanOneAppointment() {
        Appointment appointment = Appointment.builder()
            .dateTime(dateTime)
            .patient(patient)
            .doctor(doctor).build();

        Appointment appointment2 = Appointment.builder()
            .dateTime(dateTime)
            .patient(patient)
            .doctor(doctor).build();

        appointmentRepository.save(appointment);
        appointmentRepository.save(appointment2);

        List<Appointment> appointmentList = appointmentRepository.findAll();

        assertEquals(2, appointmentList.size());
    }

    @Test
    public void AppointmentRepository_FindById_ReturnAppointmentNotNull() {
        Appointment appointment = Appointment.builder()
            .dateTime(dateTime)
            .patient(patient)
            .doctor(doctor).build(); 
        appointmentRepository.save(appointment);

        Appointment appointmentSaved = appointmentRepository.findById(appointment.getId()).get();

        assertNotNull(appointmentSaved);
    }

    @Test
    public void AppointmentRepository_UpdateAppointment_ReturnAppointmentNotNull() {
        Appointment appointment = Appointment.builder()
            .dateTime(dateTime)
            .patient(patient)
            .doctor(doctor).build(); 
        appointmentRepository.save(appointment);

        Doctor newDoctor = Doctor.builder()
            .firstName("Lupita")
            .speciality(speciality).build();
        
        Appointment appointmentSaved = appointmentRepository.findById(appointment.getId()).get();
        appointmentSaved.setDoctor(newDoctor);
        Appointment appointmentUpdated = appointmentRepository.save(appointmentSaved);

        assertNotNull(appointmentUpdated);
        assertEquals("Lupita", appointmentUpdated.getDoctor().getFirstName());
    }

    @Test
    public void AppointmentRepository_DeleteAppointment_ReturnAppointmentIsEmpty() {
        Appointment appointment = Appointment.builder()
            .dateTime(dateTime)
            .patient(patient)
            .doctor(doctor).build(); 
        appointmentRepository.save(appointment);
        appointmentRepository.deleteById(appointment.getId());

        Optional<Appointment> appointmentReturn = appointmentRepository.findById(appointment.getId());
        
        assertFalse(appointmentReturn.isPresent());
    }
}
