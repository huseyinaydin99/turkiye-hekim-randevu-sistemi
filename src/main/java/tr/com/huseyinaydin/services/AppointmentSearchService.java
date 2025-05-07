package tr.com.huseyinaydin.services;

import tr.com.huseyinaydin.dtos.appointments.*;
import tr.com.huseyinaydin.entities.Appointment;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentSearchService {
    List<Appointment> findAvailableAppointments(AppointmentSearchCriteria criteria);
    Appointment createAppointmentOptimistic(AppointmentRequest request);
    List<Appointment> searchAppointments(AppointmentSearchForm searchForm);
    Appointment createAppointment(AppointmentRequest request);
    //List<TimeSlot> getAvailableTimeSlots(Long doctorId, LocalDate date);
    List<AppointmentTimeSlot> getAvailableTimeSlots(Long doctorId, LocalDate date);
}