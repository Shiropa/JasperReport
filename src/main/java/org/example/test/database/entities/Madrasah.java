package org.example.test.database.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table
public class Madrasah {

	@Id
	private Short id;

	@ManyToOne
	@JoinColumn(name = "board_id", columnDefinition = "int4")
	private Board board;

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

//	private Integer boardId;

}

