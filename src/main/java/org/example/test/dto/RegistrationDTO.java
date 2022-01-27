package org.example.test.dto;

import lombok.Data;
import org.example.test.database.entities.ApplicantType;
import org.example.test.database.entities.Madrasah;

import java.sql.Timestamp;
import java.time.LocalDate;

@Data
public class RegistrationDTO {

	private Long id;

	private String registrationId;

	private int examYearId;

	private int examTypeId;

	private String name;

	private String nameBn;

	private String nameAr;

	private String fatherName;

	private String fatherNameBn;

	private String fatherNameAr;

	private String motherName;

	private String motherNameBn;

	private String motherNameAr;

	private Integer gender;

	private LocalDate dateOfBirth;

	private String identityType;

	private String identityNumber;

	private String mobileNumber;

	private String email;

	private String village;

	private String postOffice;

	private String thana;

	private Integer districtId;
//
//	private Integer applicantTypeId;
//
//	private Integer madrasahId;

	private Boolean isDeleted;

	private Timestamp createdOn;

	private String arabicName;

	private MadrasahDTO madrasah;

	private ApplicantTypeDTO applicantType;

}

