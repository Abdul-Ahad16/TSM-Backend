package com.stg.tsm.dto;

import com.stg.tsm.entity.EmployeeDetail;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * A DTO for the {@link EmployeeDetail} entity
 */
@Data
public class EmployeeRequestDto implements Serializable {
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
}