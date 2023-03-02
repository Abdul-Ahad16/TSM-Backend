package com.stg.tsm.mobileController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stg.tsm.dto.CreateUserStoryResponseDTO;
import com.stg.tsm.dto.MobileActivityUserStoryDTO;
import com.stg.tsm.dto.MobileUserStoryResopnseDto;
import com.stg.tsm.dto.MobileUserstoryActivityResponseDTO;
import com.stg.tsm.dto.ProjectAssociationResponseDTO;
import com.stg.tsm.dto.UserstoryCreateDTO;
import com.stg.tsm.dto.UserstoryCreateMobileDTO;
import com.stg.tsm.entity.UserstoryMaster;
import com.stg.tsm.exception.MobileTsmException;
import com.stg.tsm.service.MobileAppService;
import com.stg.tsm.service.MobileProjectAssociationService;

@RestController
@RequestMapping("/mobile/employee/userstory")
public class UserstoryMasterMobileController {

	@Autowired
	private MobileAppService mobileAppService;
	
	@Autowired
	private MobileProjectAssociationService mobileProjectAssociationService; 

	@PostMapping("/create-userstory-master")
	public ResponseEntity<CreateUserStoryResponseDTO> createUserstoryMaster(
			@RequestBody UserstoryCreateDTO userstoryCreateDTO) throws MobileTsmException {
		CreateUserStoryResponseDTO response = mobileAppService.createUserstoryMaster(userstoryCreateDTO.getSprintId(), userstoryCreateDTO);
		return new ResponseEntity<CreateUserStoryResponseDTO>(response, HttpStatus.OK);
	}

	@PostMapping("/get-userstorylist")
	public ResponseEntity<MobileUserstoryActivityResponseDTO> getUserstoryMasterList(@RequestBody UserstoryCreateDTO userstoryCreateDTO) throws MobileTsmException {
		List<UserstoryCreateMobileDTO> userstoryData = mobileAppService.getUserstoryMasterList(userstoryCreateDTO.getSprintId()).getData();
		List<ProjectAssociationResponseDTO> projectActivity = mobileProjectAssociationService.getAllActivityByproject(userstoryCreateDTO.getProjectId());
		MobileActivityUserStoryDTO addUserStoryActivity = new MobileActivityUserStoryDTO();
		addUserStoryActivity.setUserstories(userstoryData);
		addUserStoryActivity.setProjectActivity(projectActivity);
		MobileUserstoryActivityResponseDTO response = new MobileUserstoryActivityResponseDTO();
		response.setData(addUserStoryActivity);
		response.setMessage("successful");
		response.setStatus(200);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@PutMapping("/update-userstory")
    public ResponseEntity<String> updateUserstoryMaster(@RequestBody UserstoryMaster UserstoryMaster){
      return ResponseEntity.status(HttpStatus.OK).body(mobileAppService.updateUserstoryMaster(UserstoryMaster));
    }
}
