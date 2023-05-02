package com.system.simplemed.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.system.simplemed.exception.ResourceAlreadyExistException;
import com.system.simplemed.exception.ResourceNotFoundException;
import com.system.simplemed.model.Speciality;
import com.system.simplemed.repository.SpecialityRepository;
import com.system.simplemed.service.SpecialityService;

@Service
public class SpecialityServiceImpl implements SpecialityService {
    
    @Autowired
    private SpecialityRepository specialityRepository;

    @Override
    public List<Speciality> getAllSpecialities() {
        return specialityRepository.findAll();
    }

    @Override
    public Speciality getSpecialityById(long id) {
        
        Speciality speciality = specialityRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Speciality not exist with id: " + id));
        
        return speciality;
    }

    @Override
    public Speciality getSpecialityByName(String name) {

        Speciality speciality = specialityRepository.findByName(name)
            .orElseThrow(() -> new ResourceNotFoundException("Speciality not exist with name: " + name));

        return speciality;
    }

    @Override
    public Speciality createSpeciality(Speciality specialityRequest) {
        
        Optional<Speciality> savedSpeciality = specialityRepository.findByName(specialityRequest.getName());
        if (savedSpeciality.isPresent()) {
            throw new ResourceAlreadyExistException("Speciality already exist with name: " + specialityRequest.getName());
        } 
        return specialityRepository.save(specialityRequest);
    }

    @Override
    public Speciality updateSpeciality(long id, Speciality specialityRequest) {

        Speciality speciality = getSpecialityById(id);
        speciality.setName(specialityRequest.getName());

        return specialityRepository.save(speciality);
    }

    @Override
    public void deleteSpeciality(long id) {
        Speciality speciality = specialityRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Speciality not exist with id: " + id));

        specialityRepository.delete(speciality);
    }
}
