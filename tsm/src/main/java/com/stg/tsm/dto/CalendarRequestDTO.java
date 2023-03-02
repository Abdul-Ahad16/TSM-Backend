package com.stg.tsm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalendarRequestDTO {
	
	private int employeId;
	
	private int projectId;
	
	private int assignmentId;
	
	private int sprintId;
	
	private int taskmasterId;
	
	
	

}
