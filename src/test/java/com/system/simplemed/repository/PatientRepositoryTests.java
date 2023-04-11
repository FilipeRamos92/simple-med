package com.system.simplemed.repository;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
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

        Assertions.assertThat(patientSaved).isNotNull();
        Assertions.assertThat(patient.getId()).isGreaterThan(0);
    }

    @Test
    public void PatientRepository_FindAll_ReturnMoreThanOnePatient() {
        Patient patient = Patient.builder().firstName("Filipe").build();
        Patient patient2 = Patient.builder().firstName("Zed").build();

        patientRepository.save(patient);
        patientRepository.save(patient2);

        List<Patient> patientList = patientRepository.findAll();

        Assertions.assertThat(patientList).isNotNull();
        Assertions.assertThat(patientList.size()).isEqualTo(2);
    }

    @Test
    public void PatientRepository_FindById_ReturnPatientIsNotNull() {
        Patient patient = Patient.builder().firstName("Filipe").build();
        patientRepository.save(patient);

        Patient patientSaved = patientRepository.findById(patient.getId()).get();

        Assertions.assertThat(patientSaved).isNotNull();
    }

    @Test
    public void PatientRepository_UpdatePatient_ReturnPatientIsNotNull() {
        Patient patient = Patient.builder().firstName("Filipe").build();
        patientRepository.save(patient);

        Patient patientSave = patientRepository.findById(patient.getId()).get();
        
        patientSave.setFirstName("Zed");
        Patient patientUpdated = patientRepository.save(patientSave);

        Assertions.assertThat(patientUpdated).isNotNull();
        Assertions.assertThat(patientUpdated.getFirstName()).isEqualTo("Zed");
    }

    @Test
    public void PatientRepository_DeletePatient_ReturnPatientIsEmpty() {
        Patient patient = Patient.builder().firstName("Filipe").build();
        patientRepository.save(patient);

        patientRepository.deleteById(patient.getId());
        Optional<Patient> patientReturn = patientRepository.findById(patient.getId());

        Assertions.assertThat(patientReturn).isEmpty();
    }
}
