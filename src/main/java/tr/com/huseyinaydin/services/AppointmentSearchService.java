package tr.com.huseyinaydin.services;

import tr.com.huseyinaydin.dtos.appointments.AppointmentSearchCriteria;
import tr.com.huseyinaydin.entities.Appointment;

import java.util.List;

public interface AppointmentSearchService {
    List<Appointment> findAvailableAppointments(AppointmentSearchCriteria criteria);
}