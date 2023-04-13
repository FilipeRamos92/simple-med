package com.system.simplemed.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.system.simplemed.model.Patient;

@DataJpaTest
public class PatientRepositoryTests {
    
    @Autowired
    private PatientRepository patientRepository;

    @Test
    public void PatientRepository_SaveAll_ReturnSavedPatient() {
        Patient patient = Patient.builder().firstName("Filipe").build();
        Patient patientSaved = patientRepository.save(patient);

        assertNotNull(patientSaved);
        assertTrue(patient.getId() > 0);
    }

    @Test
    public void PatientRepository_FindAll_ReturnMoreThanOnePatient() {
        Patient patient = Patient.builder().firstName("Filipe").build();
        Patient patient2 = Patient.builder().firstName("Zed").build();

        patientRepository.save(patient);
        patientRepository.save(patient2);

        List<Patient> patientList = patientRepository.findAll();

        assertNotNull(patientList);
        assertEquals(2, patientList.size());
    }

    @Test
    public void PatientRepository_FindById_ReturnPatientIsNotNull() {
        Patient patient = Patient.builder().firstName("Filipe").build();
        patientRepository.save(patient);

        Patient patientSaved = patientRepository.findById(patient.getId()).get();

        assertNotNull(patientSaved);
    }

    @Test
    public void PatientRepository_UpdatePatient_ReturnPatientIsNotNull() {
        Patient patient = Patient.builder().firstName("Filipe").build();
        patientRepository.save(patient);

        Patient patientSave = patientRepository.findById(patient.getId()).get();
        
        patientSave.setFirstName("Zed");
        Patient patientUpdated = patientRepository.save(patientSave);

        assertNotNull(patientUpdated);
        assertEquals("Zed", patientUpdated.getFirstName());
    }

    @Test
    public void PatientRepository_DeletePatient_ReturnPatientIsEmpty() {
        Patient patient = Patient.builder().firstName("Filipe").build();
        patientRepository.save(patient);

        patientRepository.deleteById(patient.getId());
        Optional<Patient> patientReturn = patientRepository.findById(patient.getId());

        assertFalse(patientReturn.isPresent());
    }
}
