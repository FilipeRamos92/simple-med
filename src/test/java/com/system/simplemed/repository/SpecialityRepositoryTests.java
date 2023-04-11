package com.system.simplemed.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import com.system.simplemed.model.Speciality;

@DataJpaTest
public class SpecialityRepositoryTests {

    @Autowired
    private SpecialityRepository specialityRepository;

    @Test
    public void SpecialityRepository_SaveAll_ReturnSavedSpeciality() {
        Speciality speciality = Speciality.builder()
            .name("Cardiologia")
            .build();

        Speciality savedSpeciality = specialityRepository.save(speciality);

        assertThat(savedSpeciality).isNotNull();
        assertThat(savedSpeciality.getId()).isGreaterThan(0);
    }

    @Test
    public void SpecialityRepository_FindAll_ReturnMoreThanOneSpeciality() {
        Speciality speciality = Speciality.builder()
            .name("Cardiologia").build();

        Speciality speciality2 = Speciality.builder()
            .name("Pediatria").build();

        specialityRepository.save(speciality);
        specialityRepository.save(speciality2);

        List<Speciality> specialityList = specialityRepository.findAll();

        assertThat(specialityList).isNotNull();
        assertThat(specialityList.size()).isEqualTo(2);
    }

    @Test
    public void SpecialityRepository_FindById_ReturnSpeciality() {
        Speciality speciality = Speciality.builder()
            .name("Cardiologia").build();

        specialityRepository.save(speciality);
        
        Speciality specialityById = specialityRepository.findById(speciality.getId()).get();

        assertThat(specialityById).isNotNull();
    }

    @Test
    public void SpecialityRepository_FindByName_ReturnSpecialityNotNull() {
        Speciality speciality = Speciality.builder()
            .name("Cardiologia").build();

        specialityRepository.save(speciality);
        Speciality specialitySaved = specialityRepository.findByName(speciality.getName()).get();

        assertThat(specialitySaved).isNotNull();
    }

    @Test
    public void SpecialityRepository_UpdateSpeciality_ReturnSpecialityNotNull() {
        Speciality speciality = Speciality.builder()
            .name("Cardiologia").build();

        specialityRepository.save(speciality);
        speciality.setName("Pediatria");

        specialityRepository.save(speciality);

        Speciality specialityUpdated = specialityRepository.findById(speciality.getId()).get();

        assertThat(specialityUpdated).isNotNull();
        assertThat(specialityUpdated.getName()).isEqualTo("Pediatria");
    }

    @Test
    public void SpecialityRepository_DeleteSpeciality_ReturnSpecialityIsEmpty() {
        Speciality speciality = Speciality.builder()
        .name("Cardiologia").build();

        specialityRepository.save(speciality);
        specialityRepository.deleteById(speciality.getId());
        Optional<Speciality> specialityReturn = specialityRepository.findById(speciality.getId());

        assertThat(specialityReturn).isEmpty();
    }
}