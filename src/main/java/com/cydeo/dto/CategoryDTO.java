package com.cydeo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
    private Long id;

    @NotBlank(message = "Description is a required field.")
    @Size(max = 100, min = 2, message = "Description should have 2-100 characters long.")
    private String description;

    private CompanyDTO company;
    private boolean hasProduct;
}
