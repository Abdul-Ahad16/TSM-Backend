package com.stg.tsm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stg.tsm.entity.ParameterMaster;
import com.stg.tsm.service.ParameterService;

/**
 * 
 * @author saikrishnan
 *
 *
 */

@RestController
@RequestMapping("/parameter")
public class ParameterMasterController {
    
    
    @Autowired
    private ParameterService parameterService;
    
    @GetMapping(value="/activity/all")
    public ResponseEntity<ParameterMaster>  getAllActivity(){
        
        return ResponseEntity.status(HttpStatus.OK).body(this.parameterService.getActivity("ACTIVITY"));
        
    }
    

}
