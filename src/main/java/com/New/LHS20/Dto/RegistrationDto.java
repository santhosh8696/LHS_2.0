package com.New.LHS20.Dto;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class RegistrationDto {

	private long userId;
	@NotNull
	@Size(min = 2, message = "firstname should have at least 2 characters")
	private String firstName;

	@NotNull
	@Size(min = 2, message = "lastname should have at least 2 characters")
	private String lastName;

	@NotNull
	@Email
	private String email;
	private String phoneNo;
	private String dob;
	private String gender;

	// user name should not null or empty
	// user name should have at least 2 characters

	@Column(unique = true)
	private String username;

	// password should not null or empty
	// password should have at least 8 characters

	private String password;

	private String roles;

}
