package tr.com.huseyinaydin.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tr.com.huseyinaydin.dtos.appointments.AppointmentSearchForm;
import tr.com.huseyinaydin.entities.Appointment;
import tr.com.huseyinaydin.services.AppointmentService;
import tr.com.huseyinaydin.services.impls.AppointmentServiceImpl;

import java.util.List;

@Controller
@RequiredArgsConstructor
//@RequestMapping("/available")
public class AppointmentController {

    /*private final AppointmentService appointmentService;

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("/assignation/search")
    public String showSearchForm(Model model) {
        model.addAttribute("searchForm", new AppointmentSearchForm());
        return "search";
    }*/
}