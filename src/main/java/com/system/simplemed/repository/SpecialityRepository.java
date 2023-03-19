package com.system.simplemed.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.system.simplemed.model.Speciality;

@Repository
public interface SpecialityRepository extends JpaRepository<Speciality, Long>{
    
}
