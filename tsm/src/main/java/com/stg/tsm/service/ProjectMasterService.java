package com.stg.tsm.service;

import java.util.List;

import com.stg.tsm.dto.ProjectAllResponseDTO;
import com.stg.tsm.dto.ProjectMaterRequestDto;
import com.stg.tsm.entity.ProjectApplicationDetail;
import com.stg.tsm.exception.TsmException;

public interface ProjectMasterService {

	List<ProjectApplicationDetail> readAll(ProjectMaterRequestDto projectMaterRequestDto) throws TsmException;

	List<ProjectAllResponseDTO> readAllProject() throws TsmException;

	List<ProjectApplicationDetail> getAllProjectByEmpiD(int employeeId) throws TsmException;

}
