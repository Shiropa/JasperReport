package org.example.test.database.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table
public class Board {

	@Id
	private Short id;

	private String name;

	private String nameBn;

	private String nameAr;

	private String address;

	private String phone;

	private String email;

	private String shortName;

}

