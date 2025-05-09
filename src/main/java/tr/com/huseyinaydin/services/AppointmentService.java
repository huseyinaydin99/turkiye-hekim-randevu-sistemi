package tr.com.huseyinaydin.services;

import tr.com.huseyinaydin.dtos.appointments.AppointmentSearchForm;
import tr.com.huseyinaydin.entities.AppUser;
import tr.com.huseyinaydin.entities.Appointment;
import tr.com.huseyinaydin.entities.Doctor;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentService {
    List<Appointment> searchAppointments(AppointmentSearchForm searchForm);
    Appointment saveAppointment(Appointment appointment);
    boolean isAppointmentAlreadyTakenToday(AppUser user, Doctor doctor, LocalDate date);
}