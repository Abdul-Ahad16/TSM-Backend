package com.stg.tsm.dto;

import java.util.List;

import com.stg.tsm.entity.SprintMaster;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MobileApplicationResponseDTO {
    private int applicationId;
    
    private String applicationName;
    
    private String applicationNickName;
    
    private List<SprintMaster> sprints;
}
