package com.stg.tsm.service.Impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Tuple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stg.tsm.dto.ApplicationAllResponseDTO;
import com.stg.tsm.dto.ProjectAllResponseDTO;
import com.stg.tsm.dto.ProjectMasterServiceDto;
import com.stg.tsm.dto.ProjectMaterRequestDto;
import com.stg.tsm.entity.ProjectApplicationDetail;
import com.stg.tsm.entity.ProjectMaster;
import com.stg.tsm.exception.TsmException;
import com.stg.tsm.repos.ProjectApplicationDetailRepository;
import com.stg.tsm.repos.ProjectAssignmentRepository;
import com.stg.tsm.repos.ProjectMasterRepository;
import com.stg.tsm.service.ProjectMasterService;

/**
 * 
 * @author saikrishnan
 * @author jenifer
 *
 */

@Service
public class ProjectMasterServiceImpl implements ProjectMasterService {

	@Autowired
	private ProjectMasterRepository projectMasterRepository;

	@Autowired
	private ProjectApplicationDetailRepository projectApplicationDetailRepository;

	@SuppressWarnings("null")
	@Override
	public List<ProjectApplicationDetail> readAll(ProjectMaterRequestDto projectMaterRequestDto) throws TsmException {

		List<Tuple> tuples = projectMasterRepository.findAllByEmployeeId(projectMaterRequestDto.getEmployeeId());

		List<ProjectMasterServiceDto> projectMasterServiceDtos = new ArrayList<>();

		for (Tuple tuple : tuples) {
			ProjectMasterServiceDto projectMasterServiceDto = new ProjectMasterServiceDto();
			projectMasterServiceDto.setProjectId(tuple.get(0) != null ? (int) tuple.get(0) : null);
			projectMasterServiceDto.setApplicationId(tuple.get(1) != null ? (int) tuple.get(1) : null);
			projectMasterServiceDto.setAssignmnetId(tuple.get(2) != null ? (int) tuple.get(2) : null);
			projectMasterServiceDtos.add(projectMasterServiceDto);
		}

		List<ProjectApplicationDetail> projectMasters = new ArrayList<>();
		for (ProjectMasterServiceDto projectMasterServiceDto : projectMasterServiceDtos) {
			ProjectApplicationDetail masters = projectApplicationDetailRepository
					.findById(projectMasterServiceDto.getApplicationId())
					.orElseThrow(() -> new TsmException("project is not found"));
			projectMasters.add(masters);
		}
		return projectMasters;
	}

	@SuppressWarnings("null")
	public List<ProjectAllResponseDTO> readAllProject() throws TsmException {
		List<ProjectMaster> projectMasters = projectMasterRepository.findAll();

		List<ProjectAllResponseDTO> projectAllResponseDTOs = new ArrayList<>();

		for (ProjectMaster projectMaster : projectMasters) {
			ProjectAllResponseDTO projectAllResponseDTO = new ProjectAllResponseDTO();
			projectAllResponseDTO.setProjectId(projectMaster.getProjectId());
			projectAllResponseDTO.setProjectName(projectMaster.getProjectName());
			projectAllResponseDTO.setProjectNickName(projectMaster.getProjectNickName());
			List<ApplicationAllResponseDTO> applicationAllResponseDTOs = new ArrayList<>();

			for (ProjectApplicationDetail applicationDetail : projectMaster.getProjectApplicationDetails()) {
				ApplicationAllResponseDTO applicationAllResponseDTO = new ApplicationAllResponseDTO();
				applicationAllResponseDTO.setApplicationId(applicationDetail.getApplicationId());
				applicationAllResponseDTO.setApplicationName(applicationDetail.getApplicationName());
				applicationAllResponseDTO.setProjectId(applicationDetail.getProjectId());
				applicationAllResponseDTO.setApplicationNickName(applicationDetail.getApplicationNickName());
				applicationAllResponseDTOs.add(applicationAllResponseDTO);

			}
			projectAllResponseDTO.setProjectApplicationDetails(applicationAllResponseDTOs);
			projectAllResponseDTOs.add(projectAllResponseDTO);
		}

		return projectAllResponseDTOs;
	}

	@SuppressWarnings("null")
	@Override
	public List<ProjectApplicationDetail> getAllProjectByEmpiD(int employeeId) throws TsmException {
		List<Tuple> tuples = projectMasterRepository.findAllByEmployeeId(employeeId);

		List<ProjectMasterServiceDto> projectMasterServiceDtos = new ArrayList<>();

		for (Tuple tuple : tuples) {
			ProjectMasterServiceDto projectMasterServiceDto = new ProjectMasterServiceDto();
			projectMasterServiceDto.setProjectId(tuple.get(0) != null ? (int) tuple.get(0) : null);
			projectMasterServiceDto.setApplicationId(tuple.get(1) != null ? (int) tuple.get(1) : null);
			projectMasterServiceDto.setAssignmnetId(tuple.get(2) != null ? (int) tuple.get(2) : null);
			projectMasterServiceDtos.add(projectMasterServiceDto);
		}

		List<ProjectApplicationDetail> projectMasters = new ArrayList<>();
		for (ProjectMasterServiceDto projectMasterServiceDto : projectMasterServiceDtos) {
			ProjectApplicationDetail masters = projectApplicationDetailRepository
					.findById(projectMasterServiceDto.getApplicationId())
					.orElseThrow(() -> new TsmException("project is not found"));
			masters.setProjectAssignments(null);
			projectMasters.add(masters);
		}
		return projectMasters;
	}

}
