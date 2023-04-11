package com.system.simplemed.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.system.simplemed.dto.PatientDTO;
import com.system.simplemed.model.Patient;
import com.system.simplemed.repository.PatientRepository;

@Service
public class PatientService {
    
    @Autowired
    private PatientRepository patientRepository;

    public PatientDTO create(Patient patient) {
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.convert(patient);
        return patientDTO;
    }

    public List<PatientDTO> getAll() {
        PatientDTO patient = new PatientDTO();
        return patient.convertList(patientRepository.findAll());
    }
}
