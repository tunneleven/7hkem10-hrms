package com.oop7hkem10.hrm.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@Table(name = "ResCompanies")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class ResCompany {

    @Id
    @Column(nullable = false, updatable = false)
    @SequenceGenerator(
            name = "company_primary_sequence",
            sequenceName = "company_primary_sequence",
            allocationSize = 1,
            initialValue = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "company_primary_sequence"
    )
    private Long id;

    @Column
    private String name;

    @Column
    private String address;

    @Column
    private String phone;

    @Column
    private String email;

    @Column
    private String website;

    @Column
    private String taxCode;

    @OneToMany(mappedBy = "company")
    private Set<HrDepartment> deparmentIds;

    @OneToMany(mappedBy = "company")
    private Set<HrJobPosition> jobIds;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

}
