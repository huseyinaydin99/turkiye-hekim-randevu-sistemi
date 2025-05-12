package tr.com.huseyinaydin.services.impls;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tr.com.huseyinaydin.dtos.appointments.AppointmentSearchForm;
import tr.com.huseyinaydin.entities.AppUser;
import tr.com.huseyinaydin.entities.Appointment;
import tr.com.huseyinaydin.entities.Doctor;
import tr.com.huseyinaydin.repositories.AppointmentRepository;
import tr.com.huseyinaydin.services.AppointmentService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;

    // Constructor injection
    @Autowired
    public AppointmentServiceImpl(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public List<Appointment> searchAppointments(AppointmentSearchForm searchForm) {
        return appointmentRepository.findAppointmentsByCriteria(searchForm);
    }

    @Override
    public Appointment saveAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    @Override
    public boolean isAppointmentAlreadyTakenToday(AppUser user, Doctor doctor, LocalDate date) {
        return appointmentRepository.existsByUserAndDoctorAndDate(user, doctor, date);
    }

    public Page<Appointment> getUserAppointments(AppUser user, int page, int size, String sortField, String sortDir) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equalsIgnoreCase("desc") ? sort.descending() : sort.ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return appointmentRepository.findByUserId(user.getId(), pageable);
    }

    public Page<Appointment> getUpcomingUserAppointments(AppUser user, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("appointmentDateTime").ascending());
        return appointmentRepository.findByUserAndAppointmentDateTimeAfter(
                user,
                LocalDateTime.now(),
                pageable);
    }

    public Page<Appointment> getPastUserAppointments(AppUser user, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("appointmentDateTime").descending());
        return appointmentRepository.findByUserAndAppointmentDateTimeBefore(
                user,
                LocalDateTime.now(),
                pageable);
    }

    public Page<Appointment> findByUserAndDoctorName(AppUser user, String doctorName, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("appointmentDateTime").ascending());
        return appointmentRepository.findByUserAndDoctorFullNameContainingIgnoreCase(
                user,
                doctorName,
                pageable);
    }

    public Page<Appointment> findByUserAndDateBetween(
            AppUser user,
            LocalDateTime startDate,
            LocalDateTime endDate,
            int page,
            int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("appointmentDateTime").ascending());
        return appointmentRepository.findByUserAndAppointmentDateTimeBetween(
                user,
                startDate,
                endDate,
                pageable);
    }

    public Page<Appointment> findByUserAndAttendedStatus(
            AppUser user,
            boolean attended,
            int page,
            int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("appointmentDateTime").ascending());
        return appointmentRepository.findByUserAndAttended(user, attended, pageable);
    }

    public Page<Appointment> findByUserAndHospitalAndClinic(
            AppUser user,
            Long hospitalId,
            Long clinicId,
            int page,
            int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("appointmentDateTime").ascending());
        return appointmentRepository.findByUserAndHospitalIdAndClinicId(
                user,
                hospitalId,
                clinicId,
                pageable);
    }

    public Appointment findById(Long id){
        return appointmentRepository.findById(id).get();
    }

    @Transactional
    public void toggleAppointmentStatus(Long appointmentId, String username) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new EntityNotFoundException("Randevu bulunamadı"));

        if (!appointment.getUser().getEmail().equals(username)) {
            throw new SecurityException("Bu işlem için yetkiniz yok");
        }

        if (appointment.getAppointmentDateTime().isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Geçmiş randevuların durumu değiştirilemez");
        }

        appointment.setStatu(!appointment.isStatu());
        appointmentRepository.save(appointment);
    }
}