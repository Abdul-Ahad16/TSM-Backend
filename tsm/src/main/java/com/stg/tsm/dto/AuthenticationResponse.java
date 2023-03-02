package com.stg.tsm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthenticationResponse {
	
	private final String jwt;
	private EmployeeResponseDto employeeDetails;
}
