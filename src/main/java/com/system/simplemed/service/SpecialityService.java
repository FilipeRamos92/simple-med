package com.system.simplemed.service;

import java.util.List;

import com.system.simplemed.model.Speciality;


public interface SpecialityService {
    
    public List<Speciality> getAllSpecialities();

    public Speciality getSpecialityById(long id);

    public Speciality createSpeciality(Speciality speciality);

    public Speciality updateSpeciality(long id, Speciality speciality);

    public void deleteSpeciality(long id);
}
