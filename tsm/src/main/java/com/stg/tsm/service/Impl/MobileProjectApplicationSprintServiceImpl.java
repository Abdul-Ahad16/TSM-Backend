package com.stg.tsm.service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stg.tsm.dto.CreateSprintMasterRequestDTO;
import com.stg.tsm.dto.CreateSprintMasterResponseDTO;
import com.stg.tsm.dto.MobileApplicationSprintResponseDTO;
import com.stg.tsm.dto.MobileProjectApplicationResponseDTO;
import com.stg.tsm.dto.MobileProjectApplicationSprintDTO;
import com.stg.tsm.dto.MobileSprintMasterDTO;
import com.stg.tsm.dto.MobileSprintMasterResponseDTO;
import com.stg.tsm.entity.ProjectApplicationDetail;
import com.stg.tsm.entity.ProjectAssignment;
import com.stg.tsm.entity.ProjectMaster;
import com.stg.tsm.entity.SprintMaster;
import com.stg.tsm.exception.MobileTsmException;
import com.stg.tsm.exception.TsmException;
import com.stg.tsm.service.MobileProjectApplicationSprintService;
import com.stg.tsm.service.ProjectApplicationDetailService;
import com.stg.tsm.repos.ProjectApplicationDetailRepository;
import com.stg.tsm.repos.ProjectAssignmentRepository;
import com.stg.tsm.repos.ProjectMasterRepository;
import com.stg.tsm.repos.SprintMasterRepository;

@Service
public class MobileProjectApplicationSprintServiceImpl implements MobileProjectApplicationSprintService {

	@Autowired(required = true)
	private SprintMasterRepository sprintMasterRepository;

	@Autowired
	private ProjectMasterRepository projectMasterRepository;

	@Autowired
	private ProjectApplicationDetailRepository projectApplicationDetailRepository;

	@Autowired
	private ProjectAssignmentRepository ProjectAssignmentRepository;

	@Autowired
	private ProjectApplicationDetailService projectApplicationDetailService;

	@Override
	public MobileProjectApplicationSprintDTO getProjectApplicationSprint(int emplyoeeId) throws MobileTsmException {
		// Response
		MobileProjectApplicationSprintDTO mobileProjectApplicationSprint = new MobileProjectApplicationSprintDTO();

		List<ProjectAssignment> assignmentsList = ProjectAssignmentRepository.findAllByEmployeeId(emplyoeeId);
		if(assignmentsList.isEmpty()) {
			throw new MobileTsmException(null, "Project and Application not found");
		}
		List<MobileProjectApplicationResponseDTO> projectResponse = new ArrayList<MobileProjectApplicationResponseDTO>();
		// Iterate over assignmentsList where we get list of applications of employee
		for (ProjectAssignment projectAssignment : assignmentsList) {
			ProjectApplicationDetail applicationDetails = projectAssignment.getProjectApplicationDetail();
			if(applicationDetails == null) {
				throw new MobileTsmException(null, "Application not found");
			}
			// Setting project first
			MobileProjectApplicationResponseDTO project = new MobileProjectApplicationResponseDTO();
			project.setProjectId(applicationDetails.getProjectId());
			project.setProjectName(applicationDetails.getProjectName());
			project.setProjectNickName(applicationDetails.getProjectNickName());

			// Setting Application second
			List<MobileApplicationSprintResponseDTO> listOfApplications = new ArrayList<MobileApplicationSprintResponseDTO>();
			MobileApplicationSprintResponseDTO application = new MobileApplicationSprintResponseDTO();
			application.setApplicationId(applicationDetails.getApplicationId());
			application.setApplicationName(applicationDetails.getApplicationName());
			application.setApplicationNickName(applicationDetails.getApplicationNickName());

			List<SprintMaster> sprintMasters = applicationDetails.getSprintMasters();
			List<MobileSprintMasterDTO> updatesSprintMasters = new ArrayList<MobileSprintMasterDTO>();
			for (SprintMaster sprintMaster : sprintMasters) {
				MobileSprintMasterDTO sprint = new MobileSprintMasterDTO();
				sprint.setSprintId(sprintMaster.getSprintId());
				sprint.setSprintName(sprintMaster.getSprintName());
				sprint.setSprintDescription(sprintMaster.getSprintDescription());
				sprint.setStartDate(sprintMaster.getStartDate());
				sprint.setCreatedDate(sprintMaster.getCreatedDate());
				sprint.setEndDate(sprintMaster.getEndDate());
				updatesSprintMasters.add(sprint);
			}
			application.setSprints(updatesSprintMasters);
			listOfApplications.add(application);
			project.setApplicationSprintResponseDTOs(listOfApplications);
			projectResponse.add(project);
		}
		mobileProjectApplicationSprint.setEmployeeId(emplyoeeId);
		mobileProjectApplicationSprint.setData(projectResponse);
		mobileProjectApplicationSprint.setMessage("Successful");
		mobileProjectApplicationSprint.setStatus(200);
		return mobileProjectApplicationSprint;
	}

	@Override
	public MobileSprintMasterResponseDTO getSprints(int applicationId) throws MobileTsmException {
		MobileSprintMasterResponseDTO response = new MobileSprintMasterResponseDTO();
		List<MobileSprintMasterDTO> listOfSprints = new ArrayList<MobileSprintMasterDTO>();
		List<SprintMaster> sprintMasters;
		try {
			sprintMasters = projectApplicationDetailService.readSprintMastersById(applicationId);
		} catch (TsmException e) {
			throw new MobileTsmException(null, "Sprint not exist!");
		}
		if (sprintMasters.isEmpty()) {
			throw new MobileTsmException(null, "Sprint not exist!");
		}
		for (SprintMaster sprintMaster : sprintMasters) {
			MobileSprintMasterDTO sprint = new MobileSprintMasterDTO();
			sprint.setSprintId(sprintMaster.getSprintId());
			sprint.setSprintName(sprintMaster.getSprintName());
			sprint.setSprintDescription(sprintMaster.getSprintDescription());
			sprint.setCreatedBy(sprintMaster.getCreatedBy());
			sprint.setStartDate(sprintMaster.getStartDate());
			sprint.setCreatedDate(sprintMaster.getCreatedDate());
			sprint.setEndDate(sprintMaster.getEndDate());
			listOfSprints.add(sprint);
		}
		response.setData(listOfSprints);
		response.setMessage("Successful");
		response.setStatus(200);
		return response;
	}

	@Override
	public CreateSprintMasterResponseDTO createSprint(CreateSprintMasterRequestDTO createSprintMasterRequestDTO)
			throws MobileTsmException {
		ProjectMaster projectMaster = projectMasterRepository.findById(createSprintMasterRequestDTO.getProjectId())
				.orElseThrow(() -> new MobileTsmException(null, "Project Master is not Found"));

		ProjectApplicationDetail projectApplicationDetail = projectApplicationDetailRepository
				.findById(createSprintMasterRequestDTO.getApplicationId())
				.orElseThrow(() -> new MobileTsmException(null, "Project Application  is not Found"));

		CreateSprintMasterResponseDTO responseSprint = new CreateSprintMasterResponseDTO();
		SprintMaster newSprint = new SprintMaster();

		if (projectMaster != null && projectApplicationDetail != null) {
			List<SprintMaster> sprintList = sprintMasterRepository.findAllByApplicationId(createSprintMasterRequestDTO.getApplicationId());
			if (!sprintList.isEmpty()) {
				for (SprintMaster sprintMaster : sprintList) {
					String getName = sprintMaster.getSprintName();
					String givenName = createSprintMasterRequestDTO.getSprintName();
					if (givenName.equalsIgnoreCase(getName)) {
						throw new MobileTsmException(null, "sprint name already exist");
					}
				}
			}
			newSprint.setSprintName(createSprintMasterRequestDTO.getSprintName());
			newSprint.setSprintDescription(createSprintMasterRequestDTO.getSprintDescription());
			newSprint.setStartDate(createSprintMasterRequestDTO.getStartDate());
			newSprint.setEndDate(createSprintMasterRequestDTO.getEndDate());
			newSprint.setCreatedDate(createSprintMasterRequestDTO.getCreatedDate());
			newSprint.setCreatedBy(createSprintMasterRequestDTO.getCreatedBy());
			newSprint.setProjectMaster(projectMaster);
			newSprint.setProjectApplicationDetail(projectApplicationDetail);
			try {
				SprintMaster returnSprint = sprintMasterRepository.save(newSprint);
				MobileSprintMasterDTO response = new MobileSprintMasterDTO();
				response.setSprintId(returnSprint.getSprintId());
				response.setSprintName(returnSprint.getSprintName());
				response.setSprintDescription(returnSprint.getSprintDescription());
				response.setStartDate(returnSprint.getStartDate());
				response.setEndDate(returnSprint.getEndDate());
				response.setCreatedDate(returnSprint.getCreatedDate());
				response.setCreatedBy(returnSprint.getCreatedBy());
				responseSprint.setData(response);
				responseSprint.setMessage("Successful");
				responseSprint.setStatus(200);
				return responseSprint;
			} catch (Exception e) {
				throw new MobileTsmException(null, "Sprint is not Created");
			}
		} else {
			throw new MobileTsmException(null, "Given Correct Details");
		}
	}

}
