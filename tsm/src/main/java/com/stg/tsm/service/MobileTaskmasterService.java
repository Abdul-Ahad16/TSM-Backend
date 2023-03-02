package com.stg.tsm.service;

import com.stg.tsm.dto.TaskMasterDTO;
import com.stg.tsm.entity.TaskMaster;
import com.stg.tsm.exception.MobileTsmException;
import com.stg.tsm.exception.TsmException;

public interface MobileTaskmasterService {
	 TaskMaster createTasks(int empId, int applicatiopnId,int userStoryId, TaskMaster taskMaster) throws MobileTsmException;
	 
	 TaskMaster updateTaskMaster(int userstoryId, int assignmentId, int taskId, TaskMaster taskMaster)
             throws MobileTsmException;


//	 int deleteTaskmasterById(TaskMaster taskMaster) throws MobileTsmException;

	int deleteTaskmasterById(TaskMasterDTO taskMasterDTO) throws MobileTsmException;
}
