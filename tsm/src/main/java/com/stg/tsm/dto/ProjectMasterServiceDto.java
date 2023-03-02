package com.stg.tsm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectMasterServiceDto {
    
    private int projectId;
    
    private String projectName;
    
    private int assignmnetId;
    
    private int applicationId;

}
