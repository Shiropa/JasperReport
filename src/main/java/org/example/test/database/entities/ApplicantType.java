package org.example.test.database.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Data
@Table
public class ApplicantType {

	@Id
	private Short id;

	private String name;

	private String nameBn;

}

