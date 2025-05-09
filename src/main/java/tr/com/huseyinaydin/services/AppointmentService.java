package tr.com.huseyinaydin.services;

import tr.com.huseyinaydin.dtos.appointments.AppointmentSearchForm;
import tr.com.huseyinaydin.entities.Appointment;

import java.util.List;

public interface AppointmentService {
    List<Appointment> searchAppointments(AppointmentSearchForm searchForm);
    Appointment saveAppointment(Appointment appointment);
}