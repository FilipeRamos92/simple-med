package com.system.simplemed.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import com.system.simplemed.model.Patient;

public class PatientDtoTests {
    
    private ModelMapper modelMapper = new ModelMapper();

    @Test
    public void whenConvertPatientEntityToPatientDto_thenCorrect() {
        Patient patient = Patient.builder()
            .firstName("Filipe")
            .lastName("Ramos").build();

        PatientDto patientDto = modelMapper.map(patient, PatientDto.class);
        assertEquals(patient.getFirstName(), patientDto.getFirstName());
        assertEquals(patient.getLastName(), patientDto.getLastName());
    }

    @Test
    public void whenConvertPatientDtoToPatientEntity_thenCorrect() {
        PatientDto patientDto = PatientDto.builder()
            .firstName("Zed")
            .lastName("Oliveira").build();

        Patient patient = modelMapper.map(patientDto, Patient.class);
        assertEquals(patientDto.getFirstName(), patient.getFirstName());
        assertEquals(patientDto.getLastName(), patient.getLastName());
    }
    
}
