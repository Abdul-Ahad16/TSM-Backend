package com.stg.tsm.controller;

import com.stg.tsm.entity.UserRole;
import com.stg.tsm.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user-role")
public class UserRoleController {

    @Autowired
    private UserRoleService userRoleService;

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserRole> create(@RequestBody UserRole userRole){
       return new ResponseEntity<>(userRoleService.createUserRoleAssignment(userRole), HttpStatus.CREATED);
    }
}
