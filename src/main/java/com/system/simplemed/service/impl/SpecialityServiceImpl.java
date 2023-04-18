package com.system.simplemed.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.system.simplemed.exception.ResourceAlreadyExistException;
import com.system.simplemed.exception.ResourceNotFoundException;
import com.system.simplemed.model.Speciality;
import com.system.simplemed.repository.SpecialityRepository;
import com.system.simplemed.service.SpecialityService;

@Service
public class SpecialityServiceImpl implements SpecialityService {
    
    private final SpecialityRepository specialityRepository;

    public SpecialityServiceImpl(SpecialityRepository specialityRepository) {
        this.specialityRepository = specialityRepository;
    }

    @Override
    public List<Speciality> getAllSpecialities() {
        return specialityRepository.findAll();
    }

    @Override
    public Speciality getSpecialityById(long id) {
        Optional<Speciality> result = specialityRepository.findById(id);
        if (result.isPresent()) {
            return result.get();
        } 
        throw new ResourceNotFoundException("Speciality not exist with id: " + id);
    }

    @Override
    public Speciality createSpeciality(Speciality speciality) {
        
        Optional<Speciality> savedSpeciality = specialityRepository.findByName(speciality.getName());
        if (savedSpeciality.isPresent()) {
            throw new ResourceAlreadyExistException("Speciality already exist with name: " + speciality.getName());
        } 
        return specialityRepository.save(speciality);
    }

    @Override
    public Speciality updateSpeciality(long id, Speciality specialityRequest) {

        Speciality speciality = specialityRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Speciality not exist with id: " + specialityRequest.getId()));

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
