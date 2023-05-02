package com.system.simplemed.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.system.simplemed.exception.ResourceAlreadyExistException;
import com.system.simplemed.exception.ResourceNotFoundException;
import com.system.simplemed.model.Doctor;
import com.system.simplemed.model.Speciality;
import com.system.simplemed.repository.DoctorRepository;
import com.system.simplemed.service.DoctorService;
import com.system.simplemed.service.SpecialityService;

@Service
public class DoctorServiceImpl implements DoctorService {
    
    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private SpecialityService specialityService;

    @Override
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    @Override
    public Doctor getDoctorById(long id) {
        
        Doctor doctor = doctorRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Doctor not exist with id: " + id));
        
        return doctor;
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
    public Doctor updateDoctorInfo(long id, Doctor doctorRequest) {

        Doctor doctor = getDoctorById(id);
        
        doctor.setFirstName(doctorRequest.getFirstName());
        doctor.setLastName(doctorRequest.getLastName());
        doctor.setGender(doctorRequest.getGender());
        doctor.setLocalService(doctorRequest.getLocalService());
        doctor.setDoctorReg(doctorRequest.getDoctorReg());

        return doctorRepository.save(doctor);
    }

    @Override
    public Doctor updateDoctorSpeciality(long doctorId, long specialityId) {

        Doctor doctor = getDoctorById(doctorId);
        Speciality speciality = specialityService.getSpecialityById(specialityId);

        doctor.setSpeciality(speciality);

        return doctorRepository.save(doctor);
    }

    @Override
    public void deleteDoctor(long id) {

        Doctor savedDoctor = getDoctorById(id);

        doctorRepository.delete(savedDoctor);
    }

}
