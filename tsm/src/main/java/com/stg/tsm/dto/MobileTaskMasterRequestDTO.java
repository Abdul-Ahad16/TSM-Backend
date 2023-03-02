package com.stg.tsm.dto;

import com.stg.tsm.entity.TaskMaster;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MobileTaskMasterRequestDTO {
	private int employeeId;
	private int projectApplicationId;
	private int userstoryId;
	private TaskMasterDTO taskMaster;
	
}
