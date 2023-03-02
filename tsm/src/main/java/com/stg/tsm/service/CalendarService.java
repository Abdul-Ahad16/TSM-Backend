package com.stg.tsm.service;

import com.stg.tsm.dto.CalendarRequestDTO;

import com.stg.tsm.dto.CalendarTotalNumofWorkPerMonth;
import com.stg.tsm.exception.TsmException;

public interface CalendarService {

    CalendarTotalNumofWorkPerMonth noOfHoursWorkedPerDayBySprintId(CalendarRequestDTO calendarRequestDTO)
            throws TsmException;

    CalendarTotalNumofWorkPerMonth noOfHoursWorkedPerDay(CalendarRequestDTO calendarRequestDTO) throws TsmException;

}
