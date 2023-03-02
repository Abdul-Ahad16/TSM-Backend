package com.stg.tsm.controller;

import com.stg.tsm.dto.ProjectAllResponseDTO;
import com.stg.tsm.dto.ProjectMaterRequestDto;
import com.stg.tsm.entity.ProjectApplicationDetail;
import com.stg.tsm.service.ProjectMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 
 * @author saikrishnan
 * @author jenifer
 *
 */

@RestController
@RequestMapping("/project")
public class ProjectMasterController {
	
	@Autowired
	private ProjectMasterService projectMasterService;
	
    
    
    @GetMapping(value="allprojects")
    public ResponseEntity<List<ProjectAllResponseDTO>> getAllProject(){
        
    List<ProjectAllResponseDTO> projectMaster =  projectMasterService.readAllProject(); 
        
    return ResponseEntity.status(HttpStatus.OK).body(projectMaster);
        
    }
  	
	@PostMapping(value="all")
	public List<ProjectApplicationDetail> getAll(@RequestBody ProjectMaterRequestDto projectMaterRequestDto){
		return projectMasterService.readAll(projectMaterRequestDto);
	}

	

}
