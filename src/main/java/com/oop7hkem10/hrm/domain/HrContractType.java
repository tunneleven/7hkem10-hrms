package com.oop7hkem10.hrm.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@Table(name = "HrContractTypes")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class HrContractType {

    @Id
    @Column(nullable = false, updatable = false)
    @SequenceGenerator(
            name = "contract_type_primary_sequence",
            sequenceName = "contract_type_primary_sequence",
            allocationSize = 1,
            initialValue = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "contract_type_primary_sequence"
    )
    private Long id;

    @Column
    private String name;

    @Column
    private String code;

    @Column
    private Boolean isUnlimited;

    @Column
    private Boolean isProbationaryContract;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

}
