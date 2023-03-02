package com.stg.tsm.controller;

import com.stg.tsm.entity.ProjectApplicationDetail;
import com.stg.tsm.entity.SprintMaster;
import com.stg.tsm.service.ProjectApplicationDetailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping(value = "application")
public class ProjectApplicationDetailController {

    @Autowired
    private ProjectApplicationDetailService projectApplicationDetailService;

    @GetMapping(value = "all")
    public List<ProjectApplicationDetail> readAll() {
        return projectApplicationDetailService.readAll();
    }

    @GetMapping(value = "/get/sprints/application-id/{projectApplicationId}")
    public ResponseEntity<List<SprintMaster>> getAllSprintsByProjectAppId(@PathVariable int projectApplicationId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(projectApplicationDetailService.readSprintMastersById(projectApplicationId));
    }
}
