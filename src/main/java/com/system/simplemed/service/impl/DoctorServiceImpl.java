package com.system.simplemed.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.system.simplemed.exception.ResourceAlreadyExistException;
import com.system.simplemed.exception.ResourceNotFoundException;
import com.system.simplemed.model.Doctor;
import com.system.simplemed.model.Speciality;
import com.system.simplemed.repository.DoctorRepository;
import com.system.simplemed.repository.SpecialityRepository;
import com.system.simplemed.service.DoctorService;

@Service
public class DoctorServiceImpl implements DoctorService {
    
    private final DoctorRepository doctorRepository;

    private final SpecialityRepository specialityRepository;

    public DoctorServiceImpl(DoctorRepository doctorRepository, SpecialityRepository specialityRepository) {
        super();
        this.doctorRepository = doctorRepository;
        this.specialityRepository = specialityRepository;
    }

    @Override
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    @Override
    public Doctor getDoctorById(long id) {
        
        Optional<Doctor> savedDoctor = doctorRepository.findById(id);
        if (savedDoctor.isPresent()) {
            return savedDoctor.get();
        }
        throw new ResourceNotFoundException("Doctor not exist with id> " + id);
    }

    @Override
    public Doctor createDoctor(Doctor doctor) {

        Optional<Doctor> result = doctorRepository.findByDoctorReg(doctor.getDoctorReg());
        
        if (result.isPresent()) {
            throw new ResourceAlreadyExistException(
                "Doctor already exist with doctor registry no. " + doctor.getDoctorReg()
            );
        }
        return doctorRepository.save(doctor);
    }

    @Override
    public Doctor updateDoctor(long id, Doctor doctorRequest) {

        Speciality speciality = specialityRepository.findByName(doctorRequest.getSpeciality().getName())
            .orElseThrow(() -> 
                new ResourceNotFoundException("Speciality not exist with name: " + doctorRequest.getSpeciality().getName()
            ));

        Doctor savedDoctor = doctorRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Doctor not exist with id: " + id));
        
            savedDoctor.setFirstName(doctorRequest.getFirstName());
            savedDoctor.setDoctorReg(doctorRequest.getDoctorReg());
            savedDoctor.setSpeciality(speciality);

            return doctorRepository.save(savedDoctor);
    }

    @Override
    public void deleteDoctor(long id) {

        Doctor savedDoctor = doctorRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Doctor not exist with id: " + id));

        doctorRepository.delete(savedDoctor);
    }

}
