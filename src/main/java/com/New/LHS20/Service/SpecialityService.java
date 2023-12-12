//package com.New.LHS20.Service;
//
//import java.util.Collection;
//import java.util.List;
//import java.util.Optional;
//
//import javax.transaction.Transactional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.New.LHS20.Controller.SpecialityController;
//import com.New.LHS20.Dao.SpecialityRepository;
//import com.New.LHS20.Entity.Speciality;
// 
//
// 
//@Service
//public class SpecialityService {
//	 
// 
//
//	    private final SpecialityRepository specialityRepository;
//
//	    @Autowired
//	    public SpecialityService(SpecialityRepository specialityRepository) {
//	        this.specialityRepository = specialityRepository;
//	    }
//
//	    @Transactional
//	    public Collection<Speciality> findAll() {
//	        return specialityRepository.findAll();
//	    }
//
//	    @Transactional
//	    public Optional<Speciality> findById(Integer id) {
//	        return specialityRepository.findById(id);
//	    }
//
//	    @Transactional
//	    public Speciality save(Speciality speciality) {
//	        return specialityRepository.save(speciality);
//	    }
//
//	    @Transactional
//	    public void deleteById(Integer id) {
//	        specialityRepository.deleteById(id);
//	    }
//	    public List<Speciality> findByName(String name) {
//	        return specialityRepository.findByName(name);
//	    }
//
//
//	}
//
//
//
