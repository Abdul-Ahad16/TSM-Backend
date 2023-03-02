package com.stg.tsm.dto;

import java.util.Optional;

import com.stg.tsm.entity.RoleMaster;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeProfileMobileDTO {
    private String empName;
    private String roleName;
    private String EmailId;
}
