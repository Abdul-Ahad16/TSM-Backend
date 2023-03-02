package com.stg.tsm.dto;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalendarResponseDTO {
	
	private Date date;
	
	private LocalTime numOfhoursWorkedPerDay;
	
	private int userstoryId;
	
	private String userstoryName;
	
	private String taskName;


	
}
