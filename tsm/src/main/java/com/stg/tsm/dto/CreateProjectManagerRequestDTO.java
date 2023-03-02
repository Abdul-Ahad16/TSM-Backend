package com.stg.tsm.dto;

import java.util.Date;

import lombok.Data;


@Data
public class CreateProjectManagerRequestDTO {
    
    private String createdBy;
    private Date createdDate;
    private String employeeName;
    private String username;
    private String password;
    private String email;
    private Date joiningDate;
    private Date relievingDate;
    private String updatedBy;
    private Date updatedDate;
    private String creatorEmail;
    private String role;
    
}

