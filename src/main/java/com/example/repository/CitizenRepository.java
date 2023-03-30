package com.example.repository;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.convert.QueryByExamplePredicateBuilder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.example.entity.CitizenPlane;


public interface CitizenRepository extends JpaRepository<CitizenPlane, Serializable>, JpaSpecificationExecutor<CitizenPlane> {
	
	@Query("select distinct(planName) from CitizenPlane")
	public List<String> getPlanNames();
	
	@Query("select distinct(planStatus) from CitizenPlane") 
	public List<String> getPlanStatus();
	
	
	default Specification<CitizenPlane> getSpecFromDates(LocalDate startDate,LocalDate endDate,Example<CitizenPlane> example){
		
		return (Specification<CitizenPlane> )(root,query,builder)->{
			final List<Predicate> predicate=new ArrayList<>();
			if(startDate!=null) {
				predicate.add(builder.greaterThanOrEqualTo(root.get("startDate"), startDate));
			}
			if(endDate!=null) {
				predicate.add(builder.lessThanOrEqualTo(root.get("endDate"), endDate));
			}
			predicate.add(QueryByExamplePredicateBuilder.getPredicate(root	, builder, example));
			return builder.and(predicate.toArray(new Predicate[predicate.size()]));
			
		};
		
	}
	
	default List<CitizenPlane> findAllByConditions(CitizenPlane entity,LocalDate startdDate,LocalDate endDate){
		Example<CitizenPlane> example=Example.of(entity);
		System.out.println(getSpecFromDates(startdDate, endDate, example));
		return findAll(getSpecFromDates(startdDate, endDate, example));
	}
	
	
	
	
	
}
