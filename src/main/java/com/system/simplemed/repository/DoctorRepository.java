package com.system.simplemed.repository;

import org.springframework.data.repository.CrudRepository;

import com.system.simplemed.model.Doctor;

public interface DoctorRepository extends CrudRepository<Doctor, Long>{
    
}
