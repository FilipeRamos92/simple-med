package com.system.simplemed.service;

import java.util.List;

import com.system.simplemed.model.Doctor;

public interface DoctorService {
    
    public List<Doctor> getAllDoctors();

    public Doctor getDoctorById(long id);

    public Doctor createDoctor(Doctor doctor);

    public Doctor updateDoctor(long id, Doctor doctor);

    public void deleteDoctor(long id);
}
