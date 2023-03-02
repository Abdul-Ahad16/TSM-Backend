package com.stg.tsm.dto;

import java.util.List;

import com.stg.tsm.entity.UserstoryMaster;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateSprintResponseDTO {
	private int sprintId;
	private String sprintName;
	private String sprintDescription;
	private String createdDate;
	private String startDate;
	private String endDate;
	private String createdBy;
	private List<UserstoryMaster> userstoryMasters;
}
