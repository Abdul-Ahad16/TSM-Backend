package com.stg.tsm.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationAllResponseDTO {

    private int applicationId;
    
    private String applicationName;
    
    private int projectId;
    
    private String ApplicationNickName;
}
