package com.stg.tsm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MobileDashboardData {
    
    private int targetHoursCurrentMonth;
    
    private String totalHoursWorkedCurrentMonth;

    private int targetSprintHours;
    
    private int totalSprintHoursWorked;
}
