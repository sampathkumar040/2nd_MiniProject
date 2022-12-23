package com.example.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.binding.CitizenPlane;
@Repository
public interface CitizenRepository extends JpaRepository<CitizenPlane, Serializable> {
	
	@Query("select distinct(planeName) from CitizenPlane")
	public List<String> getPlaneNames();
	
	@Query("select distinct(planeStatus) from CitizenPlane") 
	public List<String> getPlaneStatus();
	
	
	

}
