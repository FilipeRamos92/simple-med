package com.system.simplemed.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.system.simplemed.model.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    
}
