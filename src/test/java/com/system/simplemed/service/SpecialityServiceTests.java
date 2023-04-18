package com.system.simplemed.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.system.simplemed.exception.ResourceAlreadyExistException;
import com.system.simplemed.exception.ResourceNotFoundException;
import com.system.simplemed.model.Speciality;
import com.system.simplemed.repository.SpecialityRepository;
import com.system.simplemed.service.impl.SpecialityServiceImpl;

@ExtendWith(MockitoExtension.class)
public class SpecialityServiceTests {
    
    @Mock
    private SpecialityRepository specialityRepository;

    @InjectMocks
    private SpecialityServiceImpl specialityService;

    private Speciality speciality;

    @BeforeEach
    public void init() {
        speciality = Speciality.builder()
            .id(1L)
            .name("Cardiologia")
            .build();
    }

    @Test
    public void givenSpecialityList_whenGetAllSpecialities_thenReturnSpecialityList() {

        Speciality speciality2 = Speciality.builder()
            .id(2L)
            .name("Pediatria")
            .build();

        given(specialityRepository.findAll()).willReturn(Arrays.asList(speciality, speciality2));

        List<Speciality> specialityList = specialityService.getAllSpecialities();

        assertEquals(2, specialityList.size());
    }

    @Test
    public void givenSpecialityList_whenGetAllSpecialities_thenReturnEmptySpecialityList() {
        
        given(specialityRepository.findAll()).willReturn(Collections.emptyList());

        List<Speciality> specialityList = specialityService.getAllSpecialities();

        assertEquals(0, specialityList.size());
    }

    @Test
    public void givenSpecialityId_whenGetSpecialityById_thenReturnSpecialityObject() {
        
        given(specialityRepository.findById(speciality.getId())).willReturn(Optional.of(speciality));

        Speciality savedSpeciality = specialityService.getSpecialityById(speciality.getId());

        assertNotNull(savedSpeciality);
    }

    @Test
    public void givenSpecialityObject_whenSaveSpeciality_thenReturnSpecialityObject() {

        given(specialityRepository.findByName(speciality.getName())).willReturn(Optional.empty());
        given(specialityRepository.save(speciality)).willReturn(speciality);

        Speciality savedSpeciality = specialityService.createSpeciality(speciality);

        assertNotNull(savedSpeciality);
    }

    @Test
    public void givenSpecialityId_whenGetSpecialityById_thenThrowsException() {
        
        given(specialityRepository.findById(speciality.getId())).willReturn(Optional.empty());

       assertThrows(ResourceNotFoundException.class, () -> {
        specialityService.getSpecialityById(speciality.getId());
       });
    }

    @Test
    public void givenSpecialityObject_whenCreateSpeciality_thenReturnSpecialityObject() {

        given(specialityRepository.findByName(speciality.getName())).willReturn(Optional.of(speciality));

        assertThrows(ResourceAlreadyExistException.class, () -> {
            specialityService.createSpeciality(speciality);
        });
    }

    @Test
    public void givenSpecialityId_whenUpdateSpeciality_thenReturnUpdatedSpeciality() {

        given(specialityRepository.findById(speciality.getId())).willReturn(Optional.of(speciality));
        given(specialityRepository.save(speciality)).willReturn(speciality);

        String newName = "Pediatria";
        speciality.setName(newName);

        Speciality updatedSpeciality = specialityService.updateSpeciality(speciality.getId(), speciality);

        assertEquals(newName, updatedSpeciality.getName());
    }

    @Test
    public void givenSpecialityId_whenDeleteSpeciality_thenNothing() {
        
        long specialityId = 1L;

        given(specialityRepository.findById(specialityId)).willReturn(Optional.of(speciality));

        specialityService.deleteSpeciality(specialityId);

        verify(specialityRepository, times(1)).delete(speciality);
    }

}
