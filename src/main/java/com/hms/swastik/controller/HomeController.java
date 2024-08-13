package com.hms.swastik.controller;

import com.hms.swastik.model.BillDetails;
import com.hms.swastik.model.Patient;
import com.hms.swastik.model.PatientBill;
import com.hms.swastik.service.BillService;
import com.hms.swastik.service.PatientService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class HomeController {

    private final PatientService patientService;
    private final BillService billService;
    public HomeController(PatientService patientService,BillService billService){
        this.patientService=patientService;
        this.billService=billService;
    }

    @GetMapping("/patientList")
    public String viewHomePage(Model model) {
        return findPaginated(1, "name", "asc", model);
    }

    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model) {
        int pageSize = 5;

        //Page<Patient> page = patientService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<Patient> listPatient = patientService.findPaginated(pageNo, pageSize, sortField, sortDir);

//        model.addAttribute("currentPage", pageNo);
//        model.addAttribute("totalPages", page.getTotalPages());
//        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listPatient", listPatient);
        return "patientList";
    }

    @GetMapping("/showRegistrationForm")
    public String showNewEmployeeForm(Model model) {
        // create model attribute to bind form data
        Patient patient = new Patient();
        model.addAttribute("patient", patient);
        return "register";
    }

    @GetMapping("/report")
    public String showReportForm(Model model) {
        // create model attribute to bind form data
        LocalDate toDate=LocalDate.now();
        LocalDate endDate=LocalDate.now();
        model.addAttribute("toDate", toDate);
        model.addAttribute("endDate", endDate);
        return "report";
    }

    @GetMapping(value = "/get-bill/{id}")
    public String getBill(@PathVariable("id") Long id, Model model) {
        PatientBill patientBill=billService.getBillDetails(id);
        Patient patient=patientService.getPatientById(patientBill.getPatientId());
        List<BillDetails> billDetails=billService.getBillDetailsList(id);

        model.addAttribute("formattedDate", patientBill.getCreatedAt());
        model.addAttribute("patient",patient);
        model.addAttribute("bill",patientBill);
        model.addAttribute("list",billDetails);

        return "bill";
    }

    @GetMapping(value = "/billList")
    public String getBill(Model model) {

        //Page<Patient> page = patientService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<PatientBill> patientBill = billService.getAllBills();

        model.addAttribute("patientBill", patientBill);
        return "billList";
    }

}
