package com.New.LHS20.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@AllArgsConstructor
@Component
public class AdmissionForm {

	
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private long id;
private long regdNo;
private String admissionDate;
private String doctor;
private String disease;
private String ward;
private long roomNo;
private int bedNo; 


}

