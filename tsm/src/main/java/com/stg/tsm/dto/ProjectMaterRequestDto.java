package com.stg.tsm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectMaterRequestDto {
    
    private int projectId;
    
    private int projectApplicationId;
    
    private int employeeId;
    
    private int assignmnetId;
    

}
