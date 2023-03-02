package com.stg.tsm.service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stg.tsm.dto.ProjectAssignmentRequestDTO;
import com.stg.tsm.entity.EmployeeDetail;
import com.stg.tsm.entity.ProjectApplicationDetail;
import com.stg.tsm.entity.ProjectAssignment;
import com.stg.tsm.exception.TsmException;
import com.stg.tsm.repos.EmployeeDetailRepository;
import com.stg.tsm.repos.ProjectApplicationDetailRepository;
import com.stg.tsm.repos.ProjectAssignmentRepository;
import com.stg.tsm.service.ProjectAssignmentService;

/**
 * 
 * @author saikrishnan
 * @author jenifer
 *
 */

@Service
public class ProjectAssignmentImpl implements ProjectAssignmentService {

    @Autowired
    private EmployeeDetailRepository employeeDetailRepository;

    @Autowired
    private ProjectApplicationDetailRepository projectApplicationDetailRepository;

    @Autowired
    private ProjectAssignmentRepository assignmentRepository;


    @Override
    public int projectAssignmentByProjectAndEmployeeId(int projectApplicationId, int employeeId)
            throws TsmException {

        EmployeeDetail employeeDetail = employeeDetailRepository.findById(employeeId)
                .orElseThrow(() -> new TsmException("Employee is not Found"));

        ProjectApplicationDetail projectApplicationDetail = projectApplicationDetailRepository.findById(projectApplicationId)
                .orElseThrow(() -> new TsmException("Project Application is not Found"));

        ProjectAssignment projectAssignment = assignmentRepository
                .findProjectAssignmentByEmployeeDetailAndProjectApplicationDetail(employeeDetail, projectApplicationDetail);

        return projectAssignment.getAssignmentId();
    }


    @Override
    public List<ProjectAssignment> createApplicationAssignmnet(List<ProjectAssignmentRequestDTO> assignmentRequestDTOs) throws TsmException {
        
        List<ProjectAssignment> assignments = new ArrayList<>();
         
        for (ProjectAssignmentRequestDTO projectAssignmentRequestDTO : assignmentRequestDTOs) {
            ProjectAssignment projectAssignment = new ProjectAssignment();
            
            EmployeeDetail employeeDetail = employeeDetailRepository.findById(projectAssignmentRequestDTO.getEmployeeId())
                    .orElseThrow(() -> new TsmException("Employee is not Found"));
            
            ProjectApplicationDetail projectApplicationDetail = projectApplicationDetailRepository.findById(projectAssignmentRequestDTO.getProjectApplicationId())
                    .orElseThrow(() -> new TsmException("Project Application is not Found"));
            
            projectAssignment.setEmployeeDetail(employeeDetail);
            projectAssignment.setProjectApplicationDetail(projectApplicationDetail);
            projectAssignment.setCreatedBy(projectAssignmentRequestDTO.getCreatedBy());
            projectAssignment.setCreatedDate(projectAssignmentRequestDTO.getCreatedDate());
            projectAssignment.setAssignmentStartDate(projectAssignmentRequestDTO.getAssignmentStartDate());
            projectAssignment.setAssignmentEndDate(projectAssignmentRequestDTO.getAssignmentEndDate());
            projectAssignment.setBillable(projectAssignmentRequestDTO.getBillable());
            
            
            assignments.add(projectAssignment);
        }
        
       
        return this.assignmentRepository.saveAll(assignments);
    }    

}
