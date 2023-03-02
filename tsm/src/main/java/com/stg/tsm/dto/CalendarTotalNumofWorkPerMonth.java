package com.stg.tsm.dto;


import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalendarTotalNumofWorkPerMonth {
    
    
    private String totalHoursWorkedPerMonth;
    
    private int targetHoursWorkPerMonth;
    
    private Date startDate;
    
    private Date endDate;
    
    private Map<Date,Integer> dates;
    
    private List<CalendarResponseDTO> CalendarResponseDTOs;

}
