package com.stg.tsm.repos;

import com.stg.tsm.entity.EmployeeDetail;
import com.stg.tsm.entity.ProjectApplicationDetail;
import com.stg.tsm.entity.ProjectAssignment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProjectAssignmentRepository extends JpaRepository<ProjectAssignment, Integer> {
    
    ProjectAssignment findProjectAssignmentByEmployeeDetailAndProjectApplicationDetail(EmployeeDetail employeeId,ProjectApplicationDetail projectApplicationId);

    @Query(value = "select * from project_assignment where employee_id = ?1", nativeQuery = true)
    public abstract List<ProjectAssignment> findAllByEmployeeId(@Param("employeeId") int employeeId);
}