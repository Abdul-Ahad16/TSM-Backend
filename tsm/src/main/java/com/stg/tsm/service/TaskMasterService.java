package com.stg.tsm.service;

import java.util.List;
import java.io.ByteArrayInputStream;

import com.stg.tsm.dto.AllTaskDTO;
import com.stg.tsm.dto.TaskListDTO;
import com.stg.tsm.dto.UserstorySearchDTO;
import com.stg.tsm.entity.TaskMaster;
import com.stg.tsm.exception.TsmException;
import javax.persistence.Tuple;

public interface TaskMasterService {
    
	TaskMaster createTask(int userstoryId, int assignmentId, TaskMaster taskMaster) throws TsmException;

	List<Tuple> readAll(UserstorySearchDTO userstorySearchDTO) throws TsmException;
	
	int countAll(UserstorySearchDTO userstorySearchDTO) throws TsmException;
	
	int countAllBySprintId(UserstorySearchDTO userstorySearchDTO) throws TsmException;
	
	List<Tuple> readUserstoryBySprintAndApplication(UserstorySearchDTO searchDTO) throws TsmException;

	TaskListDTO taskList(UserstorySearchDTO searchDTO) throws TsmException;

	TaskMaster updateTaskMaster(int userstoryId, int assignmentId,int taskId, TaskMaster taskMaster) throws TsmException;
	
	int deleteTaskmasterById(int id) throws TsmException;
	
	ByteArrayInputStream load(List<AllTaskDTO> taskDTO)  throws TsmException;
}
