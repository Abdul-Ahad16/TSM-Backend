package com.stg.tsm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stg.tsm.dto.ProjectAssignmentRequestDTO;
import com.stg.tsm.dto.ProjectMaterRequestDto;
import com.stg.tsm.entity.ProjectAssignment;
import com.stg.tsm.service.ProjectAssignmentService;

/**
 * 
 * @author saikrishnan
 * @author jenifer
 *
 */

@RestController
@RequestMapping("/assignmnet")
public class ProjectAssignmnetController {

    @Autowired
    private ProjectAssignmentService projectAssignmentService;

    @PostMapping(value = "/read-all/project-employee-id")
    public ResponseEntity<Integer> getAssignmnetByProjectIdAndEmployeeId(
            @RequestBody ProjectMaterRequestDto dto) {

        int assignment = projectAssignmentService
                .projectAssignmentByProjectAndEmployeeId(dto.getProjectApplicationId(), dto.getEmployeeId());
        return ResponseEntity.status(HttpStatus.OK)
                .body(assignment);

    }

    @PostMapping(value = "/project-application/create")
    public ResponseEntity<List<ProjectAssignment>> projectAssignment(
            @RequestBody List<ProjectAssignmentRequestDTO> assignmentRequestDTOs) {

        List<ProjectAssignment> assignments = this.projectAssignmentService
                .createApplicationAssignmnet(assignmentRequestDTOs);
        return ResponseEntity.status(HttpStatus.OK)
                .body(assignments);

    }

}
