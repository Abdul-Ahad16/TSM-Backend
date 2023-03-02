package com.stg.tsm.dto;


import lombok.Data;

import java.sql.Date;
import java.time.LocalTime;

@Data
public class TaskMasterDTO {

    private Integer taskId;

    private Integer userStoryId;

    private Integer assignmentId;

    private String taskName;

    private LocalTime efforts;

    private Date date;

    private String createdBy;

    private Date createdDate;

    private String updatedBy;

    private Date updatedDate;

}
