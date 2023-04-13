package com.system.simplemed.repository;

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

    @Test
    public void SpecialityRepository_SaveAll_ReturnSavedSpeciality() {
        Speciality speciality = Speciality.builder().name("Cardiologia").build();
        Speciality savedSpeciality = specialityRepository.save(speciality);

        assertNotNull(savedSpeciality);
        assertTrue(savedSpeciality.getId() > 0);
    }

    @Test
    public void SpecialityRepository_FindAll_ReturnMoreThanOneSpeciality() {
        Speciality speciality = Speciality.builder().name("Cardiologia").build();
        Speciality speciality2 = Speciality.builder().name("Pediatria").build();

        specialityRepository.save(speciality);
        specialityRepository.save(speciality2);

        List<Speciality> specialityList = specialityRepository.findAll();

        assertNotNull(specialityList);
        assertEquals(2, specialityList.size());
    }

    @Test
    public void SpecialityRepository_FindById_ReturnSpeciality() {
        Speciality speciality = Speciality.builder().name("Cardiologia").build();
        specialityRepository.save(speciality);
        
        Speciality specialityById = specialityRepository.findById(speciality.getId()).get();

        assertNotNull(specialityById);
    }

    @Test
    public void SpecialityRepository_FindByName_ReturnSpecialityNotNull() {
        Speciality speciality = Speciality.builder().name("Cardiologia").build();
        specialityRepository.save(speciality);

        Speciality specialitySaved = specialityRepository.findByName(speciality.getName()).get();

        assertNotNull(specialitySaved);
    }

    @Test
    public void SpecialityRepository_UpdateSpeciality_ReturnSpecialityNotNull() {
        Speciality speciality = Speciality.builder().name("Cardiologia").build();
        specialityRepository.save(speciality);
        
        Speciality specialitySave = specialityRepository.findById(speciality.getId()).get();
        specialitySave.setName("Pediatria");
        Speciality specialityUpdated = specialityRepository.save(specialitySave);

        assertNotNull(specialityUpdated);
        assertEquals("Pediatria", specialityUpdated.getName());
    }

    @Test
    public void SpecialityRepository_DeleteSpeciality_ReturnSpecialityIsEmpty() {
        Speciality speciality = Speciality.builder().name("Cardiologia").build();
        specialityRepository.save(speciality);

        specialityRepository.deleteById(speciality.getId());
        Optional<Speciality> specialityReturn = specialityRepository.findById(speciality.getId());

        assertFalse(specialityReturn.isPresent());
    }
}