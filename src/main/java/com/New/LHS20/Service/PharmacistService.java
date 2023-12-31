package com.New.LHS20.Service;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.New.LHS20.Dao.DoctorPrescriptionRepository;
import com.New.LHS20.Dao.SupplimentsRepository;
import com.New.LHS20.Entity.Doctor_Prescription;
import com.New.LHS20.Entity.MonitoringData;
import com.New.LHS20.Entity.Patient;
import com.New.LHS20.Entity.Suppliments;

@Service
public class PharmacistService {

	@Autowired
	SupplimentsRepository supplimentsRepository;
	@Autowired
	DoctorPrescriptionRepository doctorPrescriptionRepository;
	
	public List<Suppliments> fetchById(Patient patient) {
		 List<Suppliments> suppliments=supplimentsRepository.findByPatient(patient);
//       MonitoringData   monitoringdata = monitoring.get(0);
            return suppliments;
            


	}

	public List<Doctor_Prescription> fetchById1(Patient patient) {
		 List<Doctor_Prescription> prescc=doctorPrescriptionRepository.findByPatient(patient);
//      MonitoringData   monitoringdata = monitoring.get(0);
           return prescc;
	}
	
	
	//pharmacist will add   amount for suppliments

	public ResponseEntity addingAmountForSuppliments(@RequestBody Suppliments suppliments) {
		List<Suppliments> suppliments2 = supplimentsRepository.findByPatient(suppliments.getPatient());
		Iterator iterator = suppliments2.iterator(); 
		while (iterator.hasNext()) {
			Suppliments suppliments3 = (Suppliments) iterator.next();
			if (suppliments.getName().equals(suppliments3.getName())) {
				suppliments3.setAmount(suppliments.getAmount());
				return new 
						ResponseEntity<>(supplimentsRepository.save(suppliments3), HttpStatus.ACCEPTED);
			}

		}
		return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
	}

	//pharmacist will add   amount for Medicines
	public ResponseEntity addingAmountForSuppliments(@RequestBody Doctor_Prescription doctor_presc) {
		List<Doctor_Prescription> doctor_Prescriptions = doctorPrescriptionRepository
				.findByPatient(doctor_presc.getPatient());
		Iterator iterator = doctor_Prescriptions.iterator();
		while (iterator.hasNext()) {
			Doctor_Prescription doctor_Prescription3 = (Doctor_Prescription) iterator.next();
			if (doctor_presc.getMedicineName().equals(doctor_Prescription3.getMedicineName())) {
				doctor_Prescription3.setAmount(doctor_presc.getAmount());
				return new ResponseEntity<>(doctorPrescriptionRepository.save(doctor_Prescription3),
						HttpStatus.ACCEPTED);
			}
			
	}
		return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
	}


}
