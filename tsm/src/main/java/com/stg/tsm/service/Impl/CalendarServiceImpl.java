package com.stg.tsm.service.Impl;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Tuple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stg.tsm.dto.CalendarRequestDTO;
import com.stg.tsm.dto.CalendarResponseDTO;
import com.stg.tsm.dto.CalendarTotalNumofWorkPerMonth;
import com.stg.tsm.entity.SprintMaster;
import com.stg.tsm.exception.TsmException;
import com.stg.tsm.repos.SprintMasterRepository;
import com.stg.tsm.repos.TaskMasterRepository;
import com.stg.tsm.service.CalendarService;
import com.stg.tsm.utils.Dates;

/**
 * @author saikrishnan
 * @author jenifer
 */

@Service
public class CalendarServiceImpl implements CalendarService {

    @Autowired
    private TaskMasterRepository taskMasterRepository;

    @Autowired
    private SprintMasterRepository sprintMasterRepository;

    @SuppressWarnings("null")
    @Override
    public CalendarTotalNumofWorkPerMonth noOfHoursWorkedPerDayBySprintId(CalendarRequestDTO calendarRequestDTO)
            throws TsmException {

        SprintMaster sprintMaster = sprintMasterRepository.findById(calendarRequestDTO.getSprintId()).orElseThrow(
                () -> new TsmException("sprint master not found : " + calendarRequestDTO.getSprintId()));
        List<Tuple> object = null;
        if (sprintMaster != null) {
            object = taskMasterRepository.calendarDataBySprintId(calendarRequestDTO.getSprintId(),
                    calendarRequestDTO.getAssignmentId(),
                    sprintMaster.getStartDate().toString(), sprintMaster.getEndDate().toString());
        }

        List<CalendarResponseDTO> calendarResponseDTOs = new ArrayList<>();
        CalendarTotalNumofWorkPerMonth calendarTotalNumofWorkPerMonth = new CalendarTotalNumofWorkPerMonth();
        for (Tuple tuple : object) {
            CalendarResponseDTO calendarResponseDTO = new CalendarResponseDTO();
            calendarResponseDTO.setDate(tuple.get(0) != null ? (Date) tuple.get(0) : null);
            calendarResponseDTO.setUserstoryName(tuple.get(1) != null ? (String) tuple.get(1) : "");
            calendarResponseDTO.setTaskName(tuple.get(2) != null ? (String) tuple.get(2) : "");
            calendarResponseDTO.setNumOfhoursWorkedPerDay(toLocalTime(tuple.get(3) != null ? (Time) tuple.get(3) : null));
            calendarResponseDTO.setUserstoryId(tuple.get(4) != null ? (int) tuple.get(4) : null);
            calendarResponseDTOs.add(calendarResponseDTO);
        }

        int tragetDays = Dates.getWorkingDaysBetweenTwoDates(sprintMaster.getStartDate(), sprintMaster.getEndDate());

        List<LocalTime> localTimes = new ArrayList<>();
        for (CalendarResponseDTO calendarResponseDTO : calendarResponseDTOs) {
            localTimes.add(calendarResponseDTO.getNumOfhoursWorkedPerDay());
        }

        int sumHours = 0;
        int sumMinutes = 0;

        // Iterate the list and add all hours, minutes and seconds separately after
        // parsing the time strings using the corresponding formatter
        for (LocalTime strTime : localTimes) {
            sumHours += strTime.getHour();
            sumMinutes += strTime.getMinute();

        }

        // Adjust hour, minutes and seconds if minute and/or second exceed 60

        sumHours += sumMinutes / 60;
        sumMinutes %= 60;


        calendarTotalNumofWorkPerMonth.setTargetHoursWorkPerMonth(tragetDays * 8);
        calendarTotalNumofWorkPerMonth.setTotalHoursWorkedPerMonth(String.format("%02d", sumHours) + ":" + String.format("%02d", sumMinutes));
        calendarTotalNumofWorkPerMonth.setCalendarResponseDTOs(calendarResponseDTOs);
        calendarTotalNumofWorkPerMonth.setStartDate(sprintMaster.getStartDate());
        calendarTotalNumofWorkPerMonth.setEndDate(sprintMaster.getEndDate());
        return calendarTotalNumofWorkPerMonth;

    }

    @SuppressWarnings("null")
    @Override
    public CalendarTotalNumofWorkPerMonth noOfHoursWorkedPerDay(CalendarRequestDTO calendarRequestDTO) throws TsmException {

        int month = LocalDate.now().getMonthValue();

        int year = LocalDate.now().getYear();
        
        int endDay = LocalDate.now().lengthOfMonth();

        String startDate = year + "-" + month + "-" + "01";

        String endDate = year + "-" + month + "-" + endDay;

        List<Tuple> object = taskMasterRepository.calendarData(calendarRequestDTO.getAssignmentId(), startDate,
                endDate);

        List<CalendarResponseDTO> calendarResponseDTOs = new ArrayList<>();
        CalendarTotalNumofWorkPerMonth calendarTotalNumofWorkPerMonth = new CalendarTotalNumofWorkPerMonth();
        for (Tuple tuple : object) {
            CalendarResponseDTO calendarResponseDTO = new CalendarResponseDTO();
            calendarResponseDTO.setDate(tuple.get(0) != null ? (Date) tuple.get(0) : null);
            calendarResponseDTO.setUserstoryName(tuple.get(1) != null ? (String) tuple.get(1) : "");
            calendarResponseDTO.setTaskName(tuple.get(2) != null ? (String) tuple.get(2) : "");
            calendarResponseDTO.setNumOfhoursWorkedPerDay(toLocalTime(tuple.get(3) != null ? (Time) tuple.get(3) : null));
            calendarResponseDTO.setUserstoryId(tuple.get(4) != null ? (int) tuple.get(4) : null);

            calendarResponseDTOs.add(calendarResponseDTO);
        }

        Date date1 = null;
        Date date2 = null;
        try {
            date1 = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
            date2 = new SimpleDateFormat("yyyy-MM-dd").parse(endDate);
        } catch (ParseException e) {

            e.printStackTrace();
        }
        int targetDays = Dates.getWorkingDaysBetweenTwoDates(date1, date2);

        List<LocalTime> localTimes = new ArrayList<>();
        for (CalendarResponseDTO calendarResponseDTO : calendarResponseDTOs) {
            localTimes.add(calendarResponseDTO.getNumOfhoursWorkedPerDay());
        }

        int sumHours = 0;
        int sumMinutes = 0;
        // Iterate the list and add all hours, minutes and seconds separately after
        // parsing the time strings using the corresponding formatter
        for (LocalTime strTime : localTimes) {
            sumHours += strTime.getHour();
            sumMinutes += strTime.getMinute();
        }

        // Adjust hour, minutes and seconds if minute and/or second exceed 60

        sumHours += sumMinutes / 60;
        sumMinutes %= 60;


        calendarTotalNumofWorkPerMonth.setTargetHoursWorkPerMonth(targetDays * 8);
        calendarTotalNumofWorkPerMonth.setTotalHoursWorkedPerMonth(String.format("%02d", sumHours) + ":" + String.format("%02d", sumMinutes));
        calendarTotalNumofWorkPerMonth.setCalendarResponseDTOs(calendarResponseDTOs);
        calendarTotalNumofWorkPerMonth.setStartDate(date1);
        calendarTotalNumofWorkPerMonth.setEndDate(date2);

        return calendarTotalNumofWorkPerMonth;

    }

    public static LocalTime toLocalTime(java.sql.Time time) {
        return time.toLocalTime();
    }

}
