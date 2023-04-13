package com.system.simplemed.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.system.simplemed.model.Doctor;
import com.system.simplemed.model.Speciality;

@DataJpaTest
public class DoctorReposityTests {
    
    @Autowired
    private DoctorRepository doctorRepository;

    @Test
    public void DoctorRepository_SaveAll_ReturnSavedDoctor() {
        Speciality speciality = Speciality.builder().name("Fisiologia").build();
        
        Doctor doctor = Doctor.builder()
            .firstName("Muzy")
            .speciality(speciality).build();

        Doctor doctorSaved = doctorRepository.save(doctor);
        assertNotNull(doctorSaved);
    }

    @Test
    public void DoctorRepository_FindAll_ReturnMoreThanOneDoctor() {
        Speciality speciality = Speciality.builder().name("Cardiologia").build();

        Doctor doctor = Doctor.builder()
            .firstName("Filipe")
            .speciality(speciality).build();

        Doctor doctor2 = Doctor.builder()
            .firstName("Zed")
            .speciality(speciality).build();

        doctorRepository.save(doctor);
        doctorRepository.save(doctor2);
        
        List<Doctor> doctorList = doctorRepository.findAll();

        assertNotNull(doctorList);
        assertEquals(2, doctorList.size());
    }

    @Test
    public void DoctorRepository_FindById_ReturnDoctorIsNotNull() {
        Speciality speciality = Speciality.builder().name("Cardiologia").build();

        Doctor doctor = Doctor.builder()
            .firstName("Filipe")
            .speciality(speciality).build();

        doctorRepository.save(doctor);

        Doctor doctorSaved = doctorRepository.findById(doctor.getId()).get();

        assertNotNull(doctorSaved);
    }

    @Test
    public void DoctorRepository_UpdateDoctor_ReturnDoctorNotNull() {
        Speciality speciality = Speciality.builder().name("Cardiologia").build();

        Doctor doctor = Doctor.builder()
            .firstName("Filipe")
            .speciality(speciality).build();
        
        doctorRepository.save(doctor);

        Doctor doctorSave = doctorRepository.findById(doctor.getId()).get();
        doctorSave.setFirstName("Zed");
        
        Doctor doctorUpdated = doctorRepository.save(doctorSave);

        assertNotNull(doctorUpdated);
        assertEquals("Zed", doctorUpdated.getFirstName());
    }

    @Test
    public void DoctorRepository_DeleteDoctor_ReturnDoctorIsEmpty() {
        Speciality speciality = Speciality.builder().name("Cardiologia").build();

        Doctor doctor = Doctor.builder()
            .firstName("Filie")
            .speciality(speciality).build();
        
        doctorRepository.save(doctor);
        
        doctorRepository.deleteById(doctor.getId());
        
        Optional<Doctor> doctorReturn = doctorRepository.findById(doctor.getId());

        assertFalse(doctorReturn.isPresent());
    }
}
