package com.cydeo.controller;

import com.cydeo.dto.CompanyDTO;
import com.cydeo.service.CompanyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/companies")
public class CompanyController {
    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("/list")
    public String navigateToCompanyList(Model model) {
        model.addAttribute("companies", companyService.getAllCompanies());
        return "company/company-list";
    }

    @GetMapping("/create")
    public String navigateToCompanyCreate(Model model) {
        model.addAttribute("newCompany", new CompanyDTO());
        return "/company/company-create";
    }

    @PostMapping("/create")
    public String createNewCompany(@ModelAttribute("newCompany") CompanyDTO companyDTO, Model model) {
        companyService.create(companyDTO);
        return "redirect:/companies/list";
    }
}
