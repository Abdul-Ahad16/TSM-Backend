package com.stg.tsm.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserstoryCreateMobileDTO {
	
	 private int userstoryId;

	    private String createdBy;

	    private Date createdDate;

	    private String estimatedStorypoints;


	    private String plannedEfforts;

	    private String updatedBy;

	    private Date updatedDate;

	    private String userstoryDescription;

	    private String userstoryName;

}
