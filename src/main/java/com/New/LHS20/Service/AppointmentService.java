//package com.New.LHS20.Service;
//
//import java.util.Collection;
//
//import javax.transaction.Transactional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.New.LHS20.Dao.AppointmentRepository;
//import com.New.LHS20.Entity.Appointment;
//
// 
//@Service
//public class AppointmentService {
//	 
//
//	    private final AppointmentRepository appointmentRepository;
//
//	    @Autowired
//	    public AppointmentService(AppointmentRepository appointmentRepository) {
//	        this.appointmentRepository = appointmentRepository;
//	    }
//
//	    @Transactional
//	    public Collection<Appointment> findAll() {
//	        return appointmentRepository.findAll();
//	    }
//
//	    @Transactional
//	    public Collection<Appointment> findAppointmentsForDoctor(Integer doctorId) {
//	        return appointmentRepository.findAllByDoctorId(doctorId);
//	    }
//
//	    @Transactional
//	    public Collection<Appointment> findAppointmentsForPatient(Integer patientId) {
//	        return appointmentRepository.findAllByPatientId(patientId);
//	    }
//	}
//
//
