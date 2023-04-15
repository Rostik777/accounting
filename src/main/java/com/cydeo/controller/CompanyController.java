package com.cydeo.controller;

import com.cydeo.dto.CompanyDTO;
import com.cydeo.service.CompanyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

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
    public String createNewCompany(@Valid @ModelAttribute("newCompany") CompanyDTO companyDTO, BindingResult bindingResult) {
        if (companyService.isTitleExist(companyDTO.getTitle())) {
            bindingResult.rejectValue("title", " ", "This title already exists.");
        }

        if (bindingResult.hasErrors()) {
            return "/company/company-create";
        }

        companyService.create(companyDTO);
        return "redirect:/companies/list";
    }
}
