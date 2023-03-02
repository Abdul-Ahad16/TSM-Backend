package com.stg.tsm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stg.tsm.dto.CalendarRequestDTO;
import com.stg.tsm.dto.CalendarTotalNumofWorkPerMonth;
import com.stg.tsm.service.CalendarService;


/**
 * 
 * @author saikrishnan
 * @author jenifer
 *
 */

@RestController
@RequestMapping("/calendar")
public class CalendarController {

	
	@Autowired
	private CalendarService calendarService;
	
    @PostMapping(value="/day-work/sprint-id")
	public ResponseEntity<CalendarTotalNumofWorkPerMonth> noOfHoursWorkedPerDayBySprintId(@RequestBody CalendarRequestDTO calendarRequestDTO){
        CalendarTotalNumofWorkPerMonth object = null;
        if(calendarRequestDTO.getSprintId() == 0) {
		    object = calendarService.noOfHoursWorkedPerDay(calendarRequestDTO);
		}else {
		    object = calendarService.noOfHoursWorkedPerDayBySprintId(calendarRequestDTO);
		}
		return  ResponseEntity.status(HttpStatus.OK).body(object);
	}
    
    @PostMapping(value="/day-work")
    public ResponseEntity<CalendarTotalNumofWorkPerMonth> calendarDayWork(@RequestBody CalendarRequestDTO calendarRequestDTO){
        
        CalendarTotalNumofWorkPerMonth object = calendarService.noOfHoursWorkedPerDay(calendarRequestDTO);
        
        return  ResponseEntity.status(HttpStatus.OK).body(object);
    }
}
