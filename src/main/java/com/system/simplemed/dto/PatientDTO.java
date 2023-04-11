package com.system.simplemed.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.system.simplemed.model.Patient;

import lombok.Data;

@Data
public class PatientDTO {
    private String firstName;
    private String lastName;

    public PatientDTO convert(Patient patient) {
        BeanUtils.copyProperties(patient, this,
            "id", "appointments", "cellphone", "gender", "birthdate", "email");
        return this;
    }

    public List<PatientDTO> convertList(List<Patient> listPatient) {
        PatientDTO patientDTO = new PatientDTO();
        List<PatientDTO> patientDTOList = new ArrayList<>();
        listPatient.forEach(p -> {
            patientDTOList.add(patientDTO.convert(p));
        });
        
        return patientDTOList;
    }
}
