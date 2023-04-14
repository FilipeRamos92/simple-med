package com.system.simplemed.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.system.simplemed.exception.ResourceNotFoundException;
import com.system.simplemed.model.Patient;
import com.system.simplemed.repository.PatientRepository;
import com.system.simplemed.service.impl.PatientServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class PatientServiceTests {
    
    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientServiceImpl patientService;

    private Patient patient;

    @BeforeEach
    public void init() {
        patient = Patient.builder()
            .id(1L)
            .firstName("Filipe")
            .lastName("Ramos")
            .email("filipe@email.com")
            .build();
    }

    @Test
    public void givenPatientObject_whenSavePatient_thenReturnPatientObject() {
        given(patientRepository.findByEmail(patient.getEmail()))
            .willReturn(Optional.empty());

        given(patientRepository.save(patient)).willReturn(patient);

        Patient savedPatient = patientService.createPatient(patient);

        assertNotNull(savedPatient);
    }

    @Test
    public void givenPatientObject_whenSavePatient_thenThrowsException() {
        given(patientRepository.findByEmail(patient.getEmail()))
            .willReturn(Optional.of(patient));

        assertThrows(ResourceNotFoundException.class, () -> {
            patientService.createPatient(patient);
        });

        verify(patientRepository, never()).save(any(Patient.class));
    }

    @Test
    public void givenPatientList_whenGetAllPatients_thenReturnPatientList() {
        
        Patient patient2 = Patient.builder()
            .id(2L)
            .firstName("Zed")
            .lastName("Oliveira")
            .email("zed@email.com")
            .build();

        given(patientRepository.findAll()).willReturn(Arrays.asList(patient, patient2));

        List<Patient> patientList = patientService.getAllPatients();

        assertNotNull(patientList);
        assertEquals(2, patientList.size());
    }

    @Test
    public void givenPatientList_whenGetAllPatients_thenReturnEmptyPatientList() {

        given(patientRepository.findAll()).willReturn(Collections.emptyList());

        List<Patient> patientList = patientService.getAllPatients();

        assertEquals(0, patientList.size());
    }

    @Test
    public void givenPatientId_whenGetPatientId_thenReturnPatientObject() {
        given(patientRepository.findById(1L)).willReturn(Optional.of(patient));

        Patient savedPatient = patientService.getPatientById(patient.getId());

        assertNotNull(savedPatient);
    }

    @Test
    public void givenPatientId_whenUpdatePatient_thenReturnUpdatedPatient() {
        
        given(patientRepository.findById(1L)).willReturn(Optional.of(patient));
        given(patientRepository.save(patient)).willReturn(patient);

        String newEmail = "filipendo@email.com";
        String newFirstName = "Filipendo";
        
        patient.setEmail(newEmail);
        patient.setFirstName(newFirstName);
        
        Patient updatedPatient = patientService.updatePatient(patient.getId(), patient);

        assertEquals(newEmail, updatedPatient.getEmail());
        assertEquals(newFirstName, updatedPatient.getFirstName());
    }

    @Test
    public void givenPatientId_whenDeletePatient_thenNothing() {
        
        long patientId = 1L;

        given(patientRepository.findById(patientId)).willReturn(Optional.of(patient));
        
        patientService.deletePatient(patientId);

        verify(patientRepository, times(1)).delete(patient);
    }
}
