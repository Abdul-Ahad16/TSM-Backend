package com.stg.tsm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResetPasswordDTO {

    private int employeeId;
    
    private String employeeName;
    
    private String employeeEmail;
    
    private String oldPassword;
    
    private String newPassword;
    
    private int adminId;
    
    private String adminName;
    
    private String userName;
    
    private String adminEmail;
    
    
    
}
