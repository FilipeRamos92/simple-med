package com.system.simplemed.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.system.simplemed.exception.ResourceAlreadyExistException;
import com.system.simplemed.exception.ResourceNotFoundException;
import com.system.simplemed.model.Patient;
import com.system.simplemed.repository.PatientRepository;
import com.system.simplemed.service.PatientService;

@Service
public class PatientServiceImpl implements PatientService {
    
    @Autowired
    private PatientRepository patientRepository;

    @Override
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    @Override
    public Patient getPatientById(long id) {
        Optional<Patient> result = patientRepository.findById(id);
        if (result.isPresent()) {
            return result.get();
        } else {
            throw new ResourceNotFoundException("Patient not exist with id: " + id);
        }
    }

    @Override
    public Patient createPatient(Patient patient) {

        Optional<Patient> savedPatient = patientRepository.findByEmail(patient.getEmail());

        if(savedPatient.isPresent()) {
            throw new ResourceAlreadyExistException("Patient already exist with email: " + patient.getEmail());
        }
        return patientRepository.save(patient);
    }

    @Override
    public Patient updatePatient(long id, Patient patientRequest) {

        Patient patient = patientRepository.findById(patientRequest.getId())
            .orElseThrow(() -> new ResourceNotFoundException("Patient not exist with id: " + patientRequest.getId()));

        patient.setFirstName(patientRequest.getFirstName());
        patient.setLastName(patientRequest.getLastName());

        return patientRepository.save(patient);
    }

    @Override
    public void deletePatient(long id) {
        Patient patient = patientRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Patient not exist with id: " + id));

        patientRepository.delete(patient);
    }
   
}