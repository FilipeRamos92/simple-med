package com.system.simplemed.service;

import java.util.List;

import com.system.simplemed.model.Doctor;

public interface DoctorService {
    
    public List<Doctor> getAllDoctors();

    public Doctor getDoctorById(long id);

    public Doctor createDoctor(Doctor doctor);

    public Doctor updateDoctorInfo(long id, Doctor doctor);
    
    public Doctor updateDoctorSpeciality(long doctorId, long specialityId);
    
    public void deleteDoctor(long id);
}
