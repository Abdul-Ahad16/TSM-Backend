package com.stg.tsm.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectAssignmentRequestDTO {
    
    
    private int employeeId;
    
    private String assignmnetRole;
    
    private int projectApplicationId;
    
    private String createdBy;
    
    private String updatedBy;
    
    private Date createdDate;
    
    private Date updatedDate;
    
    private Boolean billable;
    
    private Date assignmentStartDate;
    
    private Date assignmentEndDate;
    

}
