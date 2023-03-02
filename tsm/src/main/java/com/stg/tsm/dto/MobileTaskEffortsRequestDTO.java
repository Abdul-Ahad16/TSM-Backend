package com.stg.tsm.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MobileTaskEffortsRequestDTO {
	
	private int employeeId;
	private int projectId;
	private int applicationId;
	private int sprintId;
	private Date date;

}
