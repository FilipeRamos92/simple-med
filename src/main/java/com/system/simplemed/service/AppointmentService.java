package com.system.simplemed.service;

import java.util.List;

import com.system.simplemed.model.Appointment;

public interface AppointmentService {
    
    public List<Appointment> getAllAppointments();

    public Appointment getAppointmentById(long id);

    public Appointment createAppointment(Appointment appointment);

    public Appointment updateAppointment(long id, Appointment appointment);

    public void deleteAppointment(long id);
}
