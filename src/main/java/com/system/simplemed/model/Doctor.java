package com.system.simplemed.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name = "doctors")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "doctor_reg")
    private String doctorReg;

    @Column(name = "gender")
    private String gender;

    @Column(name = "local_service")
    private String localService;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "schedule")
    private List<Schedule> schedules;

    @OneToMany(mappedBy = "doctor")
    @JsonManagedReference(value = "appointment")
    private List<Appointment> appointments;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "speciality_id", nullable = false)
    private Speciality speciality;
}