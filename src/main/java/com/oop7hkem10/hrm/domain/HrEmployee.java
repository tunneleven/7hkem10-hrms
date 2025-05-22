package com.oop7hkem10.hrm.domain;

import com.oop7hkem10.hrm.model.Gender;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
@Table(name = "HrEmployees")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class HrEmployee {

    @Id
    @Column(nullable = false, updatable = false)
    @SequenceGenerator(
            name = "employee_primary_sequence",
            sequenceName = "employee_primary_sequence",
            allocationSize = 1,
            initialValue = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "employee_primary_sequence"
    )
    private Long id;

    @Column(nullable = false)
    private String name;

	@Column(unique = true, updatable = false)
	private String code;

	@jakarta.persistence.PrePersist
	private void generateCode() {
		if (this.code == null && this.id != null) {
			this.code = String.format("HTMV%05d", this.id);
		}
	}

    @Column
    private String email;

    @Column
    private String workPhone;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, String> status;

    @Column
    private LocalDate dateJoin;

    @Column
    private String dateLeft;

    @Column
    private String country;

    @Column
    private String idNumber;

    @Column
    private LocalDate idDate;

    @Column
    private String idAddress;

    @Column
    private LocalDate birthday;

    @Column
    private String placeOfBirth;

    @Column
    private String permanentAddress;

    @Column
    private String currentAddress;

    @Column
    private String passport;

    @Column(columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, String> maritalStatus;

    @Column
    private String emergencyContact;

    @Column
    private String emergencyPhone;

    @Column
    private String socialInsuranceCode;

    @Column
    private String taxCode;

    @Column
    private String religion;

    @Column
    private Boolean isUnion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private ResCompany company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private HrDepartment department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_position_id")
    private HrJobPosition jobPosition;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_title_id")
    private HrJobTitle jobTitle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "province_id", nullable = false)
    private ResProvince province;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id", nullable = false)
    private ResDistrict district;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ward_id", nullable = false)
    private ResDistrictWard ward;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private ResUser user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private HrEmployee manager;

    @OneToMany(mappedBy = "employee")
    private Set<HrContract> contractIds;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

}
