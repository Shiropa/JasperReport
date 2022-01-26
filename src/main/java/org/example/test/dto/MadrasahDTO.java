package org.example.test.dto;

import lombok.Data;
import java.util.List;

@Data
public class MadrasahDTO {

	private Short id;

	private String code;

	private String name;

	private String nameBn;

	private String nameAr;

	private String address;

	private Integer districtId;

	private Integer divisionId;

	private Integer genderId;

	private String ilhak;

	private String phone;

	private Integer boardId;

	List<RegistrationDTO> registrations;

}

