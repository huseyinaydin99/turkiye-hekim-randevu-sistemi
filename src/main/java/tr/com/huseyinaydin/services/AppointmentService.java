package tr.com.huseyinaydin.services;

import org.springframework.data.domain.Page;
import tr.com.huseyinaydin.dtos.appointments.AppointmentSearchForm;
import tr.com.huseyinaydin.entities.AppUser;
import tr.com.huseyinaydin.entities.Appointment;
import tr.com.huseyinaydin.entities.Doctor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentService {
    List<Appointment> searchAppointments(AppointmentSearchForm searchForm);

    Appointment saveAppointment(Appointment appointment);

    boolean isAppointmentAlreadyTakenToday(AppUser user, Doctor doctor, LocalDate date);

    Page<Appointment> getUserAppointments(AppUser user, int page, int size, String sortField, String sortDir);

    Page<Appointment> getUpcomingUserAppointments(AppUser user, int page, int size);

    Page<Appointment> getPastUserAppointments(AppUser user, int page, int size);

    Page<Appointment> findByUserAndDoctorName(AppUser user, String doctorName, int page, int size);

    Page<Appointment> findByUserAndDateBetween(
            AppUser user,
            LocalDateTime startDate,
            LocalDateTime endDate,
            int page,
            int size);

    Page<Appointment> findByUserAndAttendedStatus(
            AppUser user,
            boolean attended,
            int page,
            int size);

    Page<Appointment> findByUserAndHospitalAndClinic(
            AppUser user,
            Long hospitalId,
            Long clinicId,
            int page,
            int size);
}