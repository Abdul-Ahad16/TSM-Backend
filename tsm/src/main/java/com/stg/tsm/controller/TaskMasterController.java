package com.stg.tsm.controller;


import com.stg.tsm.dto.TaskMasterDTO;
import com.stg.tsm.dto.UserstorySearchDTO;
import com.stg.tsm.dto.TaskListDTO;
import com.stg.tsm.entity.TaskMaster;
import com.stg.tsm.service.TaskMasterService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author saikrishnan
 * @author jenifer
 *
 */

@RestController
@RequestMapping("/task")
public class TaskMasterController {

    @Autowired
    private TaskMasterService taskMasterService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/create")
    public ResponseEntity<TaskMaster> createTask(@RequestBody TaskMasterDTO taskMasterDTO) {

        TaskMaster taskMaster = modelMapper.map(taskMasterDTO, TaskMaster.class);

        TaskMaster taskMasterRequest = taskMasterService.createTask(taskMasterDTO.getUserStoryId(),
                taskMasterDTO.getAssignmentId(), taskMaster);

        return ResponseEntity.status(HttpStatus.OK).body(taskMasterRequest);

    }

    @PostMapping(value = "/all")
    public ResponseEntity<TaskListDTO> getAll(@RequestBody UserstorySearchDTO userstorySearchDTO) {
      
        TaskListDTO response = taskMasterService.taskList(userstorySearchDTO);
        
        return ResponseEntity.status(HttpStatus.OK).body(response);
        
    }


    @PutMapping("/update")
    public ResponseEntity<TaskMaster> updateTask(@RequestBody TaskMasterDTO taskMasterDTO) {
        TaskMaster taskMaster = modelMapper.map(taskMasterDTO, TaskMaster.class);

        TaskMaster response = taskMasterService.updateTaskMaster(taskMasterDTO.getUserStoryId(),
                taskMasterDTO.getAssignmentId(), taskMasterDTO.getTaskId(), taskMaster);

        return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@PostMapping("/delete")
	public ResponseEntity<Integer> deleteTasksById(@RequestBody int taskId) {
	    
		return ResponseEntity.status(HttpStatus.OK).body(taskMasterService.deleteTaskmasterById(taskId));
    }
	
	
	 
    @GetMapping("/download")
    public ResponseEntity<Resource> getFile(@RequestBody UserstorySearchDTO userstorySearchDTO) {
      TaskListDTO  task = taskMasterService.taskList(userstorySearchDTO);
      String filename = "tasks.xlsx";
      InputStreamResource file = new InputStreamResource(this.taskMasterService.load(task.getAllTaskDTOs()));

      return ResponseEntity.ok()
          .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
          .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
          .body(file);
    }

}
