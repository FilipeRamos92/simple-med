// package com.system.simplemed.model;

// import javax.persistence.Column;
// import javax.persistence.Entity;
// import javax.persistence.GeneratedValue;
// import javax.persistence.GenerationType;
// import javax.persistence.Id;
// import javax.persistence.JoinColumn;
// import javax.persistence.ManyToOne;
// import javax.persistence.Table;

// @Entity
// @Table(name = "appointments")
// public class Appointment {
    
//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private long id;

//     @Column(name = "payment")
//     private String payment;

//     @ManyToOne
//     @JoinColumn(name = "patient_id")
//     private Patient patient;

//     @ManyToOne
//     @JoinColumn(name = "doctor_id")
//     private Doctor doctor;

//     public Appointment() {}
    
//     public Appointment(String payment) {
//         this.payment = payment;
//     }

//     public long getId() {
//         return id;
//     }

//     public String getPayment() {
//         return payment;
//     }

//     public void setPayment(String payment) {
//         this.payment = payment;
//     }

//     public Doctor getDoctor() {
//         return doctor;
//     }

//     public void setDoctor(Doctor doctor) {
//         this.doctor = doctor;
//     }

//     public Patient getPatient() {
//         return patient;
//     }

//     public void setPatient(Patient patient) {
//         this.patient = patient;
//     }
// }
