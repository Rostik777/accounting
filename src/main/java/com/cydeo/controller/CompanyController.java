package com.cydeo.controller;

import com.cydeo.dto.CompanyDTO;
import com.cydeo.service.CompanyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/update/{companyId}")
    public String navigateToCompanyUpdate(@PathVariable("companyId") Long companyId, Model model) {
        model.addAttribute("company", companyService.findCompanyById(companyId));
        return "/company/company-update";
    }

    @PostMapping("/update/{companyId}")
    public String updateCompany(@Valid @ModelAttribute("company") CompanyDTO companyDto, BindingResult bindingResult, @PathVariable Long companyId) {

        boolean isThisCompanyTitle = companyDto.getTitle().equals(companyService.findCompanyById(companyId).getTitle());
        if (companyService.isTitleExist(companyDto.getTitle()) && !isThisCompanyTitle) {
            bindingResult.rejectValue("title", " ", "This title already exists.");
        }

        if (bindingResult.hasErrors()) {
            companyDto.setId(companyId);
            return "/company/company-update";
        }

        companyService.update(companyId, companyDto);
        return "redirect:/companies/list";
    }
}
