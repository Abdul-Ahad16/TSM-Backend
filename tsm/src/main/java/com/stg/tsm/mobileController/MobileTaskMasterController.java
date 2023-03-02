package com.stg.tsm.mobileController;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stg.tsm.dto.MobileLoginResponseDTO;
import com.stg.tsm.dto.MobileTaskMasterRequestDTO;
import com.stg.tsm.dto.TaskMasterDTO;
import com.stg.tsm.entity.TaskMaster;
import com.stg.tsm.exception.MobileTsmException;
import com.stg.tsm.service.TaskMasterService;
import com.stg.tsm.service.Impl.MobileTaskmasterServiceImpl;

@RestController
@RequestMapping("/mobile/taskMaster")
public class MobileTaskMasterController {
    @Autowired
    private TaskMasterService taskMasterService;
    
    @Autowired
    private MobileTaskmasterServiceImpl taskmasterServiceImpl;

    @Autowired
    private ModelMapper modelMapper;
    
    @PostMapping("/create")
    public ResponseEntity<MobileLoginResponseDTO> createTask
    (@RequestBody MobileTaskMasterRequestDTO requestDTO) throws MobileTsmException {
        TaskMaster taskMaster = modelMapper.map(requestDTO.getTaskMaster(), TaskMaster.class);
        TaskMaster response = taskmasterServiceImpl.createTasks(requestDTO.getEmployeeId(),
        		requestDTO.getProjectApplicationId(), requestDTO.getUserstoryId(), taskMaster);   
        MobileLoginResponseDTO reuslt = new MobileLoginResponseDTO(null, "Successful", 200);
        
        return ResponseEntity.status(HttpStatus.OK).body(reuslt);
	}
	   
	    @PutMapping("/update")
	    public ResponseEntity<MobileLoginResponseDTO> updateTask(@RequestBody TaskMasterDTO taskMasterDTO) throws MobileTsmException {
	        TaskMaster taskMaster = modelMapper.map(taskMasterDTO, TaskMaster.class);

	        TaskMaster response = taskmasterServiceImpl.updateTaskMaster(taskMasterDTO.getUserStoryId(),
	                taskMasterDTO.getAssignmentId(), taskMasterDTO.getTaskId(), taskMaster);
	        
	        MobileLoginResponseDTO reuslt = new MobileLoginResponseDTO(response, "Successful", 200);

	        return ResponseEntity.status(HttpStatus.OK).body(reuslt);
		}
	    
		@PostMapping("/delete")
		public ResponseEntity<MobileLoginResponseDTO> deleteTasksById(@RequestBody TaskMasterDTO taskMasterDTO) throws MobileTsmException {
			taskmasterServiceImpl.deleteTaskmasterById(taskMasterDTO);
			MobileLoginResponseDTO reuslt = new MobileLoginResponseDTO(null, "Successfully deleted", 200);
			return ResponseEntity.status(HttpStatus.OK).body(reuslt);
	    }
		
}
