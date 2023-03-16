package com.system.simplemed.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.system.simplemed.model.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long>{
    
}
