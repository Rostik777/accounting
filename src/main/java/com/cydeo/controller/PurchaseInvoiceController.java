package com.cydeo.controller;

import com.cydeo.dto.InvoiceDTO;
import com.cydeo.dto.InvoiceProductDTO;
import com.cydeo.enums.ClientVendorType;
import com.cydeo.enums.InvoiceType;
import com.cydeo.service.ClientVendorService;
import com.cydeo.service.InvoiceProductService;
import com.cydeo.service.InvoiceService;
import com.cydeo.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/purchaseInvoices")
public class PurchaseInvoiceController {
    private final InvoiceService invoiceService;
    private final ClientVendorService clientVendorService;
    private final InvoiceProductService invoiceProductService;
    private final ProductService productService;

    public PurchaseInvoiceController(InvoiceService invoiceService, ClientVendorService clientVendorService, InvoiceProductService invoiceProductService, ProductService productService) {
        this.invoiceService = invoiceService;
        this.clientVendorService = clientVendorService;
        this.invoiceProductService = invoiceProductService;
        this.productService = productService;
    }

    @GetMapping("/list")
    public String navigateToPurchaseInvoiceList(Model model) throws Exception {
        model.addAttribute("invoices", invoiceService.getAllInvoicesOfCompany(InvoiceType.PURCHASE));
        return "/invoice/purchase-invoice-list";
    }

    @GetMapping("/create")
    public String navigateToPurchaseInvoiceCreate(Model model) throws Exception {
        model.addAttribute("newPurchaseInvoice", invoiceService.getNewInvoice(InvoiceType.PURCHASE));
        return "/invoice/purchase-invoice-create";
    }

    @PostMapping("/create")
    public String createNewPurchaseInvoice(@Valid @ModelAttribute("newPurchaseInvoice") InvoiceDTO invoiceDto, BindingResult result) {

        if (result.hasErrors()) {
            return "/invoice/purchase-invoice-create";
        }

        var invoice = invoiceService.save(invoiceDto, InvoiceType.PURCHASE);
        return "redirect:/purchaseInvoices/update/" + invoice.getId();
    }

    @GetMapping("/update/{invoiceId}")
    public String navigateToPurchaseInvoiceUpdate(@PathVariable("invoiceId") Long invoiceId, Model model) {
        model.addAttribute("invoice", invoiceService.findInvoiceById(invoiceId));
        model.addAttribute("newInvoiceProduct", new InvoiceProductDTO());
        model.addAttribute("invoiceProducts", invoiceProductService.getInvoiceProductsOfInvoice(invoiceId));
        return "/invoice/purchase-invoice-update";
    }

    @PostMapping("/update/{invoiceId}")
    public String updatePurchaseInvoice(@PathVariable("invoiceId") Long invoiceId, InvoiceDTO invoiceDto) {
        invoiceService.update(invoiceId, invoiceDto);
        return "redirect:/purchaseInvoices/list";
    }

    @ModelAttribute
    public void commonAttributes(Model model) {
        model.addAttribute("vendors", clientVendorService.getAllClientVendorsOfCompany(ClientVendorType.VENDOR));
        model.addAttribute("products", productService.getAllProducts());
    }
}
