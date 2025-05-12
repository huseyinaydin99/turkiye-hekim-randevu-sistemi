package tr.com.huseyinaydin.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tr.com.huseyinaydin.dtos.AvailableAppointmentDTO;
import tr.com.huseyinaydin.entities.*;
import tr.com.huseyinaydin.security.AppUserDetails;
import tr.com.huseyinaydin.services.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final CityService cityService;
    private final DistrictService districtService;
    private final HospitalService hospitalService;
    private final ClinicService clinicService;
    private final DoctorService doctorService;
    private final AppUserService appUserService;
    private final AppointmentService appointmentService;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    //@PreAuthorize("hasRole('USER')")
    //@PreAuthorize("hasAuthority('ROLE_USER')")
    @PostMapping("/confirm")
    @ResponseBody
    public ResponseEntity<String> confirmAppointment(@RequestBody AvailableAppointmentDTO dto,
                                                     @AuthenticationPrincipal AppUserDetails currentUser) {
        LocalDateTime now = LocalDateTime.now();
        LocalDate today = now.toLocalDate();

        Doctor doctor = doctorService.findByDoctorId(dto.getDoctorId()).get();
        AppUser appUser = appUserService.findUserByEmail(currentUser.getUsername());

        boolean alreadyTaken = appointmentService.isAppointmentAlreadyTakenToday(appUser, doctor, today);
        if (alreadyTaken) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Aynı gün içinde bu doktor için zaten randevu aldınız.");
        }

        City city = cityService.findById(dto.getCityId()).get();
        District district = districtService.findById(dto.getDistrictId()).get();
        Hospital hospital = hospitalService.findById(dto.getHospitalId()).get();
        Clinic clinic = clinicService.findById(dto.getClinicId()).get();


        Appointment appointment = new Appointment(LocalDateTime.now(), true, dto.getDoctorNote(), appUser, doctor, clinic, hospital, district, city);

        appointmentService.saveAppointment(appointment);

        return ResponseEntity.ok("Veri alındı.");
    }

    /*@GetMapping("/my")
    public String getUserAppointments(@AuthenticationPrincipal AppUser currentUser,
                                      @RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size,
                                      Model model) {
        Page<Appointment> appointments = appointmentService.getUserAppointments(currentUser, page, size, "appointmentDateTime", "asc");
        model.addAttribute("appointments", appointments);
        return "appointmentList";
    }*/

    @GetMapping("/myAppointments")
    public String getUserAppointments(
            @AuthenticationPrincipal AppUserDetails currentUser,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "appointmentDateTime") String sortField,
            @RequestParam(defaultValue = "asc") String sortDir,
            Model model) {

        String email = currentUser.getUsername();
        AppUser user = appUserService.findUserByEmail(email);

        Page<Appointment> appointments = appointmentService.getUserAppointments(
                user, page, size, sortField, sortDir);

        model.addAttribute("appointments", appointments);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("pageSizes", List.of(5, 10, 20, 50));

        return "appointmentList";
    }

    @GetMapping("/upcoming")
    public String getUpcomingAppointments(
            @AuthenticationPrincipal AppUserDetails currentUser,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {

        String email = currentUser.getUsername();
        AppUser user = appUserService.findUserByEmail(email);

        Page<Appointment> appointments = appointmentService.getUpcomingUserAppointments(
                user, page, size);

        model.addAttribute("appointments", appointments);
        model.addAttribute("pageSizes", List.of(5, 10, 20, 50));
        return "upcomingAppointments";
    }

    @GetMapping("/past")
    public String getPastAppointments(
            @AuthenticationPrincipal AppUserDetails currentUser,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {

        String email = currentUser.getUsername();
        AppUser user = appUserService.findUserByEmail(email);

        Page<Appointment> appointments = appointmentService.getPastUserAppointments(
                user, page, size);

        model.addAttribute("appointments", appointments);
        model.addAttribute("pageSizes", List.of(5, 10, 20, 50));
        return "pastAppointments";
    }

    @GetMapping("/appointmentSearch")
    public String searchAppointments(
            @AuthenticationPrincipal AppUserDetails currentUser,
            @RequestParam(required = false) String doctorName,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {

        Page<Appointment> appointments;

        String email = currentUser.getUsername();
        AppUser user = appUserService.findUserByEmail(email);

        if (doctorName != null && !doctorName.isEmpty()) {
            // Doktor adına göre arama
            appointments = appointmentService.findByUserAndDoctorName(
                    user, doctorName, page, size);
        } else if (startDate != null && endDate != null) {
            // Tarih aralığına göre arama
            LocalDateTime start = LocalDateTime.parse(startDate, DATE_FORMATTER);
            LocalDateTime end = LocalDateTime.parse(endDate, DATE_FORMATTER);
            appointments = appointmentService.findByUserAndDateBetween(
                    user, start, end, page, size);
        } else {
            // Filtre yoksa tüm randevular
            appointments = appointmentService.getUserAppointments(
                    user, page, size, "appointmentDateTime", "asc");
        }

        model.addAttribute("appointments", appointments);
        model.addAttribute("doctorName", doctorName);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("pageSizes", List.of(5, 10, 20, 50));

        return "appointmentList";
    }

    @PatchMapping("/{id}/toggle-status")
    @ResponseBody
    public ResponseEntity<Map<String, String>> toggleAppointmentStatus(
            @PathVariable Long id,
            @AuthenticationPrincipal AppUserDetails currentUser
            /*,@RequestHeader("X-XSRF-TOKEN") String xsrfToken*/) {

        Map<String, String> response = new HashMap<>();
        try {
            appointmentService.toggleAppointmentStatus(id, currentUser.getUsername());
            response.put("status", "success");
            response.put("newStatus", appointmentService.findById(id).isStatu() ? "active" : "passive");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}