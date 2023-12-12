package com.New.LHS20.Dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.New.LHS20.Entity.AdmissionForm;
import com.New.LHS20.Entity.Nurse;

 

@Repository
public interface NurseRepository extends JpaRepository<Nurse, Long> {
	  

 

Nurse save(Nurse nurse);

void save(Optional<Nurse> nurse);

Nurse findByUserId(long userId);

	     

	}
    

