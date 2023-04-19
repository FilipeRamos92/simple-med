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
import com.system.simplemed.model.Appointment;
import com.system.simplemed.model.Doctor;
import com.system.simplemed.model.Patient;
import com.system.simplemed.model.Speciality;
import com.system.simplemed.repository.AppointmentRepository;
import com.system.simplemed.repository.DoctorRepository;
import com.system.simplemed.repository.PatientRepository;
import com.system.simplemed.service.impl.AppointmentServiceImpl;

@ExtendWith(MockitoExtension.class)
public class AppointmentServicetests {
    
    @Mock
    public AppointmentRepository appointmentRepository;

    @Mock
    public DoctorRepository doctorRepository;

    @Mock
    public PatientRepository patientRepository;

    @InjectMocks
    public AppointmentServiceImpl appointmentService;

    private Doctor doctor;

    private Patient patient;

    private LocalDateTime dateTime;

    private Appointment appointment;

    private Speciality speciality;
    

    @BeforeEach
    public void init() {
        speciality = Speciality.builder().name("Cardiologia").build();

        doctor = Doctor.builder().firstName("Filipe").speciality(speciality).build();

        patient = Patient.builder().firstName("Zed").build();

        dateTime = LocalDateTime.of(2023, 4, 19, 12, 0, 0);

        appointment = Appointment.builder()
            .id(1L)
            .dateTime(dateTime)
            .doctor(doctor)
            .patient(patient).build();
    }

    @Test
    public void givenAppointmentList_whenGetAllAppointments_thenReturnAppointmentList() {
        Appointment appointment2 = Appointment.builder()
        .dateTime(dateTime)
        .doctor(doctor)
        .patient(patient).build();

        given(appointmentRepository.findAll()).willReturn(Arrays.asList(appointment, appointment2));

        List<Appointment> appointmentList = appointmentService.getAllAppointments();

        assertEquals(2, appointmentList.size());
    }

    @Test
    public void givenAppointmentList_whenGetAllAppointments_thenReturnEmptyAppointmentList() {

        given(appointmentRepository.findAll()).willReturn(Collections.emptyList());

        List<Appointment> appointmentList = appointmentService.getAllAppointments();

        assertEquals(0, appointmentList.size());
    }

    @Test
    public void givenAppointmentId_whenGetAppointmentById_thenReturnAppointmentObject() {

        given(appointmentRepository.findById(appointment.getId())).willReturn(Optional.of(appointment));

        Appointment savedAppointment = appointmentService.getAppointmentById(appointment.getId());

        assertNotNull(savedAppointment);
    }

    @Test
    public void givenAppoinmentId_whenGetAppointmentById_thenThrowsException() {

        assertThrows(ResourceNotFoundException.class, () -> {
            appointmentService.getAppointmentById(appointment.getId());
        });
    }

    @Test
    public void givenAppoimentObject_whenCreateAppointment_thenReturnAppointmentObject() {

        given(appointmentRepository.save(appointment)).willReturn(appointment);

        Appointment savedAppointment = appointmentService.createAppointment(appointment);

        assertNotNull(savedAppointment);
    }
    
    @Test
    public void givenAppoimentId_whenUpdateAppointment_thenReturnUpdatedAppointment() {

        given(appointmentRepository.findById(appointment.getId())).willReturn(Optional.of(appointment));
        given(doctorRepository.findById(doctor.getId())).willReturn(Optional.of(doctor));
        given(patientRepository.findById(patient.getId())).willReturn(Optional.of(patient));
        given(appointmentRepository.save(appointment)).willReturn(appointment);

        Doctor newDoctor = Doctor.builder().firstName("Jéssica").speciality(speciality).build();
        Patient newPatient = Patient.builder().firstName("Lupita").build();
        LocalDateTime newDateTime = LocalDateTime.of(2023, 4, 22, 13, 0, 0);

        Appointment newAppointment = Appointment.builder()
            .dateTime(newDateTime)
            .doctor(newDoctor)
            .patient(newPatient).build();

        Appointment updatedAppointment = appointmentService.updateAppointment(appointment.getId(), newAppointment);

        assertEquals(newDoctor.getFirstName(), updatedAppointment.getDoctor().getFirstName());
    }

    @Test
    public void givenAppoimentId_whenUpdateAppointmentHasPatientFail_thenThrowsException() {

        given(appointmentRepository.findById(appointment.getId())).willReturn(Optional.of(appointment));
        given(doctorRepository.findById(doctor.getId())).willReturn(Optional.of(doctor));

        Doctor newDoctor = Doctor.builder().firstName("Jéssica").speciality(speciality).build();
        Patient newPatient = Patient.builder().firstName("Lupita").build();
        LocalDateTime newDateTime = LocalDateTime.of(2023, 4, 22, 13, 0, 0);

        Appointment newAppointment = Appointment.builder()
            .dateTime(newDateTime)
            .doctor(newDoctor)
            .patient(newPatient).build();

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            appointmentService.updateAppointment(appointment.getId(), newAppointment);
        });

        assertEquals("Patient not exist with id: " + newAppointment.getPatient().getId(), exception.getMessage());
    }

    @Test
    public void givenAppoimentId_whenUpdateAppointmentHasDoctorFail_thenThrowsException() {

        given(appointmentRepository.findById(appointment.getId())).willReturn(Optional.of(appointment));

        Doctor newDoctor = Doctor.builder().firstName("Jéssica").speciality(speciality).build();
        Patient newPatient = Patient.builder().firstName("Lupita").build();
        LocalDateTime newDateTime = LocalDateTime.of(2023, 4, 22, 13, 0, 0);

        Appointment newAppointment = Appointment.builder()
            .dateTime(newDateTime)
            .doctor(newDoctor)
            .patient(newPatient).build();

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            appointmentService.updateAppointment(appointment.getId(), newAppointment);
        });
    
        assertEquals("Doctor not exist with id: " + newAppointment.getDoctor().getId(), exception.getMessage());
    }

    @Test
    public void givenAppointmentId_whenDeleteAppointment_thenReturnNothing() {

        long appointmentId = 1L;

        given(appointmentRepository.findById(appointmentId)).willReturn(Optional.of(appointment));

        appointmentService.deleteAppointment(appointmentId);

        verify(appointmentRepository, times(1)).delete(appointment);

    }
}
