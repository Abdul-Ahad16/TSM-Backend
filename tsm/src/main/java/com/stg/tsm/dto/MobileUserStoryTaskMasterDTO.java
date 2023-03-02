package com.stg.tsm.dto;

import java.util.List;

import com.stg.tsm.entity.TaskMaster;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MobileUserStoryTaskMasterDTO {
	private String userstoryName;
	private List<TaskMaster> taskMasters;
}
