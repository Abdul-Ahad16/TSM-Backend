package com.stg.tsm.service.Impl;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stg.tsm.dto.CalendarRequestDTO;
import com.stg.tsm.dto.CalendarTotalNumofWorkPerMonth;
import com.stg.tsm.dto.MobileTaskEffortsRequestDTO;
import com.stg.tsm.dto.MobileTaskMasterEffortsDTO;
import com.stg.tsm.dto.MobileTaskMasterResponseDTO;
import com.stg.tsm.dto.EffortsRecordDTO;
import com.stg.tsm.dto.TargetedHoursWithUserStoriesDto;
import com.stg.tsm.dto.TaskMasterDTO;
import com.stg.tsm.entity.ProjectMaster;
import com.stg.tsm.entity.SprintMaster;
import com.stg.tsm.entity.TaskMaster;
import com.stg.tsm.entity.UserstoryMaster;
import com.stg.tsm.exception.MobileTsmException;
import com.stg.tsm.repos.ProjectMasterRepository;
import com.stg.tsm.repos.SprintMasterRepository;
import com.stg.tsm.repos.TaskMasterRepository;
import com.stg.tsm.repos.UserstoryMasterRepository;
import com.stg.tsm.service.CalendarService;
import com.stg.tsm.service.ProjectAssignmentService;
import com.stg.tsm.service.TargetedHoursWithUserStoriesService;
import com.stg.tsm.utils.Dates;

@Service
public class TargetedHoursWithUserStoriesServiceImpl implements TargetedHoursWithUserStoriesService {

	@Autowired
	private SprintMasterRepository sprintMasterRepository;

	@Autowired
	private UserstoryMasterRepository userstoryMasterRepository;

	@Autowired
	private ProjectMasterRepository projectMasterRepository;

	@Autowired
	private ProjectAssignmentService projectAssignmentService;

	@Autowired
	private TaskMasterRepository taskMasterRepository;

	@Autowired
	private CalendarService calendarService;

	@Override
	public MobileTaskMasterResponseDTO getUserStoriesEfforts(MobileTaskEffortsRequestDTO userStoriesDto)
			throws MobileTsmException {

		MobileTaskMasterResponseDTO responseDTO = new MobileTaskMasterResponseDTO();

		TargetedHoursWithUserStoriesDto dto = new TargetedHoursWithUserStoriesDto();

		ProjectMaster projectMaster = projectMasterRepository.findById(userStoriesDto.getProjectId())
				.orElseThrow(() -> new MobileTsmException(null, "Project not found"));

		int projectAssignmentId = projectAssignmentService.projectAssignmentByProjectAndEmployeeId(
				userStoriesDto.getApplicationId(), userStoriesDto.getEmployeeId());

		CalendarRequestDTO calendarRequestDTO = new CalendarRequestDTO();
		calendarRequestDTO.setAssignmentId(projectAssignmentId);
		calendarRequestDTO.setEmployeId(userStoriesDto.getEmployeeId());
		calendarRequestDTO.setProjectId(userStoriesDto.getProjectId());
		calendarRequestDTO.setSprintId(userStoriesDto.getSprintId());
		calendarRequestDTO.setTaskmasterId(projectAssignmentId);
		CalendarTotalNumofWorkPerMonth response = calendarService.noOfHoursWorkedPerDay(calendarRequestDTO);
		dto.setTargetHoursWorkPerMonth(response.getTargetHoursWorkPerMonth());
		dto.setTotalHoursWorkedPerMonth(response.getTotalHoursWorkedPerMonth());

		SprintMaster sprintMaster = sprintMasterRepository.findById(userStoriesDto.getSprintId()).get();
		if(sprintMaster == null) {
			responseDTO.setData(dto);
			responseDTO.setMessage("sprints not found");
			responseDTO.setStatus(206);
		}
				//.orElseThrow(() -> new MobileTsmException(null, "Sprint not Found"));
		// need to modify

		int sprintDays = Dates.getWorkingDaysBetweenTwoDates(sprintMaster.getStartDate(), sprintMaster.getEndDate());
		dto.setSprintTargetedWorkingHours(sprintDays * 8);

		List<UserstoryMaster> listOfUserstoryMasters = null;
		listOfUserstoryMasters = userstoryMasterRepository.findAllBySprintId(userStoriesDto.getSprintId());
		if (listOfUserstoryMasters.isEmpty()) {
			//throw new MobileTsmException(null, "Task are not exist!");
			dto.setTotalSprintUserStoriesEfforts("00:00");
			responseDTO.setData(dto);
			responseDTO.setMessage("user stories not found");
			responseDTO.setStatus(206);
			return responseDTO;
		} // This is has to remove

		List<LocalTime> localTimesOfAll = new ArrayList<>();

		List<TaskMaster> listOfTaskMasterAll = null;

		listOfTaskMasterAll = taskMasterRepository.findAllByProjectAssignmnetId(projectAssignmentId);
		if (listOfTaskMasterAll == null || listOfTaskMasterAll.isEmpty()) {
			//throw new MobileTsmException(null, "Task are not exist!");
			dto.setTotalSprintUserStoriesEfforts("00:00");
			MobileTaskMasterEffortsDTO mobileTaskMasterEffortsDTO = new MobileTaskMasterEffortsDTO();
			mobileTaskMasterEffortsDTO.setOnDatetotalWokrHours("00:00");
			mobileTaskMasterEffortsDTO.setEffortsRecord(null);
			dto.setTaskMasterEfforts(mobileTaskMasterEffortsDTO);
			responseDTO.setData(dto);
			responseDTO.setMessage("efforts not found");
			responseDTO.setStatus(206);
			return responseDTO;
		} // This is has to remove

		List<TaskMaster> filteredListOfTaskMaster = new ArrayList<TaskMaster>();
		
		//This is for taskmaster list
		//List<TaskMaster> listOfTaskMasters = new ArrayList<TaskMaster>();
		
		List<EffortsRecordDTO> listOfEfforts = new ArrayList<EffortsRecordDTO>();
		for(UserstoryMaster userMasterToFilter : listOfUserstoryMasters) {
			
			EffortsRecordDTO effortsRecord = new EffortsRecordDTO();// this has to move above for loop
			List<TaskMaster> listOfTaskMasters = new ArrayList<TaskMaster>();
			
			for (TaskMaster filterTasMas : listOfTaskMasterAll) {
				
				if (filterTasMas.getUserstoryMaster().getUserstoryId() == userMasterToFilter.getUserstoryId()) {
					filteredListOfTaskMaster.add(filterTasMas);
					localTimesOfAll.add(filterTasMas.getEfforts());
					listOfTaskMasters.add(filterTasMas);
					effortsRecord.setUserstoryName(userMasterToFilter.getUserstoryName());
				}
			}
			if(!listOfTaskMasters.isEmpty()) {
				effortsRecord.setTaskMasters(listOfTaskMasters);
				listOfEfforts.add(effortsRecord);	
			}
		}

		if (userStoriesDto.getDate() != null) {
			MobileTaskMasterEffortsDTO effortsRecordDTO = TargetedHoursWithUserStoriesServiceImpl
					.onDateTotalWorkingHours(filteredListOfTaskMaster, listOfUserstoryMasters,
							userStoriesDto.getDate());
			dto.setTaskMasterEfforts(effortsRecordDTO);
		} else {
			MobileTaskMasterEffortsDTO effortsRecordDTO = new MobileTaskMasterEffortsDTO();
			effortsRecordDTO.setEffortsRecord(listOfEfforts);
			dto.setTaskMasterEfforts(effortsRecordDTO);
		}

		if (!localTimesOfAll.isEmpty()) {
			dto.setTotalSprintUserStoriesEfforts(TargetedHoursWithUserStoriesServiceImpl.getEfforts(localTimesOfAll));
		} else {// This is has to remove
			//throw new MobileTsmException(null, "User story yet to add!");
			dto.setTotalSprintUserStoriesEfforts("00:00");
			responseDTO.setData(dto);
			responseDTO.setMessage("efforts not found");
			responseDTO.setStatus(206);
			return responseDTO;
		}
		responseDTO.setData(dto);
		responseDTO.setMessage("Successful");
		responseDTO.setStatus(200);
		return responseDTO;
	}

	public static MobileTaskMasterEffortsDTO onDateTotalWorkingHours(List<TaskMaster> taskMasters,
			List<UserstoryMaster> listOfUserstoryMasters, Date date) throws MobileTsmException {
		
		//List<TaskMaster> listOfEfforts = new ArrayList<TaskMaster>();
		List<EffortsRecordDTO> listOfEfforts = new ArrayList<EffortsRecordDTO>();
		List<LocalTime> localTimesOfMatched = new ArrayList<>();
		
		MobileTaskMasterEffortsDTO mobileTaskMasterEffortsDTO = new MobileTaskMasterEffortsDTO();
		
		for (UserstoryMaster userstoryMaster : listOfUserstoryMasters) {
			
			EffortsRecordDTO effortsRecord = new EffortsRecordDTO();// this has to move above for loop
			List<TaskMaster> listOfTaskMasters = new ArrayList<TaskMaster>();
			
			for (TaskMaster master : taskMasters) {
				Date givenDate = date;
				SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
				String givenStrDate = formatter.format(givenDate);

				Date fetchedDate = master.getDate();
				SimpleDateFormat formatter2 = new SimpleDateFormat("MM/dd/yyyy");
				String fetchedStrDate = formatter2.format(fetchedDate);
				if (userstoryMaster.getUserstoryId() == master.getUserstoryMaster().getUserstoryId()) {
					if (givenStrDate.equals(fetchedStrDate)) {
						localTimesOfMatched.add(master.getEfforts());
						listOfTaskMasters.add(master);
						effortsRecord.setUserstoryName(userstoryMaster.getUserstoryName());
					}
				}
				
			}
			if(!listOfTaskMasters.isEmpty()) {
				effortsRecord.setTaskMasters(listOfTaskMasters);
				listOfEfforts.add(effortsRecord);
			}
		}
		
		if (!localTimesOfMatched.isEmpty()) {
			mobileTaskMasterEffortsDTO.setEffortsRecord(listOfEfforts);
			mobileTaskMasterEffortsDTO
					.setOnDatetotalWokrHours(TargetedHoursWithUserStoriesServiceImpl.getEfforts(localTimesOfMatched));
		} else {
			throw new MobileTsmException(null, "No efforts for given date!");
		}

		return mobileTaskMasterEffortsDTO;
	}

	public static String getEfforts(List<LocalTime> localTimes) {
		int sumHours = 0;
		int sumMinutes = 0;
		for (LocalTime strTime : localTimes) {
			sumHours += strTime.getHour();
			sumMinutes += strTime.getMinute();
		}
		sumHours += sumMinutes / 60;
		sumMinutes %= 60;
		return String.format("%02d", sumHours) + ":" + String.format("%02d", sumMinutes);
	}

}
