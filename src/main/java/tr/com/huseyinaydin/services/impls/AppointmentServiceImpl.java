package tr.com.huseyinaydin.services.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tr.com.huseyinaydin.dtos.appointments.AppointmentSearchForm;
import tr.com.huseyinaydin.entities.AppUser;
import tr.com.huseyinaydin.entities.Appointment;
import tr.com.huseyinaydin.entities.Doctor;
import tr.com.huseyinaydin.repositories.AppointmentRepository;
import tr.com.huseyinaydin.services.AppointmentService;

import java.time.LocalDate;
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
}