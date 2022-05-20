package com.fteam.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "staffs")
public class Staff {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;	
	
	@Column(length = 255, nullable = false)
	private String fullname;
	
	@Column(name = "phone_number", length = 20, nullable = false)
	private String phoneNumber;
	
	private Boolean enabled;
	
	@Column(length = 64, nullable = false)
	private String password;
	
	@Column(length = 255, nullable = false, unique = true)
	private String email;
	
	@Column(length = 255)
	private String photo;
	
	@OneToMany(mappedBy = "staff")
	private List<Order> orders;

}



































