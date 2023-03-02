package com.stg.tsm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TargetedHoursWithUserStoriesDto {
	
	// This class is for displaying dash board with given task master
	private String totalHoursWorkedPerMonth;
    private int targetHoursWorkPerMonth;
	private String totalSprintUserStoriesEfforts;
	private int sprintTargetedWorkingHours;
	private MobileTaskMasterEffortsDTO taskMasterEfforts;
	
}
