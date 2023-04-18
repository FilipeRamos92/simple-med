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
import com.system.simplemed.model.Doctor;
import com.system.simplemed.model.Speciality;
import com.system.simplemed.repository.DoctorRepository;
import com.system.simplemed.repository.SpecialityRepository;
import com.system.simplemed.service.impl.DoctorServiceImpl;

@ExtendWith(MockitoExtension.class)
public class DoctorServiceTests {
    
    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private SpecialityRepository specialityRepository;

    @InjectMocks
    private DoctorServiceImpl doctorService;

    private Doctor doctor;

    private Speciality speciality;

    private Speciality speciality2;

    @BeforeEach
    public void init() {
        
        speciality = Speciality.builder()
            .name("Cardiologia").build();
        
        speciality2 = Speciality.builder()
            .name("Fisiologia").build();

        doctor = Doctor.builder()
            .id(1L)
            .firstName("Filipe")
            .doctorReg("1234")
            .speciality(speciality)
            .build();
    }


    @Test
    public void givenDoctorList_whenGetAllDoctors_thenReturnDoctorList() {
        
        Doctor doctor2 = Doctor.builder()
            .id(2L)
            .firstName("Jéssica")
            .build();

        given(doctorRepository.findAll()).willReturn(Arrays.asList(doctor, doctor2));

        List<Doctor> doctorList = doctorService.getAllDoctors();

        assertEquals(2, doctorList.size());
    }

    @Test
    public void givenDoctorList_whenGetAllDoctor_thenReturnEmptyDoctorList() {
        
        given(doctorRepository.findAll()).willReturn(Collections.emptyList());

        List<Doctor> doctorList = doctorService.getAllDoctors();

        assertEquals(0, doctorList.size());
    }

    @Test
    public void givenDoctorId_whenGetDoctorById_thenReturnDoctorObject() {
        
        given(doctorRepository.findById(doctor.getId())).willReturn(Optional.of(doctor));

        Doctor savedDoctor = doctorService.getDoctorById(doctor.getId());

        assertNotNull(savedDoctor);
    }

    @Test
    public void givenDoctorId_whenGetDoctorById_thenThrowsException() {
        
        given(doctorRepository.findById(doctor.getId())).willReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            doctorService.getDoctorById(doctor.getId());
        });
    }

    @Test
    public void givenDoctorObject_whenSaveDoctor_thenReturnDoctorObject() {

        given(doctorRepository.findByDoctorReg(doctor.getDoctorReg())).willReturn(Optional.empty());
        given(doctorRepository.save(doctor)).willReturn(doctor);

        Doctor savedDoctor = doctorService.createDoctor(doctor);

        assertNotNull(savedDoctor);
    }

    @Test
    public void givenDoctorObject_whenSaveDoctor_thenThrowsException() {

        given(doctorRepository.findByDoctorReg(doctor.getDoctorReg())).willReturn(Optional.of(doctor));

        assertThrows(ResourceAlreadyExistException.class, () -> {
            doctorService.createDoctor(doctor);
        });
    }

    @Test
    public void givenDoctorId_whenUpdateDoctor_thenReturnDoctorObject() {

        given(doctorRepository.findById(doctor.getId())).willReturn(Optional.of(doctor));
        given(specialityRepository.findByName("Fisiologia")).willReturn(Optional.of(speciality2));
        given(doctorRepository.save(doctor)).willReturn(doctor);

        String newFirstName = "Filipendo";
        String newDoctorReg = "4321";
        String newSpeciality = "Fisiologia";

        doctor.setFirstName(newFirstName);
        doctor.setDoctorReg(newDoctorReg);
        doctor.setSpeciality(speciality2);

        Doctor updatedDoctor = doctorService.updateDoctor(doctor.getId(), doctor);

        assertEquals(newFirstName, updatedDoctor.getFirstName());
        assertEquals(newDoctorReg, updatedDoctor.getDoctorReg());
        assertEquals(newSpeciality, updatedDoctor.getSpeciality().getName());
    }

    @Test
    public void givenDoctorId_whenUpdateDoctor_thenThrowsException() {

        assertThrows(ResourceNotFoundException.class, () -> {
            doctorService.updateDoctor(doctor.getId(), doctor);
        });
    }

    @Test
    public void givenDoctorId_whenDeleteDoctor_thenReturnNothing() {

        long doctorId = 1L;

        given(doctorRepository.findById(doctorId)).willReturn(Optional.of(doctor));

        doctorService.deleteDoctor(doctorId);

        verify(doctorRepository, times(1)).delete(doctor);
    }

}
