package com.stg.tsm.mobileController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stg.tsm.dto.MobileProjectAssociationResponseDTO;
import com.stg.tsm.dto.ProjectAssociationResponseDTO;
import com.stg.tsm.dto.ProjectMaterRequestDto;
import com.stg.tsm.exception.MobileTsmException;
import com.stg.tsm.service.MobileProjectAssociationService;

@RestController
@RequestMapping("/mobile/employee")
public class ProjectAssociationMobileController {

	@Autowired
	private MobileProjectAssociationService mobileProjectAssociationService;

	@PostMapping(value = "/project-activity")
	public ResponseEntity<MobileProjectAssociationResponseDTO> getAllActivitiesByProjectId(@RequestBody ProjectMaterRequestDto materRequestDto) throws MobileTsmException {
		MobileProjectAssociationResponseDTO response = new MobileProjectAssociationResponseDTO();
		List<ProjectAssociationResponseDTO> associationResponseDTOs = mobileProjectAssociationService.getAllActivityByproject(materRequestDto.getProjectId());
		response.setData(associationResponseDTOs);
		response.setMessage("Successful");
		response.setStatus(200);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

}
