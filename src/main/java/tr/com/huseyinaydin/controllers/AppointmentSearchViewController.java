package tr.com.huseyinaydin.controllers;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import tr.com.huseyinaydin.dtos.AppointmentDto;
import tr.com.huseyinaydin.dtos.appointments.*;
import tr.com.huseyinaydin.dtos.login.LoginRequest;
import tr.com.huseyinaydin.entities.Appointment;
import tr.com.huseyinaydin.exceptions.ConflictException;
import tr.com.huseyinaydin.security.AppUserDetails;
import tr.com.huseyinaydin.services.*;
import tr.com.huseyinaydin.session.UserSession;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

@Controller
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentSearchViewController {
    private final LocationService locationService;
    private final AppointmentSearchService appointmentSearchService;  // Eksik olan ekleme
    private final AppointmentService appointmentService;
    private final DoctorService doctorService;
    private final AppUserService appUserService;
    //private final UserSession userSession;

    @GetMapping("/available-slots")
    public ResponseEntity<List<AppointmentTimeSlot>> getAvailableSlots(
            @RequestParam Long doctorId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        List<AppointmentTimeSlot> slots = appointmentSearchService.getAvailableTimeSlots(doctorId, date);
        return ResponseEntity.ok(slots);
    }

    @PostMapping("/book")
    public ResponseEntity<?> bookAppointment(
            @RequestBody AppointmentRequest request,
            @AuthenticationPrincipal AppUserDetails userDetails) {

        try {
            request.setUserId(appUserService.getUserByEmail(userDetails.getUsername()).getId());
            Appointment appointment = appointmentSearchService.createAppointment(request);
            return ResponseEntity.ok(appointment);
        } catch (ConflictException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (OptimisticLockingFailureException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Randevu alınırken bir çakışma oluştu. Lütfen tekrar deneyin.");
        }
    }

    @PostMapping("/book-optimistic")
    public ResponseEntity<?> bookAppointmentOptimistic(
            @RequestBody AppointmentRequest request,
            @AuthenticationPrincipal AppUserDetails userDetails) {

        try {
            request.setUserId(appUserService.getUserByEmail(userDetails.getUsername()).getId());
            Appointment appointment = appointmentSearchService.createAppointmentOptimistic(request);
            return ResponseEntity.ok(appointment);
        } catch (ConflictException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (OptimisticLockingFailureException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Randevu alınırken bir çakışma oluştu. Lütfen tekrar deneyin.");
        }
    }

    @GetMapping("/search")
    public ModelAndView showSearchForm(@AuthenticationPrincipal AppUserDetails userDetails) {
        ModelAndView modelAndView = new ModelAndView("login-success");

        if (userDetails != null) {
            modelAndView.addObject("searchForm", new AppointmentSearchForm()); // form nesnesini ekliyoruz
            modelAndView.addObject("nameSurname", userDetails.getUsername());
            return modelAndView;
        }
        ModelAndView errorView = new ModelAndView("login-error");
        errorView.addObject("loginRequest", new LoginRequest());
        errorView.addObject("errorMessage", "Email veya şifre hatalı!");
        return errorView;
    }

    //@PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("/search/results")
    public ModelAndView searchAppointments(@ModelAttribute AppointmentSearchForm searchForm,
                                           HttpServletResponse response) {
        //List<AppointmentDto> appointments = new ArrayList<>();
        //List<AppointmentDto> appointments = generateRandomAppointments(5);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm:ss", new Locale("tr", "TR"));
        List<AppointmentTimeSlot> slots = appointmentSearchService.getAvailableTimeSlots(Long.parseLong(searchForm.getDoctor()), LocalDate.of(2025,06,07));
        ModelAndView page = new ModelAndView("appointmentResult");
        page.addObject("slots", slots);
        page.addObject("formatter", formatter);
        //page.addObject("backgroundImage", "/images/backgrounds/medical-office-bg.jpg");

        if (slots.isEmpty()) {
            page.addObject("message", "Seçtiğiniz kriterlere uygun randevu bulunamadı.");
        }

        return page;
    }

    private static List<AppointmentDto> generateRandomAppointments(int count) {
        List<AppointmentDto> appointments = new ArrayList<>();
        Random random = new Random();
        String[] notes = {
                "Hastamın şeker seviyeleri yüksek",
                "Tansiyon kontrolü gerekiyor",
                "Alerji şikayetleri var",
                "Rutin kontrol",
                "Ağrı şikayeti",
                "Yeni ilaç reçetesi gerekiyor",
                "Ameliyat sonrası kontrol",
                null  // Bazı randevularda not olmayabilir
        };

        for (int i = 1; i <= count; i++) {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime randomDateTime = now.plusDays(random.nextInt(30))
                    .plusHours(random.nextInt(24))
                    .plusMinutes(random.nextInt(12) * 5)
                    .truncatedTo(ChronoUnit.MINUTES);

            AppointmentDto appointment = AppointmentDto.builder()
                    .id((long) i)
                    .appointmentDateTime(randomDateTime)
                    .attended(random.nextBoolean())
                    .noteToDoctor(notes[random.nextInt(notes.length)])
                    .build();

            appointments.add(appointment);
        }

        return appointments;
    }
}