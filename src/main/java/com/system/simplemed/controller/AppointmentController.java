package com.system.simplemed.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.system.simplemed.exception.ResourceNotFoundException;
import com.system.simplemed.model.Appointment;
import com.system.simplemed.model.Doctor;
import com.system.simplemed.model.Patient;
import com.system.simplemed.repository.AppointmentRepository;
import com.system.simplemed.repository.DoctorRepository;
import com.system.simplemed.repository.PatientRepository;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("api/v1/")
public class AppointmentController {
    
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired 
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @GetMapping("appointments")
    public List<Appointment> getAllAppointments(){
        return appointmentRepository.findAll();
    }

    @PostMapping("/appointments/patient/{pId}/doctor/{dId}")
    public ResponseEntity<Appointment> createAppointment(
        @PathVariable Long pId, 
        @PathVariable Long dId,
        @RequestBody Appointment appointmentDetails
    ){
        Patient patient = patientRepository.findById(pId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not exist with id: " + pId));

        Doctor doctor = doctorRepository.findById(dId)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not exist with id: " + dId));

        appointmentDetails.setPatient(patient);
        appointmentDetails.setDoctor(doctor);
        Appointment createdAppointment = appointmentRepository.save(appointmentDetails);
        return ResponseEntity.ok(createdAppointment);
    }

    @DeleteMapping("appointments/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteAppointment(@PathVariable Long id){
        
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not exist with id: " + id));

            appointmentRepository.delete(appointment);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
