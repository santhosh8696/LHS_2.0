package com.New.LHS20.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.New.LHS20.Dao.BillRepositry;
import com.New.LHS20.Dao.DoctorRepository;
import com.New.LHS20.Dao.PatientRepository;
import com.New.LHS20.Dao.PrescriptionRepository;
import com.New.LHS20.Dao.ReceptionRepository;
import com.New.LHS20.Dao.RegistrationRepository;
import com.New.LHS20.Dao.SlotRepo;
import com.New.LHS20.Dao.SupplimentsRepository;
import com.New.LHS20.Dao.monitoringDataRepository;
import com.New.LHS20.Entity.AdmissionForm;
import com.New.LHS20.Entity.Bill;
import com.New.LHS20.Entity.Doctor;
import com.New.LHS20.Entity.Doctor_Prescription;
import com.New.LHS20.Entity.MonitoringData;
import com.New.LHS20.Entity.Patient;
import com.New.LHS20.Entity.SlotTime;
import com.New.LHS20.Entity.Suppliments;

@Service
public class ReceptionService {

	@Autowired
	SlotRepo slotrepo;
	@Autowired
	ReceptionRepository receprepo;

	@Autowired
	RegistrationRepository regrepo;

	@Autowired
	Doctor_Prescription doctorprescription;

	@Autowired
	PatientRepository patientrepo;

	@Autowired
	SupplimentsRepository supplimentsRepository;

	@Autowired
	DoctorRepository doctorRepository;

	@Autowired
	PrescriptionRepository prescriptionRepository;

	@Autowired
	monitoringDataRepository monitoringDataRepository;

	@Autowired
	BillRepositry billrepository;

//	public AdmissionForm addAdmittedPatients(AdmissionForm admissionform) {
//	   
//		if((admissionform. getRegdNo()==0)&& (admissionform. getAdmissionDate()==null) && (admissionform. getDoctor()==null)) {
//		 
//		
//		
//		return receprepo.save(admissionform) ;
//		
//		}
//		return admissionform;
//	}

	// receptionist updating the admitted patients
	public AdmissionForm addAdmittedPatients(AdmissionForm admissionform) {
	
		if (receprepo.existsByRegdNo(admissionform.getRegdNo())) {

			AdmissionForm details = receprepo.findByRegdNoAndDate(admissionform.getRegdNo(),
					admissionform.getAdmissionDate());
			admissionform.setId(details.getId());
			admissionform.setDoctor(details.getDoctor());
			admissionform.setAdmissionDate(details.getAdmissionDate());
			admissionform.setBedNo(admissionform.getBedNo());
			admissionform.setDisease(details.getDisease());
//			 
//			   RegistrationForm   regform=  regrepo.findByUsername(details.getDoctor());
//			   doctorprescription.setId(regform.getUserId());
//			   doctorprescription.setPatient(admissionform.getRegdNo());
//			   

//			Doctor doctor = doctorrepo.findById(regform.getUserId());
//			doctor.setFirstName(regform.getFirstName());
//			doctor.setLastName(regform.getLastName());
//			doctor.setId(regform.getUserId());
//			doctorrepo.save(doctor);
			Suppliments suppliments = new Suppliments();
//            suppliments.setId();

			Patient patient = patientrepo.findByUserId(admissionform.getRegdNo());
			suppliments.setPatient(patient);

			if(suppliments.getQuantity()==null) {
				suppliments.setQuantity("0");
			}
			
			supplimentsRepository.save(suppliments);
			Doctor_Prescription presc = new Doctor_Prescription();
			Patient patient2 = patientrepo.findByUserId(patient.getUserId());
			presc.setPatient(patient2);
			 
			Doctor doctor = doctorRepository.findByEmail(details.getDoctor());
			
			presc.setDoctor(doctor);
			prescriptionRepository.save(presc);

			MonitoringData data = new MonitoringData();

			
			data.setPatient(patient);
			Bill bill = new Bill();

			SlotTime slottime2 = new SlotTime();
			List<SlotTime> slot = slotrepo.findByPatientId(admissionform.getRegdNo());
			SlotTime slottime1 = slot.get(0);
			bill.setAppointmenttime(slottime1.getStartTime());
			bill.setAppointmentdate(slottime1.getDate());
			bill.setPatient(patient);
			 
			bill.setDoctor(doctor);
			
			
			billrepository.save(bill);

			monitoringDataRepository.save(data);
			return receprepo.save(admissionform);
		} else {
			throw new RuntimeException("User not found  for the id");
		}
	}

}
