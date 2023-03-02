package com.stg.tsm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserstorySearchDTO {
    private int employeeId;
    private int sprintId;
    private int projectId;
    private int startingRecordNumber;
    private int pageSize;
    private String sortField;
    private String sortOrder;
    private int assignmnetId;
}
