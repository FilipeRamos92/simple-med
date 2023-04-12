package com.system.simplemed.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.system.simplemed.exception.ResourceNotFoundException;
import com.system.simplemed.model.Patient;
import com.system.simplemed.repository.PatientRepository;
import com.system.simplemed.service.PatientService;

@Service
public class PatientServiceImpl implements PatientService {
    
    private final PatientRepository patientRepository;

    public PatientServiceImpl(PatientRepository patientRepository) {
        super();
        this.patientRepository = patientRepository;
    }

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
        return patientRepository.save(patient);
    }
   
}