package tr.com.huseyinaydin.services;

import tr.com.huseyinaydin.dtos.AvailableAppointmentDTO;
import tr.com.huseyinaydin.dtos.appointments.*;
import tr.com.huseyinaydin.entities.Appointment;
import tr.com.huseyinaydin.entities.AvailableAppointment;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentSearchService {
    List<AvailableAppointment> findAvailableAppointments(AppointmentSearchCriteria criteria);
    List<AvailableAppointmentDTO> findAvailableAppointments(Long cityId, Long districtId,
                                                            Long hospitalId, Long clinicId, Long doctorId, LocalDateTime startDate);
    Appointment createAppointmentOptimistic(AppointmentRequest request);
    List<Appointment> searchAppointments(AppointmentSearchForm searchForm);
    Appointment createAppointment(AppointmentRequest request);
    //List<TimeSlot> getAvailableTimeSlots(Long doctorId, LocalDate date);
    List<AppointmentTimeSlot> getAvailableTimeSlots(Long doctorId, LocalDate date);
    List<AvailableAppointmentDTO> findAvailableAppointments(Long cityId, Long districtId,
                                                            Long hospitalId, Long clinicId, LocalDateTime startDate);
}