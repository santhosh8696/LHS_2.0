package com.New.LHS20.Entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@NoArgsConstructor
@Entity
public class RegistrationForm {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long userId;

	private String firstName;

	private String lastName;

	private String email;

	private String phoneNo;
	private String dob;
	private String gender;

	private String username;

	private String password;

	private String roleName;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "Registrationform_Authorities", joinColumns = @JoinColumn(name = "reg_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private List<Authorities> roles = new ArrayList<>();
	
//	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
//	@JoinColumn(name="Date",referencedColumnName = "Date")
//	SlotTime2 slotTime2;
	
	
}
