package com.stg.tsm.service.Impl;

import com.stg.tsm.dto.CreateSprintResponseDTO;
import com.stg.tsm.entity.ProjectApplicationDetail;
import com.stg.tsm.entity.ProjectMaster;
import com.stg.tsm.entity.SprintMaster;
import com.stg.tsm.entity.TaskMaster;
import com.stg.tsm.exception.MobileTsmException;
import com.stg.tsm.exception.TsmException;
import com.stg.tsm.repos.ProjectApplicationDetailRepository;
import com.stg.tsm.repos.ProjectMasterRepository;
import com.stg.tsm.repos.SprintMasterRepository;
import com.stg.tsm.service.SprintService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author saikrishnan
 *
 *
 */

@Service
public class SprintServiceImpl implements SprintService {

	@Autowired(required = true)
	private SprintMasterRepository sprintMasterRepository;

	@Autowired
	private ProjectMasterRepository projectMasterRepository;

	@Autowired
	private ProjectApplicationDetailRepository projectApplicationDetailRepository;

	@Override
	public CreateSprintResponseDTO createSprint(int projectId, int applicationId, SprintMaster sprintMaster) throws TsmException {

		ProjectMaster projectMaster = projectMasterRepository.findById(projectId)
				.orElseThrow(() -> new TsmException("Project Master is not Found"));

		ProjectApplicationDetail projectApplicationDetail = projectApplicationDetailRepository.findById(applicationId)
				.orElseThrow(() -> new TsmException("Project Application  is not Found"));
		System.out.println(sprintMaster.getCreatedDate());
		System.out.println(projectMaster.getCreatedDate());
		System.out.println(projectMaster.getProjectStartDate());
		String dateStr = sprintMaster.getStartDate().toString();
		System.out.println(dateStr);
		 
		SimpleDateFormat dt = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
		if(sprintMaster.getStartDate().before(projectMaster.getProjectStartDate())) {
			sprintMaster.getStartDate().equals(projectMaster.getProjectStartDate());
			System.out.println("yea");
		}else {
			System.out.println("yes");
		}
		Date date;
		
		
		if (projectMaster != null && projectApplicationDetail != null) {
			List<SprintMaster> sprintList = sprintMasterRepository.findAllByApplicationId(applicationId);
			if (!sprintList.isEmpty()) {
				for (SprintMaster itrSprint : sprintList) {
					String getName = itrSprint.getSprintName();
					String givenName = sprintMaster.getSprintName();
					if(itrSprint.getStartDate().before(sprintMaster.getStartDate()) && itrSprint.getEndDate().after(sprintMaster.getEndDate())) {
						throw new TsmException("Sprint is overlaping with another sprint period");
					}
					if (givenName.equalsIgnoreCase(getName)) {
						throw new TsmException("sprint name already exist");
					}
				}
			}
			sprintMaster.setProjectMaster(projectMaster);
			sprintMaster.setProjectApplicationDetail(projectApplicationDetail);
			try {
				SprintMaster sprintResponse = sprintMasterRepository.save(sprintMaster);
				CreateSprintResponseDTO createSprintResponse = new CreateSprintResponseDTO();
				createSprintResponse.setSprintId(sprintResponse.getSprintId());
				createSprintResponse.setSprintName(sprintResponse.getSprintName());
				createSprintResponse.setSprintDescription(sprintResponse.getSprintDescription());
				createSprintResponse.setStartDate(SprintServiceImpl.getDateFormat(sprintResponse.getStartDate()));
				createSprintResponse.setEndDate(SprintServiceImpl.getDateFormat(sprintResponse.getEndDate()));
				createSprintResponse.setCreatedDate(SprintServiceImpl.getDateFormat(sprintResponse.getCreatedDate()));
				createSprintResponse.setCreatedBy(sprintResponse.getCreatedBy());
				createSprintResponse.setUserstoryMasters(sprintResponse.getUserstoryMasters());
				return createSprintResponse;
			} catch (Exception e) {
				throw new TsmException("Sprint is not Created");
			}
		} else {
			throw new TsmException("Given Correct Details");
		}
	}

	@Override
	public SprintMaster getSprintById(int sprintId) throws TsmException {

		return sprintMasterRepository.findById(sprintId).orElseThrow(() -> new TsmException("sprint not found"));
	}
	
	public static String getDateFormat(Date date) throws ParseException {
		String givenDate = date.toString();
		SimpleDateFormat inputFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date formatDate = inputFormat.parse(givenDate);
        String outputDate = outputFormat.format(formatDate);
		return outputDate;
	}
	
}
