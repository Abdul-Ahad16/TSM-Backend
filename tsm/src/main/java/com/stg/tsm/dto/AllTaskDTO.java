package com.stg.tsm.dto;

import java.time.LocalTime;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AllTaskDTO {
	
	private int userstoryId;
	
	private int assignmentId;
	
	private int taskId;
	
	private String userstoryName;
	
	private String taskName;
	
	private LocalTime efforts;
	
	private Date createdDate;
	
	private Date date;
	
	private String userstoryDescription;
	
	private String sprintName;
}
