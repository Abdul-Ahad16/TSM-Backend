package com.stg.tsm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stg.tsm.dto.ProjectAssociationResponseDTO;
import com.stg.tsm.dto.ProjectMaterRequestDto;
import com.stg.tsm.repos.ProjectAssignmentRepository;
import com.stg.tsm.service.ProjectAssociationService;

/**
 * 
 * @author saikrishnan
 *
 *
 * This java class  is for task activity based on project
 *
 */

@RestController
@RequestMapping("/project-association")
public class ProjectAssociationController {

    
    @Autowired
    private ProjectAssociationService associationService;
    
    @PostMapping(value = "/project-id")
    public ResponseEntity<List<ProjectAssociationResponseDTO>> getAllActivitiesByProjectId(@RequestBody ProjectMaterRequestDto materRequestDto ){
        return    ResponseEntity.status(HttpStatus.OK)
                .body(associationService.getAllActivityByproject(materRequestDto.getProjectId()));
    }
}
