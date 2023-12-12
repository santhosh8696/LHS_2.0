package com.New.LHS20.Controller;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.New.LHS20.Dao.DoctorPrescriptionRepository;
import com.New.LHS20.Dao.SupplimentsRepository;
import com.New.LHS20.Entity.Doctor_Prescription;
import com.New.LHS20.Entity.MonitoringData;
import com.New.LHS20.Entity.Patient;
import com.New.LHS20.Entity.Suppliments;
import com.New.LHS20.Service.PharmacistService;

@RestController
@RequestMapping("/api")
public class PharmacistController {

	@Autowired
	SupplimentsRepository supplimentsRepository;

	@Autowired
	DoctorPrescriptionRepository doctorPrescriptionRepository;

	
	
	//pharmacist will add   amount for suppliments
	@PostMapping("/addingAmount")
	public ResponseEntity addingAmountForSuppliments(@RequestBody Suppliments suppliments) {

		return pharmacistService.addingAmountForSuppliments(suppliments);
	}

	//pharmacist will add   amount for Medicines
	@PostMapping("/addingMedicinesAmount")
	public ResponseEntity addingMedicinesAmount(@RequestBody Doctor_Prescription doctor_presc) {

		return pharmacistService.addingAmountForSuppliments(doctor_presc);
	}
	
	
	@Autowired
	PharmacistService pharmacistService;
	
	
	
	// Pharmacist can fetch supplements data  by using patient id
    @GetMapping("/supliments/{patient}")
    public ResponseEntity get(@PathVariable  Patient patient) {
     List<Suppliments> suppliments=pharmacistService.fetchById(patient);
       

        return new ResponseEntity(suppliments , HttpStatus.OK);
    }


    
    // Pharmacist can fetch doctorprescription data  by using patient id
    @GetMapping("/docpresc/{patient}")
    public ResponseEntity get1(@PathVariable  Patient patient) {
     List<Doctor_Prescription> prsc=pharmacistService.fetchById1(patient);
       

        return new ResponseEntity(prsc , HttpStatus.OK);
    }
}
