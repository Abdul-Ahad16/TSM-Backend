package com.stg.tsm.dto;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import com.stg.tsm.entity.TaskMaster;
import com.stg.tsm.entity.TaskMasterPK;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EffortsRecordDTO {
	
//	private int userstoryId;
//	private String userstoryName;
//	private Date date;
//	private String activity;
//	private LocalTime effortsSpent;
//	private TaskMasterPK taskMaster;
	private String userstoryName;
	private List<TaskMaster> taskMasters;

}
