package com.oop7hkem10.hrm.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Map;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@Table(name = "HrContracts")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class HrContract {

    @Id
    @Column(nullable = false, updatable = false)
    @SequenceGenerator(
            name = "contract_primary_sequence",
            sequenceName = "contract_primary_sequence",
            allocationSize = 1,
            initialValue = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "contract_primary_sequence"
    )
    private Long id;

    @Column
    private String name;

    @Column
    private String code;

    @Column
    private LocalDate dateFrom;

    @Column
    private LocalDate dateTo;

    @Column
    private String salary;

    @Column(columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, String> status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private HrEmployee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_type_id", nullable = false)
    private HrContractType contractType;

    @OneToMany(mappedBy = "contract")
    private Set<HrContractAnnex> annexId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_position_id", nullable = false)
    private HrJobPosition jobPosition;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_title_id", nullable = false)
    private HrJobTitle jobTitle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = false)
    private HrDepartment department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private ResCompany company;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

}
