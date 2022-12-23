package com.example.binding;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "CITIZEN_PLANE_INFO")
public class CitizenPlane {
	@Id
	private Integer cid;	
	private String cname;
	private String cemail;
	private String gender;
	private long phno;
	private long ssn;
	private String planeName;
	private String planeStatus;
	

}
