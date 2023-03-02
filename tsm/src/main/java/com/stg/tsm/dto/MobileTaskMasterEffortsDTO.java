package com.stg.tsm.dto;

import java.util.List;

import com.stg.tsm.entity.TaskMaster;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MobileTaskMasterEffortsDTO {
	
	private String onDatetotalWokrHours;
//	private EffortsRecordDTO effortsRecord;
	private List<EffortsRecordDTO> effortsRecord;
	//private List<TaskMaster> taskMasters;

}
