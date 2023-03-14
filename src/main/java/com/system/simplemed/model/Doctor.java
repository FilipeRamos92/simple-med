package com.system.simplemed.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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

    @Column(name = "speciality")
    private String speciality;

    @OneToOne
    @JoinColumn(name = "schedule_id", referencedColumnName = "id")
    private Schedule schedule;

    public Doctor() {}
    
    public Doctor(String firstName, String lastName, String doctorReg, String gender, String localService, String speciality) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.doctorReg = doctorReg;
        this.gender = gender;
        this.localService = localService;
        this.speciality = speciality;
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDoctorReg() {
        return doctorReg;
    }

    public void setDoctorReg(String doctorReg) {
        this.doctorReg = doctorReg;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLocalService() {
        return localService;
    }

    public void setLocalService(String localService) {
        this.localService = localService;
    }

    public String getSpeciality() {
        return speciality;
    }
    
    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }
}
