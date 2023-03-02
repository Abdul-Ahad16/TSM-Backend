package com.stg.tsm.mobileController;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.stg.tsm.dto.AddMobileEffortRequestDto;
import com.stg.tsm.dto.AuthenticationMobileResponse;
import com.stg.tsm.dto.AuthenticationRequest;
import com.stg.tsm.dto.EmployeeProfileMobileDTO;
import com.stg.tsm.dto.MobileAuthenticationResponseDTO;
import com.stg.tsm.dto.MobileLoginResponseDTO;
import com.stg.tsm.dto.MobileProfileRequest;
import com.stg.tsm.dto.TaskMasterDTO;
import com.stg.tsm.dto.ResetPasswordDTO;
import com.stg.tsm.entity.EmployeeDetail;
import com.stg.tsm.entity.SprintMaster;
import com.stg.tsm.entity.TaskMaster;
import com.stg.tsm.exception.MobileTsmException;
import com.stg.tsm.exception.TsmException;
import com.stg.tsm.repos.EmployeeDetailRepository;
import com.stg.tsm.security.CustomUserDetailsService;
import com.stg.tsm.security.JwtUtil;
import com.stg.tsm.service.MobileAppService;
import com.stg.tsm.service.TaskMasterService;
import com.stg.tsm.service.Impl.MobileAppServiceImpl;

@RestController
@RequestMapping("/mobile/employee")
public class EmployeeControllerMobile {
    
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private EmployeeDetailRepository employeeDetailRepository;

    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private MobileAppService mobileAppService;
    

    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private TaskMasterService taskMasterService;
    
    
    @PostMapping(value = "/authenticate")
    public ResponseEntity<MobileLoginResponseDTO> createAuthenticationToken(
            @RequestBody AuthenticationRequest authenticationRequest) throws MobileTsmException {
    	String message = "";
    	try {

        EmployeeDetail employeeDetailTemp = employeeDetailRepository.findByEmail(authenticationRequest.getEmail());

        UserDetails userDetails;
        if (employeeDetailTemp != null) {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(employeeDetailTemp.getUsername(),
                    authenticationRequest.getPassword()));

            userDetails = customUserDetailsService
                    .loadUserByUsername(employeeDetailTemp.getUsername());
        } else {
        	message = "Invalid Email Id";
        	return new ResponseEntity<>(new MobileLoginResponseDTO(null, message,400),HttpStatus.OK);
        }


        final String jwt = jwtUtil.generateToken(userDetails);
        final MobileAuthenticationResponseDTO employeeResponseDto = mobileAppService.getEmployeeData(employeeDetailTemp);
        
        AuthenticationMobileResponse authenticationMobileResponse = new AuthenticationMobileResponse(jwt, employeeResponseDto);

        return new ResponseEntity<>(new MobileLoginResponseDTO(authenticationMobileResponse, "Successful",200),HttpStatus.OK);
    	}catch (BadCredentialsException e) {
    		message = "Invalid password";
    		return new ResponseEntity<>(new MobileLoginResponseDTO(null, message,400),HttpStatus.OK); 
		}
    }
    
    @GetMapping(value = "profile")
    public ResponseEntity<MobileLoginResponseDTO> employeeProfile(@RequestBody int empId) throws MobileTsmException{
    	EmployeeProfileMobileDTO profileMobileDTO= mobileAppService.getEmployeeProfile(empId);
    	return new ResponseEntity<>(new MobileLoginResponseDTO(profileMobileDTO, "successful",200),HttpStatus.OK);  
    }

}
