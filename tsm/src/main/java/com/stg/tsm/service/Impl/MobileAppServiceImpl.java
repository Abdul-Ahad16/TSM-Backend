package com.stg.tsm.service.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.stg.tsm.dto.CalendarRequestDTO;
import com.stg.tsm.dto.CalendarTotalNumofWorkPerMonth;
import com.stg.tsm.dto.CreateUserStoryResponseDTO;
import com.stg.tsm.dto.EmployeeProfileMobileDTO;
import com.stg.tsm.dto.EmployeeRoleDTO;
import com.stg.tsm.dto.MobileApplicationResponseDTO;
import com.stg.tsm.dto.MobileAuthenticationResponseDTO;
import com.stg.tsm.dto.MobileDashboardData;
import com.stg.tsm.dto.MobileEmployeeResponse;
import com.stg.tsm.dto.MobileProjectResponseDTO;
import com.stg.tsm.dto.MobileUserStoryResopnseDto;
import com.stg.tsm.dto.ResetPasswordDTO;
import com.stg.tsm.dto.UserstoryCreateDTO;
import com.stg.tsm.dto.UserstoryCreateMobileDTO;
import com.stg.tsm.entity.EmployeeDetail;
import com.stg.tsm.entity.ProjectApplicationDetail;
import com.stg.tsm.entity.ProjectAssignment;
import com.stg.tsm.entity.RoleMaster;
import com.stg.tsm.entity.TaskMaster;
import com.stg.tsm.entity.TaskMasterPK;
import com.stg.tsm.entity.UserRole;
import com.stg.tsm.entity.UserstoryMaster;
import com.stg.tsm.entity.SprintMaster;
import com.stg.tsm.entity.UserstoryMaster;
import com.stg.tsm.exception.MobileTsmException;
import com.stg.tsm.exception.TsmException;
import com.stg.tsm.repos.EmployeeDetailRepository;
import com.stg.tsm.repos.ProjectAssignmentRepository;
import com.stg.tsm.repos.RoleMasterRepository;
import com.stg.tsm.repos.SprintMasterRepository;
import com.stg.tsm.repos.UserstoryMasterRepository;
import com.stg.tsm.security.CustomUserDetailsService;
import com.stg.tsm.repos.TaskMasterRepository;
import com.stg.tsm.repos.UserRoleRepository;
import com.stg.tsm.repos.UserstoryMasterRepository;
import com.stg.tsm.service.MailSender;
import com.stg.tsm.service.MobileAppService;
import com.stg.tsm.service.ProjectMasterService;
import com.stg.tsm.service.TaskMasterService;

@Service
public class MobileAppServiceImpl implements MobileAppService {

	@Autowired
	private ProjectMasterService projectMasterService;

	@Autowired
	private CalendarServiceImpl calendarServiceImpl;

	@Autowired
	private EmployeeDetailRepository employeeDetailRepository;
	
	@Autowired
	private UserstoryMasterRepository  userstoryMasterRepository;
	
	@Autowired
	private ProjectAssignmentImpl projectAssignmentImpl;
	
	@Autowired
	private ProjectAssignmentRepository projectAssignmentRepository;
	
	@Autowired
	private TaskMasterRepository taskMasterRepository;
	
	@Autowired
	private UserRoleRepository userRoleRepository;
	
	@Autowired
	private TaskMasterService taskMasterService;
	
	@Autowired
	private SprintMasterRepository sprintMasterRepository;

	@Autowired
	private CustomUserDetailsService customUserDetails;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private MailSender mailSender;



	@Override
	public MobileAuthenticationResponseDTO getEmployeeData(EmployeeDetail employeeDetailTemp) throws TsmException {
		MobileEmployeeResponse employeeResponseDto = this.getEmployeeData(employeeDetailTemp.getUsername());

		employeeResponseDto.getProjectAssignments().listIterator().forEachRemaining(t -> t.setTaskMasters(null));

		List<ProjectApplicationDetail> applicationDetails = projectMasterService
				.getAllProjectByEmpiD(employeeResponseDto.getEmployeeId());

		List<MobileProjectResponseDTO> mobileProjectResponseDTOs = new ArrayList<>();

		List<Integer> projectIds = new ArrayList<>();

		for (ProjectApplicationDetail projectApplicationDetail : applicationDetails) {
			if (!projectIds.contains(projectApplicationDetail.getProjectId())) {
				projectIds.add(projectApplicationDetail.getProjectId());
				MobileProjectResponseDTO mobileProjectResponseDTO = new MobileProjectResponseDTO();
				mobileProjectResponseDTO.setProjectId(projectApplicationDetail.getProjectId());
				mobileProjectResponseDTO.setProjectName(projectApplicationDetail.getProjectName());
				mobileProjectResponseDTO.setProjectNickName(projectApplicationDetail.getProjectNickName());

				mobileProjectResponseDTO.setApplicationResponseDTOs(
						this.applicationResponseDTOs(applicationDetails, projectApplicationDetail.getProjectId()));

				mobileProjectResponseDTOs.add(mobileProjectResponseDTO);
			}
		}

		CalendarRequestDTO calendarRequestDTO = new CalendarRequestDTO();

		calendarRequestDTO.setAssignmentId(employeeResponseDto.getProjectAssignments().get(0).getAssignmentId());

		CalendarTotalNumofWorkPerMonth calendarTotalNumofWorkPerMonth = calendarServiceImpl
				.noOfHoursWorkedPerDay(calendarRequestDTO);

		MobileAuthenticationResponseDTO authenticationResponseDTO = new MobileAuthenticationResponseDTO();

		MobileDashboardData dashboardData = new MobileDashboardData();

		dashboardData.setTargetHoursCurrentMonth(calendarTotalNumofWorkPerMonth.getTargetHoursWorkPerMonth());

		dashboardData.setTotalHoursWorkedCurrentMonth(calendarTotalNumofWorkPerMonth.getTotalHoursWorkedPerMonth());

		authenticationResponseDTO.setDashboard(dashboardData);

		authenticationResponseDTO.setEmployeeData(employeeResponseDto);

		authenticationResponseDTO.setProjectData(mobileProjectResponseDTOs);

		return authenticationResponseDTO;
	}

	public MobileEmployeeResponse getEmployeeData(String username) {

		EmployeeDetail employeeDetail = employeeDetailRepository.findByUsername(username);
		if (employeeDetail != null) {

			List<EmployeeRoleDTO> roleFunctions = employeeDetailRepository
					.getEmpRoleFunctions(employeeDetail.getEmployeeId());
			MobileEmployeeResponse employeeResponseDto = new MobileEmployeeResponse();

			employeeResponseDto.setEmployeeId(employeeDetail.getEmployeeId());
			employeeResponseDto.setEmployeeName(employeeDetail.getEmployeeName());
			employeeResponseDto.setUsername(employeeDetail.getUsername());
			employeeResponseDto.setEmail(employeeDetail.getEmail());
			employeeResponseDto.setProjectAssignments(employeeDetail.getProjectAssignments());

			List<String> temp = new ArrayList<>();
			if (roleFunctions != null) {
				roleFunctions.forEach(each -> {
					employeeResponseDto.setEmpRole(each.getRoleName());
					temp.add(each.getDescription());
				});
				employeeResponseDto.setEmpFunctions(temp);
			}
			return employeeResponseDto;
		} else {
			throw new BadCredentialsException("Invalid username");
		}
	}

	public List<MobileApplicationResponseDTO> applicationResponseDTOs(
			List<ProjectApplicationDetail> projectApplicationDetail, int projectId) {
		List<MobileApplicationResponseDTO> applicationResponseDTOList = new ArrayList<>();

		for (ProjectApplicationDetail projectApplication : projectApplicationDetail) {
			if (projectApplication.getProjectId() == projectId) {
				MobileApplicationResponseDTO applicationResponseDTO = new MobileApplicationResponseDTO();
				applicationResponseDTO.setApplicationId(projectApplication.getApplicationId());
				applicationResponseDTO.setApplicationName(projectApplication.getApplicationName());
				applicationResponseDTO.setApplicationNickName(projectApplication.getApplicationNickName());
				applicationResponseDTO.setSprints(projectApplication.getSprintMasters());
				applicationResponseDTOList.add(applicationResponseDTO);
			}
		}
		return applicationResponseDTOList;
	}

	@Override
	public TaskMaster createTasks(int employeeId, int projectApplicationId, int userstoryId, TaskMaster taskMaster)
			throws TsmException {
	     
	        
	        int ProjectAssignmentId = projectAssignmentImpl.projectAssignmentByProjectAndEmployeeId(projectApplicationId, employeeId);
	        
	        TaskMasterPK masterPK = new TaskMasterPK();
	        
	        masterPK.setAssignmentId(ProjectAssignmentId);
	        masterPK.setUserStoryId(userstoryId);
	        taskMaster.setId(masterPK);
	        
	        return this.taskMasterService.createTask(userstoryId, ProjectAssignmentId, taskMaster);
	}

	@Override
	public EmployeeProfileMobileDTO getEmployeeProfile(int employeeId)throws MobileTsmException {
		
		
		EmployeeDetail employeeDetail= employeeDetailRepository.findById(employeeId)
				.orElseThrow(() -> new MobileTsmException(null,"Employee Id not Found"));
				EmployeeProfileMobileDTO employeeProfileMobileDTO = new EmployeeProfileMobileDTO();
				
				employeeProfileMobileDTO.setEmpName(employeeDetail.getEmployeeName());
				employeeProfileMobileDTO.setEmailId(employeeDetail.getEmail());
				
				employeeProfileMobileDTO.setRoleName(userRoleRepository.findByEmpId(employeeId).getRoleMaster().getRoleName());
				return employeeProfileMobileDTO;
	}
	
	
	@Override
	public String changePassword(ResetPasswordDTO resetPasswordDTO) throws TsmException {
		EmployeeDetail oldEmployee = this.employeeDetailRepository.findByEmail(resetPasswordDTO.getEmployeeEmail());
		if (oldEmployee != null) {

			EmployeeDetail employeeDetail = new EmployeeDetail();

			if (passwordEncoder.matches(resetPasswordDTO.getOldPassword(), oldEmployee.getPassword())) {

				employeeDetail.setCreatedBy(oldEmployee.getCreatedBy());
				employeeDetail.setCreatedDate(oldEmployee.getCreatedDate());
				employeeDetail.setEmployeeName(oldEmployee.getEmployeeName());
				employeeDetail.setUsername(oldEmployee.getUsername());
				employeeDetail
						.setPassword(customUserDetails.passwordEncoder().encode(resetPasswordDTO.getNewPassword()));
				employeeDetail.setEmail(oldEmployee.getEmail());
				employeeDetail.setJoiningDate(oldEmployee.getJoiningDate());
				employeeDetail.setRelievingDate(oldEmployee.getRelievingDate());
				employeeDetail.setUpdatedBy(resetPasswordDTO.getAdminName());
				employeeDetail.setUpdatedDate(oldEmployee.getUpdatedDate());
				employeeDetail.setEmployeeId(oldEmployee.getEmployeeId());
				EmployeeDetail employeeDetailsUpdated = employeeDetailRepository.saveAndFlush(employeeDetail);

				mailSender.sendEmailUserPasswordChanged(resetPasswordDTO.getEmployeeEmail(),
						oldEmployee.getEmployeeName());
				return "Password Changed Sucessfully";
			} else {
				throw new TsmException("Password not matched please give Correct old password");
			}
		} else {
			throw new TsmException("Employee Email address dose not exist");
		}
	}

	@Override
	public CreateUserStoryResponseDTO createUserstoryMaster(int sprintId, UserstoryCreateDTO userstoryMasterDTO)
			throws MobileTsmException {
		SprintMaster sprintMaster = sprintMasterRepository.findById(sprintId)
				.orElseThrow(() -> new MobileTsmException(null, "Sprint not Found with given id : " + sprintId));
		List<UserstoryMaster> userstoryListTemp = sprintMaster.getUserstoryMasters();

		CreateUserStoryResponseDTO response = new CreateUserStoryResponseDTO();

		UserstoryMaster userstoryMaster = new UserstoryMaster();
		for (UserstoryMaster userstoryMasterTemp : userstoryListTemp) {
			if (userstoryMasterTemp.getUserstoryName().equals(userstoryMasterDTO.getUserstoryName())) {
				throw new MobileTsmException(null, "Userstory is already exist");
			}
		}
		userstoryMaster.setSprintMaster(sprintMaster);
		userstoryMaster.setCreatedBy(userstoryMasterDTO.getCreatedBy());
		userstoryMaster.setCreatedDate(userstoryMasterDTO.getCreatedDate());
		userstoryMaster.setEstimatedStorypoints(userstoryMasterDTO.getEstimatedStorypoints());
		userstoryMaster.setUserstoryDescription(userstoryMasterDTO.getUserstoryDescription());
		userstoryMaster.setPlannedEfforts(userstoryMasterDTO.getPlannedEfforts());
		userstoryMaster.setUserstoryName(userstoryMasterDTO.getUserstoryName());
		try {
			UserstoryMaster userStory = userstoryMasterRepository.save(userstoryMaster);
			UserstoryCreateDTO responseUserStory = new UserstoryCreateDTO();

			responseUserStory.setUserstoryId(userStory.getUserstoryId());
			responseUserStory.setUserstoryName(userStory.getUserstoryName());
			responseUserStory.setUserstoryDescription(userStory.getUserstoryDescription());
			responseUserStory.setSprintId(userStory.getSprintMaster().getSprintId());
			responseUserStory.setPlannedEfforts(userStory.getPlannedEfforts());
			responseUserStory.setEstimatedStorypoints(userStory.getEstimatedStorypoints());
			responseUserStory.setCreatedBy(userStory.getCreatedBy());
			responseUserStory.setCreatedDate(userStory.getCreatedDate());
			responseUserStory.setUpdatedBy(userStory.getUpdatedBy());
			responseUserStory.setUpdatedDate(userStory.getUpdatedDate());

			response.setData(responseUserStory);
			response.setMessage("Successful");
			response.setStatus(200);

			return response;
		} catch (Exception e) {
			throw new MobileTsmException(null, "Userstory is not Created");
		}

	}

	@Override
	public MobileUserStoryResopnseDto getUserstoryMasterList(int sprintId) throws MobileTsmException {

		SprintMaster sprintMasterTemp = sprintMasterRepository.findById(sprintId)
				.orElseThrow(() -> new MobileTsmException(null, "Sprint not Found with given id : " + sprintId));
		List<UserstoryMaster> userstoryListTemp = sprintMasterTemp.getUserstoryMasters();

		MobileUserStoryResopnseDto responseDto = new MobileUserStoryResopnseDto();
		List<UserstoryCreateMobileDTO> listOfUserStories = new ArrayList<UserstoryCreateMobileDTO>();
		if (userstoryListTemp.isEmpty()) {
			responseDto.setData(null);
			responseDto.setMessagae("No userstoryList Found");
			return responseDto;

		} else {
			for (UserstoryMaster userstoryMaster : userstoryListTemp) {
				UserstoryCreateMobileDTO createDTO = new UserstoryCreateMobileDTO();

				createDTO.setUserstoryId(userstoryMaster.getUserstoryId());
				createDTO.setCreatedBy(userstoryMaster.getCreatedBy());
				createDTO.setCreatedDate(userstoryMaster.getCreatedDate());
				createDTO.setEstimatedStorypoints(userstoryMaster.getEstimatedStorypoints());
				createDTO.setPlannedEfforts(userstoryMaster.getPlannedEfforts());
				createDTO.setUpdatedBy(userstoryMaster.getUpdatedBy());
				createDTO.setUpdatedDate(userstoryMaster.getUpdatedDate());
				createDTO.setUserstoryDescription(userstoryMaster.getUserstoryDescription());
				createDTO.setUserstoryName(userstoryMaster.getUserstoryName());

				listOfUserStories.add(createDTO);
			}

			responseDto.setData(listOfUserStories);
			responseDto.setMessagae("Successful");
			responseDto.setStatus(200);
			return responseDto;
		}

	}

	@Override
	public String updateUserstoryMaster(UserstoryMaster userstoryMaster) throws TsmException {
		UserstoryMaster userstoryMasterTemp = userstoryMasterRepository.findById(userstoryMaster.getUserstoryId())
				.get();
		if (userstoryMasterTemp != null) {
			userstoryMasterRepository.save(userstoryMaster);
		} else {
			throw new TsmException("No UserstoryMaster found to update");
		}
		return "UserstoryMaster Updated Successfully";

	}

}
