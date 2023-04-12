package com.system.simplemed.service;

import java.util.List;

import com.system.simplemed.model.Patient;

public interface PatientService {
    
    List<Patient> getAllPatients();
    
    Patient getPatientById(long id);

    Patient createPatient(Patient patient);
    
    // Patient updatePatient(long id, Patient patient);
    
    
    // void deletePatient(long id);	
}
