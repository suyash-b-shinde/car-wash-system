package com.cg.repository;
 
import com.cg.entity.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
 
public interface UserCredentialRepository  extends JpaRepository<UserCredential,Integer> 
{
    Optional<UserCredential> findByUsername(String username);
 
	Optional<UserCredential> findByEmail(String email);
	
	List<UserCredential> findByRole(String role);
	
	List<UserCredential> findByRoleAndStatus(String role, String status);

	Optional<UserCredential> findByName(String name);

}