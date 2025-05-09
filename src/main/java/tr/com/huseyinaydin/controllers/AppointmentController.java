package tr.com.huseyinaydin.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tr.com.huseyinaydin.dtos.AvailableAppointmentDTO;
import tr.com.huseyinaydin.entities.*;
import tr.com.huseyinaydin.security.AppUserDetails;
import tr.com.huseyinaydin.services.*;

import java.time.LocalDateTime;

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

    //@PreAuthorize("hasRole('USER')")
    //@PreAuthorize("hasAuthority('ROLE_USER')")
    @PostMapping("/confirm")
    @ResponseBody
    public ResponseEntity<String> confirmAppointment(@RequestBody AvailableAppointmentDTO dto,
                                                     @AuthenticationPrincipal AppUserDetails currentUser) {
        City city = cityService.findById(dto.getCityId()).get();
        District district = districtService.findById(dto.getDistrictId()).get();
        Hospital hospital = hospitalService.findById(dto.getHospitalId()).get();
        Clinic clinic = clinicService.findById(dto.getClinicId()).get();
        Doctor doctor = doctorService.findByDoctorId(dto.getDoctorId()).get();
        AppUser appUser = appUserService.findUserByEmail(currentUser.getUsername());

        Appointment appointment = new Appointment(LocalDateTime.now(), true, dto.getDoctorNote(), appUser, doctor, clinic, hospital, district, city);

        appointmentService.saveAppointment(appointment);

        return ResponseEntity.ok("Veri alındı.");
    }
}