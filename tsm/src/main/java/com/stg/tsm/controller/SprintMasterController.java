package com.stg.tsm.controller;

import com.stg.tsm.dto.CreateSprintResponseDTO;
import com.stg.tsm.dto.SprintRequestDTO;
import com.stg.tsm.entity.SprintMaster;
import com.stg.tsm.service.SprintService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 
 * @author saikrishnan
 * @author jenifer
 *
 */

@RestController
@RequestMapping("/sprint")
public class SprintMasterController {

	@Autowired(required = true)
    private SprintService sprintService;

    @PostMapping("/create-sprint/{projectId}/{applicationId}")
    public ResponseEntity<CreateSprintResponseDTO> createSprint(@PathVariable int projectId,@PathVariable int applicationId, @RequestBody  SprintMaster sprintMaster){
    	return ResponseEntity.status(HttpStatus.OK).body(sprintService.createSprint(projectId,applicationId,sprintMaster));
    }
    
    @PostMapping("search-sprint")
    public ResponseEntity<SprintMaster> getSprintById(@RequestBody SprintRequestDTO dto){
    	
    	SprintMaster sprintMaster = sprintService.getSprintById(dto.getSprintId());
    	return ResponseEntity.status(HttpStatus.OK).body(sprintMaster);
    }
    
}
