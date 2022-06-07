package com.fteam.dto;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.fteam.utilities.RegexValidation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {

	private Integer id;

	@Email
	@NotNull
	@Size(max = 255)
	private String email;

	@Pattern(regexp = RegexValidation.PASSWORD_REGEX)
	private String password;
	
	@Pattern(regexp = RegexValidation.PASSWORD_REGEX)
	private String confirmPassword;

	@Size(max = 255)
	@Pattern(regexp = RegexValidation.NAME_REGEX)
	private String fullname;

	@Size(max = 20)
	@Pattern(regexp = RegexValidation.PHONE_REGEX)
	private String phoneNumber;
	
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birthday;
	
	@NotBlank
	@Size(max = 255)
	private String address;
	
	private String photo;

}
