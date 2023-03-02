package com.stg.tsm.dto;

import java.util.List;

import com.stg.tsm.entity.ProjectApplicationDetail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MobileAuthenticationResponseDTO {
    
   private MobileDashboardData dashboard;
   
   private MobileEmployeeResponse employeeData;
   
   private List<MobileProjectResponseDTO> projectData;
   
   
   
   
    

}
