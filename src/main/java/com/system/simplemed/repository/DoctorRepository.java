package com.system.simplemed.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.system.simplemed.model.Doctor;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long>{

    Optional<Doctor> findByDoctorReg(String doctorReg);
}
