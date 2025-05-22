package com.oop7hkem10.hrm.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class HrEmployeeDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String name;

    @Size(max = 255)
    private String code;

    @Size(max = 255)
    private String email;

    @Size(max = 255)
    private String workPhone;

    @NotNull
    private Gender gender;

    private Map<String, String> status;

    private LocalDate dateJoin;

    @Size(max = 255)
    private String dateLeft;

    @Size(max = 255)
    private String country;

    @Size(max = 255)
    private String idNumber;

    private LocalDate idDate;

    @Size(max = 255)
    private String idAddress;

    private LocalDate birthday;

    @Size(max = 255)
    private String placeOfBirth;

    @Size(max = 255)
    private String permanentAddress;

    @Size(max = 255)
    private String currentAddress;

    @Size(max = 255)
    private String passport;

    private Map<String, String> maritalStatus;

    @Size(max = 255)
    private String emergencyContact;

    @Size(max = 255)
    private String emergencyPhone;

    @Size(max = 255)
    private String socialInsuranceCode;

    @Size(max = 255)
    private String taxCode;

    @Size(max = 255)
    private String religion;

    @JsonProperty("isUnion")
    private Boolean isUnion;

    @NotNull
    private Long company;

    @NotNull
    private Long department;

    @NotNull
    private Long jobPosition;

    @NotNull
    private Long jobTitle;

    @NotNull
    private Long province;

    @NotNull
    private Long district;

    @NotNull
    private Long ward;

    @NotNull
    @HrEmployeeUserUnique
    private Long user;

    private Long manager;

}
