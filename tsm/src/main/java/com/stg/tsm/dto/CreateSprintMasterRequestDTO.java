package com.stg.tsm.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateSprintMasterRequestDTO {
	
	private int projectId;
	private int applicationId;
	private String sprintName;
	private String sprintDescription;
	private String createdBy;
	private Date startDate;
	private Date endDate;
	private Date createdDate;
	
}
