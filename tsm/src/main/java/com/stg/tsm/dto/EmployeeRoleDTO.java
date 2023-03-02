package com.stg.tsm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeRoleDTO {
    private int empId;
    private String roleName;
    private String description;
}
