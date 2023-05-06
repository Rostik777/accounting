package com.cydeo.entity;

import com.cydeo.enums.CompanyStatus;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Table(name = "companies")
@Where(clause = "is_deleted=false")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Company extends BaseEntity {
    private String title;
    private String phone;
    private String website;

    @Enumerated(EnumType.STRING)
    private CompanyStatus companyStatus;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinColumn(name = "address_id")
    private Address address;
}
