package com.stg.tsm.dto;

import java.util.Date;
import java.util.List;

import com.stg.tsm.entity.ProjectAssignment;

import lombok.Data;

@Data
public class MobileEmployeeResponse {
    private int employeeId;
    private String employeeName;
    private String username;
    private String email;
    private List<ProjectAssignment> projectAssignments;
    private String empRole;
    private List<String> empFunctions;
}
