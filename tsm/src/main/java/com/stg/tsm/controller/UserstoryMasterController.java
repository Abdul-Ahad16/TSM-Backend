package com.stg.tsm.controller;

import com.stg.tsm.dto.UserstoryCreateDTO;
import com.stg.tsm.entity.UserstoryMaster;
import com.stg.tsm.exception.TsmException;
import com.stg.tsm.repos.UserstoryMasterRepository;
import com.stg.tsm.service.UserstoryMasterService;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
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
@RequestMapping("/userstory")
public class UserstoryMasterController {

    @Autowired
    private UserstoryMasterService userstoryMasterService;
    
    @Autowired
    private ModelMapper modelMapper;
    
    private UserstoryMasterRepository userstoryMasterRepository;

  
    @PostMapping("/create-userstory-master")
    public ResponseEntity<UserstoryMaster> createUserstoryMaster(@RequestBody UserstoryCreateDTO userstoryCreateDTO){
        
        return ResponseEntity.status(HttpStatus.OK).body(userstoryMasterService.createUserstoryMaster(userstoryCreateDTO.getSprintId(),userstoryCreateDTO));
    }
    
    @GetMapping("/get-userstorylist")
    public ResponseEntity<List<UserstoryMaster>> getUserstoryMasterList(@RequestParam int sprintId){
    	
        return ResponseEntity.status(HttpStatus.OK).body(userstoryMasterService.getUserstoryMasterList(sprintId));
    }
    
    @PutMapping("update-userstory")
    public ResponseEntity<String> updateUserstoryMaster(@RequestBody UserstoryMaster UserstoryMaster){
      return ResponseEntity.status(HttpStatus.OK).body(userstoryMasterService.updateUserstoryMaster(UserstoryMaster));
    }
    
    @DeleteMapping("delete-userstory")
    public ResponseEntity<List<UserstoryMaster>> deleteUserstoryById(@RequestParam int id){
      return ResponseEntity.status(HttpStatus.OK).body(userstoryMasterService.deleteUserstoryMaster(id));
    }


}
