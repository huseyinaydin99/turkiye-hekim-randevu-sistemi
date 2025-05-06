package tr.com.huseyinaydin.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tr.com.huseyinaydin.dtos.appointments.AppointmentSearchCriteria;
import tr.com.huseyinaydin.services.AppointmentSearchService;
import tr.com.huseyinaydin.services.LocationService;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentSearchViewController {
    private final LocationService locationService;
    private final AppointmentSearchService appointmentSearchService;  // Eksik olan ekleme

    @GetMapping("/search")
    public String showSearchForm(Model model) {
        model.addAttribute("cities", locationService.findAllCities());
        return "appointment-search";
    }

    @GetMapping("/search/results")
    public String showSearchResults(@RequestParam Long doctorId,
                                    @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                    Model model) {
        AppointmentSearchCriteria criteria = new AppointmentSearchCriteria();
        criteria.setDoctorId(doctorId);

        if (startDate != null) {
            criteria.setStartDate(startDate);
            criteria.setEndDate(startDate.plusDays(7));
        }

        model.addAttribute("appointments", appointmentSearchService.findAvailableAppointments(criteria));
        return "appointment-results";
    }
}