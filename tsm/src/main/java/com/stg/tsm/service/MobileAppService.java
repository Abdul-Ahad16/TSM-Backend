package com.stg.tsm.service;

import com.stg.tsm.dto.CreateUserStoryResponseDTO;
import com.stg.tsm.dto.MobileAuthenticationResponseDTO;
import com.stg.tsm.dto.MobileUserStoryResopnseDto;
import com.stg.tsm.dto.ResetPasswordDTO;
import com.stg.tsm.dto.UserstoryCreateDTO;
import com.stg.tsm.entity.EmployeeDetail;
import com.stg.tsm.entity.TaskMaster;
import com.stg.tsm.entity.TaskMasterPK;
import com.stg.tsm.entity.UserstoryMaster;
import com.stg.tsm.exception.MobileTsmException;
import com.stg.tsm.exception.TsmException;
import com.stg.tsm.dto.EmployeeProfileMobileDTO;

public interface MobileAppService {
    
    MobileAuthenticationResponseDTO getEmployeeData(EmployeeDetail employeeDetailTemp) throws TsmException;
    
	TaskMaster createTasks(int employeeId, int projectApplicationId,int userstoryId, TaskMaster taskMaster) throws TsmException; 

	EmployeeProfileMobileDTO getEmployeeProfile(int employeeId) throws MobileTsmException;

    String changePassword(ResetPasswordDTO resetPasswordDTO) throws TsmException;
    
    CreateUserStoryResponseDTO createUserstoryMaster(int sprintId, UserstoryCreateDTO userstoryMaster) throws MobileTsmException;
    
    MobileUserStoryResopnseDto getUserstoryMasterList(int sprintId) throws MobileTsmException;
    
    String updateUserstoryMaster(UserstoryMaster UserstoryMaster) throws TsmException;

}
