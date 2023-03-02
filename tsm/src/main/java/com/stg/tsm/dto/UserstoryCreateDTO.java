package com.stg.tsm.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserstoryCreateDTO {

	private int userstoryId;
    private int sprintId;
    private int projectId;

    private String createdBy;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date createdDate;

    private String estimatedStorypoints;


    private String plannedEfforts;

    private String updatedBy;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date updatedDate;

    private String userstoryDescription;

    private String userstoryName;
}
