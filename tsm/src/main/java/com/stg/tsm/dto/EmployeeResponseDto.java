package com.stg.tsm.dto;

import com.stg.tsm.entity.EmployeeDetail;
import com.stg.tsm.entity.ProjectAssignment;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * A DTO for the {@link EmployeeDetail} entity
 */
@Data
public class EmployeeResponseDto implements Serializable {
    private int employeeId;
    private String createdBy;
    private Date createdDate;
    private String employeeName;
    private String username;
    private String email;
    private Date joiningDate;
    private Date relievingDate;
    private String updatedBy;
    private Date updatedDate;
    private List<ProjectAssignment> projectAssignments;
    private String empRole;
    private List<String> empFunctions;
}