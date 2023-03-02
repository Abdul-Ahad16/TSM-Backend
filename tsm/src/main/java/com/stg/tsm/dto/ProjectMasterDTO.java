package com.stg.tsm.dto;


import lombok.Data;

import java.sql.Date;

@Data
public class ProjectMasterDTO {
    private Integer projectId;

    private Integer customerId;

    private String projectName;

    private String level1Manager;

    private String level2Manager;

    private String level3Manager;

    private Integer fteCountStgOnsite;

    private Integer fteCountOtherVendorOnsite;

    private Integer fteCountFordOnsite;

    private Long fteCountStgOffshore;

    private Integer fteCountOtherVendorOffshore;

    private Long fteCountFordOffshore;

    private Date projectStartDate;

    private Date projectEndDate;

    private String createdBy;

    private Date createdDate;

    private String updatedBy;

    private Date updatedDate;



}
