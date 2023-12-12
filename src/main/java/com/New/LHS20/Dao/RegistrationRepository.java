package com.New.LHS20.Dao;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.New.LHS20.Entity.RegistrationForm;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrationRepository extends JpaRepository<RegistrationForm, Long> {

	boolean existsByUsername(String username);

	boolean existsByEmail(String email);

	RegistrationForm findByUsername(String username);

	RegistrationForm findByEmail(String email);

	RegistrationForm findByPhoneNo(String phoneno);

	boolean existsByPhoneNo(String number);

	List<RegistrationForm> findByRoleName(String rolename);

	boolean existsByRoleName(String number);
//
//	RegistrationForm findById(long userId);



    RegistrationForm save(Optional<RegistrationForm> regform1);
    
    
    List<RegistrationForm> findByUserId(Long specid);
    Optional<RegistrationForm> findById(long userId);

}
