package com.system.simplemed.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.system.simplemed.model.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long>{
    
}
