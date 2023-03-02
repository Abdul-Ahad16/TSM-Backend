package com.stg.tsm.repos;

import java.util.List;

import javax.persistence.Tuple;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.stg.tsm.entity.ProjectMaster;

public interface ProjectMasterRepository extends JpaRepository<ProjectMaster, Integer> {

	@Query(value = "select pm.PROJECT_ID,pa.APPLICATION_ID,pa.ASSIGNMENT_ID from project_application_detail pm,\r\n"
			+ "           project_assignment pa,\r\n" + "        employee_detail ed\r\n"
			+ "            where pm.APPLICATION_ID = pa.APPLICATION_ID and\r\n"
			+ "           pa.EMPLOYEE_ID = ed.EMPLOYEE_ID and\r\n"
			+ "            ed.EMPLOYEE_ID = ?1 ", nativeQuery = true)
	List<Tuple> findAllByEmployeeId(int employeeId);

}