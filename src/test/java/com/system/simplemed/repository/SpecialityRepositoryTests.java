package com.system.simplemed.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import com.system.simplemed.model.Speciality;

@DataJpaTest
public class SpecialityRepositoryTests {

    @Autowired
    private SpecialityRepository specialityRepository;

    Speciality speciality;

    @BeforeEach
    public void setup() {
        
        speciality = Speciality.builder().name("Cardiologia").occupation("Cardiologista").build();

    }

    @Test
    public void SpecialityRepository_SaveAll_ReturnSavedSpeciality() {
        
        Speciality savedSpeciality = specialityRepository.save(speciality);

        assertNotNull(savedSpeciality);
        assertTrue(savedSpeciality.getId() > 0);
    }

    @Test
    public void SpecialityRepository_FindAll_ReturnMoreThanOneSpeciality() {
        
        Speciality speciality2 = Speciality.builder().name("Pediatria").occupation("Pediatra").build();

        specialityRepository.save(speciality);
        specialityRepository.save(speciality2);

        List<Speciality> specialityList = specialityRepository.findAll();

        assertNotNull(specialityList);
        assertEquals(2, specialityList.size());
    }

    @Test
    public void SpecialityRepository_FindById_ReturnSpeciality() {
        
        specialityRepository.save(speciality);
        
        Speciality specialityById = specialityRepository.findById(speciality.getId()).get();

        assertNotNull(specialityById);
    }

    @Test
    public void SpecialityRepository_FindByName_ReturnSpecialityNotNull() {
        
        specialityRepository.save(speciality);

        Speciality specialitySaved = specialityRepository.findByName(speciality.getName()).get();

        assertNotNull(specialitySaved);
    }

    @Test
    public void SpecialityRepository_UpdateSpeciality_ReturnSpecialityNotNull() {
        
        specialityRepository.save(speciality);
        
        Speciality specialitySave = specialityRepository.findById(speciality.getId()).get();
        specialitySave.setName("Pediatria");

        Speciality specialityUpdated = specialityRepository.save(specialitySave);

        assertNotNull(specialityUpdated);
        assertEquals("Pediatria", specialityUpdated.getName());
    }

    @Test
    public void SpecialityRepository_DeleteSpeciality_ReturnSpecialityIsEmpty() {
        
        specialityRepository.save(speciality);

        specialityRepository.deleteById(speciality.getId());
        Optional<Speciality> specialityReturn = specialityRepository.findById(speciality.getId());

        assertFalse(specialityReturn.isPresent());
    }
}