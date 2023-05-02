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
import static org.mockito.Mockito.when;
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
import com.system.simplemed.service.impl.DoctorServiceImpl;

@ExtendWith(MockitoExtension.class)
public class DoctorServiceTests {
    
    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private SpecialityService specialityService;

    @InjectMocks
    private DoctorServiceImpl doctorService;

    private Doctor doctor;

    private Speciality speciality;

    @BeforeEach
    public void setup() {
        
        speciality = Speciality.builder()
            .name("Cardiologia").build();
        
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

        when(doctorRepository.findAll()).thenReturn(Arrays.asList(doctor, doctor2));

        List<Doctor> doctorList = doctorService.getAllDoctors();

        assertEquals(2, doctorList.size());
    }

    @Test
    public void givenDoctorList_whenGetAllDoctor_thenReturnEmptyDoctorList() {
        
        when(doctorRepository.findAll()).thenReturn(Collections.emptyList());

        List<Doctor> doctorList = doctorService.getAllDoctors();

        assertEquals(0, doctorList.size());
    }

    @Test
    public void givenDoctorId_whenGetDoctorById_thenReturnDoctorObject() {
        
        when(doctorRepository.findById(doctor.getId())).thenReturn(Optional.of(doctor));

        Doctor savedDoctor = doctorService.getDoctorById(doctor.getId());

        assertNotNull(savedDoctor);
    }

    @Test
    public void givenDoctorId_whenGetDoctorById_thenThrowsException() {
        
        when(doctorRepository.findById(doctor.getId())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            doctorService.getDoctorById(doctor.getId());
        });

        assertEquals("Doctor not exist with id: 1", exception.getMessage());
    }

    @Test
    public void givenDoctorObject_whenSaveDoctor_thenReturnDoctorObject() {

        when(doctorRepository.findByDoctorReg(doctor.getDoctorReg())).thenReturn(Optional.empty());
        when(doctorRepository.save(doctor)).thenReturn(doctor);

        Doctor savedDoctor = doctorService.createDoctor(doctor);

        assertNotNull(savedDoctor);
    }

    @Test
    public void givenDoctorObject_whenSaveDoctor_thenThrowsException() {

        when(doctorRepository.findByDoctorReg(doctor.getDoctorReg())).thenReturn(Optional.of(doctor));

        Exception exception = assertThrows(ResourceAlreadyExistException.class, () -> {
            doctorService.createDoctor(doctor);
        });

        assertEquals(
            "Doctor already exist with doctor registry no. 1234", 
            exception.getMessage()
        );
    }

    @Test
    public void givenDoctorId_whenUpdateDoctorInfo_thenReturnDoctorObject() {

        when(doctorRepository.findById(doctor.getId())).thenReturn(Optional.of(doctor));
        when(doctorRepository.save(doctor)).thenReturn(doctor);

        String newFirstName = "Filipendo";
        String newDoctorReg = "4321";

        doctor.setFirstName(newFirstName);
        doctor.setDoctorReg(newDoctorReg);

        Doctor updatedDoctor = doctorService.updateDoctorInfo(doctor.getId(), doctor);

        assertEquals(newFirstName, updatedDoctor.getFirstName());
        assertEquals(newDoctorReg, updatedDoctor.getDoctorReg());
    }

    @Test
    public void givenDoctorId_whenUpdateDoctorSpeciality_thenReturnDoctorObject() {

        Speciality newSpeciality = Speciality.builder()
            .id(2L)
            .name("Pediatria").build();

        when(doctorRepository.findById(doctor.getId())).thenReturn(Optional.of(doctor));
        when(doctorRepository.save(doctor)).thenReturn(doctor);
        when(specialityService.getSpecialityById(newSpeciality.getId())).thenReturn(newSpeciality);

        Doctor updatedDoctor = doctorService.updateDoctorSpeciality(doctor.getId(), newSpeciality.getId());

        assertEquals(newSpeciality.getName(), updatedDoctor.getSpeciality().getName());
    }

    @Test
    public void givenDoctorId_whenDeleteDoctor_thenReturnNothing() {

        long doctorId = 1L;

        when(doctorRepository.findById(doctorId)).thenReturn(Optional.of(doctor));

        doctorService.deleteDoctor(doctorId);

        verify(doctorRepository, times(1)).delete(doctor);
    }
}
