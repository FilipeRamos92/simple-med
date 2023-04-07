package com.system.simplemed.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

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
        assertThat(specialityRepository.count()).isGreaterThan(0);
    }
}