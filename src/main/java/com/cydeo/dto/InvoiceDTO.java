package com.cydeo.dto;

import com.cydeo.enums.InvoiceStatus;
import com.cydeo.enums.InvoiceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceDTO {
    private Long id;

    private String invoiceNo;
    private InvoiceStatus invoiceStatus;
    private InvoiceType invoiceType;

    @DateTimeFormat(pattern = "MMMM dd, yyyy")
    private LocalDate date;

    private CompanyDTO company;

    @NotNull(message = "This is a required field.")
    @Valid
    private ClientVendorDTO clientVendor;

    private BigDecimal price;
    private BigDecimal tax;
    private BigDecimal total;
    private List<InvoiceProductDTO> invoiceProducts;
}
