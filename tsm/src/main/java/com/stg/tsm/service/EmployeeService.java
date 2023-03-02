package com.stg.tsm.service;

import com.stg.tsm.dto.CreateProjectManagerRequestDTO;
import com.stg.tsm.dto.ResetPasswordDTO;
import com.stg.tsm.entity.EmployeeDetail;
import com.stg.tsm.exception.TsmException;

public interface EmployeeService {
    
    
    EmployeeDetail resetPassword(ResetPasswordDTO resetPasswordDTO) throws TsmException;
    
    EmployeeDetail confirmPassword(ResetPasswordDTO resetPasswordDTO) throws TsmException;
    
    Object createProjectManger(CreateProjectManagerRequestDTO createProjectManagerRequestDTO) throws TsmException;
    
}
