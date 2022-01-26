package org.example.test.database.entities;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;

@Entity
@Data
@Table
public class Registration {

	@Id
	private Long id;

	@ManyToOne
	@JoinColumn(name = "madrasah_id", columnDefinition = "int4")
	private Madrasah madrasah;

	@ManyToOne
	@JoinColumn(name = "applicant_type_id", columnDefinition = "int4")
	private ApplicantType applicantType;

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

//	private Integer applicantTypeId;

//	private Integer madrasahId;

	private Boolean isDeleted;

	private Timestamp createdOn;

	private String arabicName;

}

