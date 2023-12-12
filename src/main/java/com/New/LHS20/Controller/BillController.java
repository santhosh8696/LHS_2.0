package com.New.LHS20.Controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.New.LHS20.Dao.BillRepositry;
import com.New.LHS20.Dao.PrescriptionRepository;
import com.New.LHS20.Dao.SupplimentsRepository;
import com.New.LHS20.Entity.Bill;
import com.New.LHS20.Entity.Doctor_Prescription;
import com.New.LHS20.Entity.Suppliments;
import com.New.LHS20.Exception.RegistrationCustomException;
import com.New.LHS20.Service.BillService;

@RestController
@RequestMapping("/api")
public class BillController {

	@Autowired
	BillRepositry billRepositry;

	@Autowired
	PrescriptionRepository prescriptionRepository;

	@Autowired
	SupplimentsRepository supplimentsRepository;
	@Autowired
	BillService billService;

	
	//pharmacist will calaculate all the medicines amount  
	@PostMapping("/addbillpharmacy")
	public ResponseEntity addBill(@RequestBody Bill bill) {

  return billService.addBill(bill);
	}
	//pharmacist will add   amount for Medicines
	@Transactional
	@PostMapping("/addbillequip")
	public int addBillMedical(@RequestBody Bill bill) {

			 
		return billService.addBillMedical(bill);


	}

}
