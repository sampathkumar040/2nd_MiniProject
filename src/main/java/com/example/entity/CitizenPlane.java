package com.example.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "CITIZEN_PLANE_INFO")
public class CitizenPlane {
	@Id
	@GeneratedValue
	private Integer cid;	
	private String cname;
	private String cemail;
	private String gender;
	private long phno;
	private long ssn;
	private String planName;
	private String planStatus;
	private LocalDate startDate;
	private LocalDate endDate;
	

}