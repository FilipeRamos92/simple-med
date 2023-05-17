package com.system.simplemed.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.system.simplemed.model.Doctor;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long>{

    Optional<Doctor> findByDoctorReg(String doctorReg);

    // @Query("from Speciality s inner join fetch s.occupation where s.speciality_id = :id")
    // Doctor findBySpecialityId(@Param("id") int id);

    // @Query("from Review r inner join fetch r.comments where r.reviewId = :id")
    // User findByReviewId(@Param("id") int id);

}
