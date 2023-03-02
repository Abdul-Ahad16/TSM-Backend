package com.stg.tsm.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.stg.tsm.entity.SprintMaster;

@Repository
public interface SprintMasterRepository extends JpaRepository<SprintMaster, Integer> {
	
	@Query(value = "select * from sprint_master where application_id = ?1", nativeQuery = true)
	public abstract List<SprintMaster> findAllByApplicationId(@Param("applicationId") int applicationId);
	
}