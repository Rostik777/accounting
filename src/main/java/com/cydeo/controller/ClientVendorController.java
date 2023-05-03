package com.cydeo.controller;

import com.cydeo.dto.ClientVendorDTO;
import com.cydeo.enums.ClientVendorType;
import com.cydeo.service.AddressService;
import com.cydeo.service.ClientVendorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;

@Controller
@RequestMapping("/clientVendors")
public class ClientVendorController {
    private final ClientVendorService clientVendorService;
    private final AddressService addressService;

    public ClientVendorController(ClientVendorService clientVendorService, AddressService addressService) {
        this.clientVendorService = clientVendorService;
        this.addressService = addressService;
    }

    @GetMapping("/list")
    public String navigateToClientVendorList(Model model) {
        model.addAttribute("clientVendors", clientVendorService.getAllClientVendors());
        return "/clientVendor/clientVendor-list";
    }

    @GetMapping("/create")
    public String navigateToClientVendorCreate(Model model) {
        model.addAttribute("newClientVendor", new ClientVendorDTO());
        return "/clientVendor/clientVendor-create";
    }

    @PostMapping("/create")
    public String createNewClientVendor(@Valid @ModelAttribute("newClientVendor") ClientVendorDTO clientVendorDTO, BindingResult bindingResult) {
        boolean isDuplicatedCompanyName = clientVendorService.companyNameExists(clientVendorDTO);

        if (bindingResult.hasErrors() || isDuplicatedCompanyName) {
            if (isDuplicatedCompanyName) {
                bindingResult.rejectValue("clientVendorName", " ", "A client/vendor with this name already exists. Please try with different name.");
            }
            return "/clientVendor/clientVendor-create";
        }

        clientVendorService.create(clientVendorDTO);
        return "redirect:/clientVendors/list";
    }

    @GetMapping("/update/{clientVendorId}")
    public String navigateToClientVendorUpdate(@PathVariable("clientVendorId") Long clientVendorId, Model model) {
        model.addAttribute("clientVendor", clientVendorService.findClientVendorById(clientVendorId));
        return "/clientVendor/clientVendor-update";
    }

    @PostMapping("/update/{clientVendorId}")
    public String updateClientVendor(@PathVariable("clientVendorId") Long clientVendorId, @Valid @ModelAttribute("clientVendor") ClientVendorDTO clientVendorDTO, BindingResult bindingResult) throws ClassNotFoundException, CloneNotSupportedException {
        clientVendorDTO.setId(clientVendorId);
        boolean isDuplicatedCompanyName = clientVendorService.companyNameExists(clientVendorDTO);

        if (bindingResult.hasErrors() || isDuplicatedCompanyName) {
            if(isDuplicatedCompanyName) {
                bindingResult.rejectValue("clientVendorName", " ", "A client/vendor with this name already exists. Please try with different name.");
            }
            return "/clientVendor/clientVendor-update";
        }
        clientVendorService.update(clientVendorId, clientVendorDTO);
        return "redirect:/clientVendors/list";
    }

    @ModelAttribute
    public void commonAttributes(Model model) {
        model.addAttribute("countries", addressService.getCountryList());
        model.addAttribute("clientVendorTypes", Arrays.asList(ClientVendorType.values()));
    }
}
