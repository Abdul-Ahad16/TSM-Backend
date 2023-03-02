package com.stg.tsm.mobileController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stg.tsm.dto.CreateSprintMasterRequestDTO;
import com.stg.tsm.dto.CreateSprintMasterResponseDTO;
import com.stg.tsm.dto.MobileProjectApplicationSprintDTO;
import com.stg.tsm.dto.MobileSprintMasterRequestDTO;
import com.stg.tsm.dto.MobileSprintMasterResponseDTO;
import com.stg.tsm.exception.MobileTsmException;
import com.stg.tsm.service.MobileProjectApplicationSprintService;

@RestController
@RequestMapping("/mobile/employee")
public class MobileProjectApplicationSprintController {
	
	@Autowired
	private MobileProjectApplicationSprintService projectApplicationSprint;
	
	@PostMapping(value = "/project-details")
	public ResponseEntity<MobileProjectApplicationSprintDTO> getProjectApplicationSprint(@RequestBody MobileProjectApplicationSprintDTO projectApplicationSprintDTO) throws MobileTsmException{
		MobileProjectApplicationSprintDTO result = projectApplicationSprint.getProjectApplicationSprint(projectApplicationSprintDTO.getEmployeeId());
		return new ResponseEntity<MobileProjectApplicationSprintDTO>(result, HttpStatus.OK);
	}
	
	@PostMapping(value = "/sprints")
	public ResponseEntity<MobileSprintMasterResponseDTO> getSprints(@RequestBody MobileSprintMasterRequestDTO sprintMasterApplicationIdDTO) throws MobileTsmException{
		MobileSprintMasterResponseDTO result = projectApplicationSprint.getSprints(sprintMasterApplicationIdDTO.getApplicationId());
		return new ResponseEntity<MobileSprintMasterResponseDTO>(result, HttpStatus.OK);
	}
	
	@PostMapping(value = "/create-sprint")
	public ResponseEntity<CreateSprintMasterResponseDTO> createSprint(@RequestBody CreateSprintMasterRequestDTO createSprintMasterRequestDTO) throws MobileTsmException{
		CreateSprintMasterResponseDTO response = projectApplicationSprint.createSprint(createSprintMasterRequestDTO);
		return new ResponseEntity<CreateSprintMasterResponseDTO>(response, HttpStatus.OK);
	}

}
