package com.stg.tsm.dto;


import java.util.List;

import com.stg.tsm.entity.ProjectAssociation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor

public class ProjectAllResponseDTO {

    private int projectId;
    
    private String projectName;
    
    private String projectNickName;
    
    private List<ApplicationAllResponseDTO> projectApplicationDetails;
    
    
}
