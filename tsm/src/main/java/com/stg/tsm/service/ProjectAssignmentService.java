package com.stg.tsm.service;

import java.util.List;
import com.stg.tsm.dto.ProjectAssignmentRequestDTO;
import com.stg.tsm.entity.ProjectAssignment;
import com.stg.tsm.exception.TsmException;

public interface ProjectAssignmentService {
    
    
    int projectAssignmentByProjectAndEmployeeId(int projectApplicationId, int employeeId) throws TsmException;
    

    List<ProjectAssignment> createApplicationAssignmnet(List<ProjectAssignmentRequestDTO> assignmentRequestDTOs) throws TsmException;

}
