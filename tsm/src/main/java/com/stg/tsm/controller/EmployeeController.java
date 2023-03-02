package com.stg.tsm.controller;

import com.stg.tsm.dto.AuthenticationRequest;
import com.stg.tsm.dto.AuthenticationResponse;
import com.stg.tsm.dto.CreateProjectManagerRequestDTO;
import com.stg.tsm.dto.EmployeeRequestDto;
import com.stg.tsm.dto.EmployeeResponseDto;
import com.stg.tsm.dto.ResetPasswordDTO;
import com.stg.tsm.entity.EmployeeDetail;
import com.stg.tsm.exception.TsmException;
import com.stg.tsm.repos.EmployeeDetailRepository;
import com.stg.tsm.security.CustomUserDetailsService;
import com.stg.tsm.security.JwtUtil;
import com.stg.tsm.service.EmployeeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 
 * @author raghuram
 *
 */
@RestController
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Autowired
	private EmployeeDetailRepository employeeDetailRepository;
	
	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private AuthenticationManager authenticationManager;

	@GetMapping("/details")
	public ResponseEntity<EmployeeResponseDto> getEmployeeDetails(@RequestBody EmployeeRequestDto employeeRequestDto) {
		return new ResponseEntity<>(customUserDetailsService.getEmployeeData(employeeRequestDto.getUsername()), HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('VIEW_EMPLOYEE_LIST')")
	@GetMapping("/all")
	public ResponseEntity<List<EmployeeResponseDto>> getAllEmployees() {
		return new ResponseEntity<>(customUserDetailsService.getAllEmployees(), HttpStatus.OK);
	}

	@PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<EmployeeDetail> create(@RequestBody EmployeeRequestDto employeeDetailDto) {
		return new ResponseEntity<>(customUserDetailsService.createEmployee(employeeDetailDto), HttpStatus.CREATED);
	}
	
	@PostMapping(value = "/resetPassword", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeDetail> resetPassword(@RequestBody ResetPasswordDTO resetPasswordDTO) {
	    
	    if(resetPasswordDTO.getOldPassword() == null ) {
	        //reset password by admin / project manger
	        return new ResponseEntity<>(employeeService.resetPassword(resetPasswordDTO), HttpStatus.OK);
	    }else {
	        //reset password by employee .
	        return new ResponseEntity<>(employeeService.confirmPassword(resetPasswordDTO), HttpStatus.OK);
	    }
	    
        
    }

	@PostMapping(value = "/authenticate")
	public ResponseEntity<AuthenticationResponse> createAuthenticationToken(
			@RequestBody AuthenticationRequest authenticationRequest) throws TsmException {

		EmployeeDetail employeeDetailTemp = employeeDetailRepository.findByEmail(authenticationRequest.getEmail());

		UserDetails userDetails;
		if (employeeDetailTemp != null) {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(employeeDetailTemp.getUsername(),
					authenticationRequest.getPassword()));

			userDetails = customUserDetailsService
					.loadUserByUsername(employeeDetailTemp.getUsername());
		} else {
			throw new BadCredentialsException("Invalid Email Id");
		}


		final String jwt = jwtUtil.generateToken(userDetails);
		final EmployeeResponseDto employeeResponseDto = customUserDetailsService.getEmployeeData(employeeDetailTemp.getUsername());

		return new ResponseEntity<>(new AuthenticationResponse(jwt, employeeResponseDto), HttpStatus.OK);
	}
	
	
	@PostMapping("register/project-manager")
	public ResponseEntity<Object> createProjectManger(@RequestBody CreateProjectManagerRequestDTO  createProjectManagerRequestDTO){
	    Object response  = this.employeeService.createProjectManger(createProjectManagerRequestDTO);
	    return new ResponseEntity<>(response , HttpStatus.OK);
	}

}
