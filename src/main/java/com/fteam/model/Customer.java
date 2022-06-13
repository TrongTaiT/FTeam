package com.fteam.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customers")
public class Customer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;	

	@Column(length = 255, nullable = false, unique = true)
	private String email;

	@Column(length = 64, nullable = false)
	private String password;
	
	@Column(length = 255, nullable = false)
	private String fullname;
	
	@Column(name = "phone_number", length = 20, nullable = false, unique = true)
	private String phoneNumber;
	
	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern ="yyyy-MM-dd")
	private Date birthday;
	
	@Column(length = 255, nullable = false)
	private String address;
	
	private Boolean enabled;	
	
	@Column(length = 255)
	private String photo;
	
	@Column(name = "verification_code", updatable = false)
	private String  verificationCode;
	
	@OneToMany(mappedBy = "customer")
	private List<Rating> ratings;
	
	@OneToMany(mappedBy = "customer")
	private List<Order> orders;
	
	@Transient
	public String getPhotoPath() {
		// null xử lý ở client
		return "/images/customers/" + this.id + "/" + this.photo;
	}

}



































