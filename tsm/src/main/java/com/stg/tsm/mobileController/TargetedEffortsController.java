package com.stg.tsm.mobileController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stg.tsm.dto.MobileTaskEffortsRequestDTO;
import com.stg.tsm.dto.MobileTaskMasterResponseDTO;
import com.stg.tsm.exception.MobileTsmException;
import com.stg.tsm.service.TargetedHoursWithUserStoriesService;

@RestController
@RequestMapping("/mobile/employee/")
public class TargetedEffortsController {
	
	@Autowired
	private TargetedHoursWithUserStoriesService targetedHoursWithUserStories;
	
	@PostMapping(value="/efforts")
	public ResponseEntity<MobileTaskMasterResponseDTO> getEfforts(@RequestBody MobileTaskEffortsRequestDTO targetedHoursWithUserStoriesDto) throws MobileTsmException{
		MobileTaskMasterResponseDTO result = targetedHoursWithUserStories.getUserStoriesEfforts(targetedHoursWithUserStoriesDto);
		return new ResponseEntity<MobileTaskMasterResponseDTO>(result, HttpStatus.OK);
	}

}
