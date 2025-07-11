package com.example.Washer.Repository;
 
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.Washer.Entity.Washer;
 
@Repository
public interface WasherRepo extends JpaRepository<Washer, Integer> {
	List<Washer> findByWasherStatus(String status);
}

