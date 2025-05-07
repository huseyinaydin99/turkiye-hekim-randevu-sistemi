package tr.com.huseyinaydin.services.impls;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tr.com.huseyinaydin.dtos.appointments.*;
import tr.com.huseyinaydin.entities.Appointment;
import tr.com.huseyinaydin.entities.Clinic;
import tr.com.huseyinaydin.entities.Doctor;
import tr.com.huseyinaydin.entities.Hospital;
import tr.com.huseyinaydin.exceptions.AppointmentNotAvailableException;
import tr.com.huseyinaydin.exceptions.ConflictException;
import tr.com.huseyinaydin.exceptions.ResourceNotFoundException;
import tr.com.huseyinaydin.repositories.*;
import tr.com.huseyinaydin.services.AppUserService;
import tr.com.huseyinaydin.services.AppointmentSearchService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class AppointmentSearchServiceImpl implements AppointmentSearchService {
    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final ClinicRepository clinicRepository;
    private final HospitalRepository hospitalRepository;
    private final AppUserRepository appUserRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Appointment> findAvailableAppointments(AppointmentSearchCriteria criteria) {
        validateSearchCriteria(criteria);

        Doctor doctor = doctorRepository.findById(criteria.getDoctorId())
                .orElseThrow(() -> new EntityNotFoundException("Doktor bulunamadı Id: " + criteria.getDoctorId()));

        if (criteria.hasDateRange()) {
            return appointmentRepository.findByDoctorIdAndAppointmentDateTimeBetween(
                    criteria.getDoctorId(), criteria.getStartDate(), criteria.getEndDate());
        }
        return appointmentRepository.findByDoctor(doctor);
    }

    private void validateSearchCriteria(AppointmentSearchCriteria criteria) {
        if (criteria == null) {
            throw new IllegalArgumentException("Arama kriterleri boş olamaz!");
        }
        if (criteria.getDoctorId() == null) {
            throw new IllegalArgumentException("Doktor Id boş olamaz!");
        }
    }

    @Override
    public List<Appointment> searchAppointments(AppointmentSearchForm searchForm) {
        return appointmentRepository.findAppointmentsByCriteria(searchForm);
    }

    @Override
    @Transactional
    public Appointment createAppointment(AppointmentRequest request) {
        // Doktorun randevu saatlerini kontrol et
        if (isAppointmentSlotTaken(request.getDoctorId(), request.getAppointmentDateTime())) {
            throw new AppointmentNotAvailableException("Bu randevu saati daha önce alınmış");
        }

        // Pessimistic locking ile doktor kaydını kilitle
        Doctor doctor = doctorRepository.findByIdWithPessimisticLock(request.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException("Doktor bulunamadı: " + request.getDoctorId()));

        Clinic clinic = clinicRepository.findById(request.getClinicId())
                .orElseThrow(() -> new ResourceNotFoundException("Klinik bulunamadı: " + request.getClinicId()));

        Hospital hospital = hospitalRepository.findById(request.getHospitalId())
                .orElseThrow(() -> new ResourceNotFoundException("Hastane bulunamadı: " + request.getHospitalId()));

        // Randevu oluştur
        Appointment appointment = Appointment.builder()
                .appointmentDateTime(request.getAppointmentDateTime())
                .attended(false)
                .noteToDoctor(request.getNoteToDoctor())
                .doctor(doctor)
                .clinic(clinic)
                .hospital(hospital)
                .district(clinic.getDistrict())
                .city(clinic.getCity())
                .user(appUserRepository.findById(request.getUserId()).get())
                .build();

        try {
            return appointmentRepository.save(appointment);
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("Randevu oluşturulurken veritabanı çakışması yaşandı");
        }
    }

    @Override
    @Transactional
    public Appointment createAppointmentOptimistic(AppointmentRequest request) {
        // Optimistic locking versiyonu
        if (isDoctorAvailable(request.getDoctorId(), request.getAppointmentDateTime())) {
            throw new ConflictException("Doktor bu saatte başka bir randevuya sahip");
        }

        Doctor doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException("Doktor bulunamadı"));

        // Optimistic lock kontrolü için version alanını kontrol et
        if (doctor.getVersion() != request.getDoctorVersion()) {
            throw new OptimisticLockingFailureException("Doktor bilgisi başka bir işlem tarafından değiştirildi");
        }

        Clinic clinic = clinicRepository.findById(request.getClinicId())
                .orElseThrow(() -> new ResourceNotFoundException("Klinik bulunamadı"));

        Hospital hospital = hospitalRepository.findById(request.getHospitalId())
                .orElseThrow(() -> new ResourceNotFoundException("Hastane bulunamadı"));

        Appointment appointment = Appointment.builder()
                .appointmentDateTime(request.getAppointmentDateTime())
                .attended(false)
                .noteToDoctor(request.getNoteToDoctor())
                .doctor(doctor)
                .clinic(clinic)
                .hospital(hospital)
                .district(clinic.getDistrict())
                .city(clinic.getCity())
                .user(appUserRepository.findById(request.getUserId()).get())
                .build();

        return appointmentRepository.save(appointment);
    }

    private boolean isDoctorAvailable(Long doctorId, LocalDateTime dateTime) {
        // Doktorun belirtilen saatte randevusu var mı kontrol et
        return appointmentRepository.existsByDoctorIdAndAppointmentDateTimeBetween(doctorId, dateTime, LocalDateTime.of(2025,06,07, 14, 07, 48));
    }

    /*@Override
    public List<TimeSlot> getAvailableTimeSlots(Long doctorId, LocalDate date) {
        // Doktorun çalışma saatlerini al (bu örnekte sabit 09:00-17:00 varsayıyoruz)
        LocalTime startTime = LocalTime.of(9, 0);
        LocalTime endTime = LocalTime.of(17, 0);

        // 15 dakikalık slotlar oluştur
        List<TimeSlot> allSlots = generateTimeSlots(startTime, endTime, 15);

        // Doktorun o günkü randevularını al
        LocalDateTime startDateTime = date.atStartOfDay();
        LocalDateTime endDateTime = date.plusDays(1).atStartOfDay();
        List<Appointment> appointments = appointmentRepository
                .findByDoctorAndAppointmentDateTimeBetween(
                        new Doctor(doctorId),
                        startDateTime,
                        endDateTime);

        // Dolu saatleri işaretle
        Set<LocalTime> bookedTimes = appointments.stream()
                .map(a -> a.getAppointmentDateTime().toLocalTime())
                .collect(Collectors.toSet());

        // Uygun slotları filtrele
        return allSlots.stream()
                .filter(slot -> !bookedTimes.contains(slot.getStartTime()))
                .collect(Collectors.toList());
    }*/

    @Override
    public List<AppointmentTimeSlot> getAvailableTimeSlots(Long doctorId, LocalDate date) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException("Doktor bulunamadı: " + doctorId));

        // Temel zaman slotlarını oluştur
        List<TimeSlot> basicSlots = generateBasicSlots(doctorId, date);

        return basicSlots.stream()
                .map(slot -> AppointmentTimeSlot.builder()
                        .startTime(slot.getStartTime())
                        .endTime(slot.getEndTime())
                        .doctorId(doctor.getId())
                        .doctorName(doctor.getFullName())
                        //.specialty(doctor.getSpecialty())
                        .clinicId(doctor.getClinic().getId())
                        .hospitalId(doctor.getHospital().getId())
                        .districtId(doctor.getClinic().getDistrict().getId())
                        .cityId(doctor.getClinic().getCity().getId())
                        .build())
                .collect(Collectors.toList());
    }

    private List<TimeSlot> generateBasicSlots(Long doctorId, LocalDate date) {
        LocalTime startTime = LocalTime.of(9, 0);
        LocalTime endTime = LocalTime.of(17, 0);

        List<TimeSlot> allSlots = IntStream.range(0, (int) ChronoUnit.MINUTES.between(startTime, endTime) / 15)
                .mapToObj(i -> new TimeSlot(
                        startTime.plusMinutes(i * 15L),
                        startTime.plusMinutes((i + 1) * 15L)))
                .collect(Collectors.toList());

        /*Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new EntityNotFoundException("Doktor bulunamadı!"));*/

        Set<LocalTime> bookedTimes = appointmentRepository
                .findByDoctorIdAndAppointmentDateTimeBetween(
                        doctorId,
                        date.atStartOfDay(),
                        date.plusDays(1).atStartOfDay())
                .stream()
                .map(a -> a.getAppointmentDateTime().toLocalTime())
                .collect(Collectors.toSet());

        return allSlots.stream()
                .filter(slot -> !bookedTimes.contains(slot.getStartTime()))
                .collect(Collectors.toList());
    }

    private List<TimeSlot> generateTimeSlots(LocalTime start, LocalTime end, int durationMinutes) {
        List<TimeSlot> slots = new ArrayList<>();
        LocalTime current = start;

        while (current.plusMinutes(durationMinutes).isBefore(end) ||
                current.plusMinutes(durationMinutes).equals(end)) {
            slots.add(new TimeSlot(current, current.plusMinutes(durationMinutes)));
            current = current.plusMinutes(durationMinutes);
        }

        return slots;
    }

    private boolean isAppointmentSlotTaken(Long doctorId, LocalDateTime dateTime) {
        // Doktorun belirtilen saatte randevusu var mı kontrol et
        boolean exists = appointmentRepository.existsByDoctorIdAndAppointmentDateTimeBetween(doctorId, dateTime, LocalDateTime.of(2025,06,07, 14, 07, 48));

        // Aynı saatte randevu varsa veya ±15 dakika içinde randevu varsa true döner
        if (exists) return true;

        // 15 dakika öncesi ve sonrasını kontrol et
        LocalDateTime startRange = dateTime.minusMinutes(14);
        LocalDateTime endRange = dateTime.plusMinutes(14);

        return appointmentRepository.existsByDoctorIdAndAppointmentDateTimeBetween(
                doctorId, startRange, endRange);
    }
}