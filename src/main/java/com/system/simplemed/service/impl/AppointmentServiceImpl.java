package com.system.simplemed.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.system.simplemed.exception.ResourceNotFoundException;
import com.system.simplemed.model.Appointment;
import com.system.simplemed.repository.AppointmentRepository;
import com.system.simplemed.repository.DoctorRepository;
import com.system.simplemed.repository.PatientRepository;
import com.system.simplemed.service.AppointmentService;

@Service
public class AppointmentServiceImpl implements AppointmentService {
    
    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    public AppointmentServiceImpl(
            AppointmentRepository appointmentRepository, 
            DoctorRepository doctorRepository, 
            PatientRepository patientRepository
        ) {
        super();
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
    }

    @Override
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    @Override
    public Appointment getAppointmentById(long id) {
        Appointment appointment = appointmentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Appointment not exist with id: " + id));
        return appointment;
    }

    @Override
    public Appointment createAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }
    
    @Override
    public Appointment updateAppointment(long id, Appointment appointmentRequest) {
        Appointment appointment = appointmentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Appointment not exist with id: " + id));

        doctorRepository.findById(appointmentRequest.getDoctor().getId())
            .orElseThrow(() -> 
                new ResourceNotFoundException("Doctor not exist with id: " + appointmentRequest.getDoctor().getId()
            ));

        patientRepository.findById(appointmentRequest.getPatient().getId())
            .orElseThrow(() -> 
                new ResourceNotFoundException("Patient not exist with id: " + appointmentRequest.getPatient().getId()
            ));

        appointment.setDoctor(appointmentRequest.getDoctor());
        appointment.setPatient(appointmentRequest.getPatient());
        appointment.setDateTime(appointmentRequest.getDateTime());

        return appointmentRepository.save(appointment);
    }

    @Override
    public void deleteAppointment(long id) {
        Appointment appointment = appointmentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Appointment not exist with id: " + id));

        appointmentRepository.delete(appointment);
    };
    
}
