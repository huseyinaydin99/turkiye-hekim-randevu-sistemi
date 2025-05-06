package tr.com.huseyinaydin.services.impls;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tr.com.huseyinaydin.dtos.appointments.AppointmentSearchCriteria;
import tr.com.huseyinaydin.entities.Appointment;
import tr.com.huseyinaydin.entities.Doctor;
import tr.com.huseyinaydin.repositories.AppointmentRepository;
import tr.com.huseyinaydin.repositories.DoctorRepository;
import tr.com.huseyinaydin.services.AppointmentSearchService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentSearchServiceImpl implements AppointmentSearchService {
    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Appointment> findAvailableAppointments(AppointmentSearchCriteria criteria) {
        validateSearchCriteria(criteria);

        Doctor doctor = doctorRepository.findById(criteria.getDoctorId())
                .orElseThrow(() -> new EntityNotFoundException("Doktor bulunamadı Id: " + criteria.getDoctorId()));

        if (criteria.hasDateRange()) {
            return appointmentRepository.findByDoctorAndAppointmentDateTimeBetween(
                    doctor, criteria.getStartDate(), criteria.getEndDate());
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
}