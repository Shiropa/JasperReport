package org.example.test.dto.JasperReport;

import lombok.Data;
import org.example.test.dto.RegistrationDTO;

@Data
public class AdmittedRegisteredCandidateDTO extends RegistrationDTO {

	private String serialNoBn;

	private String dateOfBirthBn;

	private String registrationIdBn;
}

