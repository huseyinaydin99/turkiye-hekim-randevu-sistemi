/*package tr.com.huseyinaydin.config;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import tr.com.huseyinaydin.entities.*;
import tr.com.huseyinaydin.repositories.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final CityRepository cityRepository;
    private final DistrictRepository districtRepository;
    private final HospitalRepository hospitalRepository;
    private final ClinicRepository clinicRepository;
    private final DoctorRepository doctorRepository;
    private final AppUserRepository appUserRepository;
    private final AppointmentRepository appointmentRepository;
    private final OAuthIdentityRepository oAuthIdentityRepository;

    @Override
    @Transactional
    public void run(String... args) {

        // 🌆 Şehir ve İlçeler
        City city = cityRepository.save(new City(null, "İstanbul", new ArrayList<>()));
        District district = districtRepository.save(new District(null, "Kadıköy", city, new ArrayList<>()));

        // 🏥 Hastane ve Klinik
        Hospital hospital = hospitalRepository.save(new Hospital(null, "Kadıköy Devlet Hastanesi", city, district, new ArrayList<>()));
        Clinic clinic = clinicRepository.save(new Clinic(null, "Dahiliye", hospital, new ArrayList<>()));

        // 👨‍⚕️ Doktor
        Doctor doctor = doctorRepository.save(new Doctor(null, "Dr. Ayşe Yılmaz", "Uzm. Dr.", clinic, hospital, new ArrayList<>()));

        // Değiştirilebilir koleksiyonlar kullanın
        List<Doctor> doctors = new ArrayList<>();
        doctors.add(doctor);
        clinic.setDoctors(doctors);

        List<Doctor> hospitalDoctors = new ArrayList<>();
        hospitalDoctors.add(doctor);
        hospital.setDoctors(hospitalDoctors);

        // 👤 Kullanıcı (Hasta)
        AppUser user = appUserRepository.save(AppUser.builder()
                .fullName("Hüseyin Aydın")
                .email("huseyin@example.com")
                .nationalId("12345678901")
                .password("sifre123") // E-devlet ile null olabilir
                .role("PATIENT")
                .appointments(new ArrayList<>()) // Boş ama değiştirilebilir liste
                .build());

        // 🔐 OAuth Kimliği
        OAuthIdentity identity = OAuthIdentity.builder()
                .provider("E-Devlet")
                .providerId("987654321")
                .user(user)
                .build();
        oAuthIdentityRepository.save(identity);

        // 📅 Randevu
        Appointment appointment = appointmentRepository.save(Appointment.builder()
                .appointmentDateTime(LocalDateTime.now().plusDays(3))
                .attended(false)
                .noteToDoctor("Baş ağrısı ve mide bulantısı var.")
                .user(user)
                .doctor(doctor)
                .build());

        // Değiştirilebilir koleksiyonlar kullanın
        List<Appointment> userAppointments = new ArrayList<>();
        userAppointments.add(appointment);
        user.setAppointments(userAppointments);

        List<Appointment> doctorAppointments = new ArrayList<>();
        doctorAppointments.add(appointment);
        doctor.setAppointments(doctorAppointments);

        // Güncellemeleri kaydet
        appUserRepository.save(user);
        doctorRepository.save(doctor);
    }
}*/