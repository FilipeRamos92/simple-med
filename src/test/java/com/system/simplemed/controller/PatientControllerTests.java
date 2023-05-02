package com.system.simplemed.controller;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

import com.system.simplemed.model.Patient;
import com.system.simplemed.service.impl.PatientServiceImpl;

@WebMvcTest(PatientController.class)
public class PatientControllerTests {
    
    @Autowired
    private MockMvc mvc;

    @MockBean
    private PatientServiceImpl patientService;

    private Patient patient;

    private String baseUrl;

    @BeforeEach
    public void init() {
        baseUrl = "/api/v1/patients";

        patient = Patient.builder()
            .id(1L)
            .firstName("Filipe")
            .lastName("Ramos")
            .email("filipe@email.com").build(); 
    }

    @Test
    public void givenPatients_whenGetAllPatients_thenReturnJsonArray() throws Exception {

        // given(patientService.getAllPatients()).willReturn(Arrays.asList(patient));
        when(patientService.getAllPatients()).thenReturn(Arrays.asList(patient));

        mvc.perform(get(baseUrl)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].firstName", is(patient.getFirstName())))
            .andExpect(content().string(
                "[{\"id\":1," + 
                "\"firstName\":\"Filipe\"," + 
                "\"lastName\":\"Ramos\"}]"
            ));
    }

    @Test
    public void givenPatientId_whenGetPatientById_thenReturnJsonArray() throws Exception {

        when(patientService.getPatientById(patient.getId())).thenReturn(patient);

        mvc.perform(get(baseUrl + "/" + patient.getId())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string(
                "{\"id\":1," + 
                "\"firstName\":\"Filipe\"," + 
                "\"lastName\":\"Ramos\"}"
            ));
    }
}
