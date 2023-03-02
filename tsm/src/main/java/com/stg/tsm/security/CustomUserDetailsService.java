package com.stg.tsm.security;

import com.stg.tsm.dto.EmployeeRequestDto;
import com.stg.tsm.dto.EmployeeResponseDto;
import com.stg.tsm.dto.EmployeeRoleDTO;
import com.stg.tsm.entity.EmployeeDetail;
import com.stg.tsm.repos.EmployeeDetailRepository;
import com.stg.tsm.service.MailSender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private EmployeeDetailRepository employeeDetailRepository;

    @Autowired 
    private MailSender mailSender;
    

    @SuppressWarnings("unused")
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        EmployeeDetail employeeDetail = employeeDetailRepository.findByUsername(username);
        List<EmployeeRoleDTO> roleFunctions = employeeDetailRepository
                .getEmpRoleFunctions(employeeDetail.getEmployeeId());
        if (employeeDetail != null) {
            return new User(employeeDetail.getUsername(),
                    employeeDetail.getPassword(), getAuthorities(new ArrayList<>(roleFunctions)));
        } else {
            throw new BadCredentialsException("Invalid username");
        }
    }

    public Collection<GrantedAuthority> getAuthorities(List<EmployeeRoleDTO> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();

        for (EmployeeRoleDTO role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
            roles.stream()
                    .map(p -> new SimpleGrantedAuthority(p.getDescription()))
                    .forEach(authorities::add);
        }
        return authorities;
    }

    public EmployeeDetail createEmployee(EmployeeRequestDto employeeDetailDto) {
        EmployeeDetail employeeDetail = new EmployeeDetail();

        employeeDetail.setCreatedBy(employeeDetailDto.getCreatedBy());
        employeeDetail.setCreatedDate(employeeDetailDto.getCreatedDate());
        employeeDetail.setEmployeeName(employeeDetailDto.getEmployeeName());
        employeeDetail.setUsername(employeeDetailDto.getUsername());
        employeeDetail.setPassword(passwordEncoder().encode(employeeDetailDto.getPassword()));
        employeeDetail.setEmail(employeeDetailDto.getEmail());
        employeeDetail.setJoiningDate(employeeDetailDto.getJoiningDate());
        employeeDetail.setRelievingDate(employeeDetailDto.getRelievingDate());
        employeeDetail.setUpdatedBy(employeeDetailDto.getUpdatedBy());
        employeeDetail.setUpdatedDate(employeeDetailDto.getUpdatedDate());
        
        EmployeeDetail employeeDetailSaved = employeeDetailRepository.saveAndFlush(employeeDetail);
        if(employeeDetailSaved != null) {
            mailSender.sendEmailUser(employeeDetailDto.getEmail(), employeeDetailDto.getCreatorEmail(),
                    employeeDetailDto.getEmployeeName());
            mailSender.sendEmailProjectManger(employeeDetailDto.getEmployeeName() , employeeDetailDto.getEmail(),
                    employeeDetailDto.getPassword(), employeeDetailDto.getCreatorEmail());
        }

        return employeeDetailSaved;
    }

    public EmployeeResponseDto getEmployeeData(String username) {

        EmployeeDetail employeeDetail = employeeDetailRepository.findByUsername(username);
        if (employeeDetail != null) {

            List<EmployeeRoleDTO> roleFunctions = employeeDetailRepository
                    .getEmpRoleFunctions(employeeDetail.getEmployeeId());
            EmployeeResponseDto employeeResponseDto = new EmployeeResponseDto();

            employeeResponseDto.setEmployeeId(employeeDetail.getEmployeeId());
            employeeResponseDto.setCreatedBy(employeeDetail.getCreatedBy());
            employeeResponseDto.setCreatedDate(employeeDetail.getCreatedDate());
            employeeResponseDto.setEmployeeName(employeeDetail.getEmployeeName());
            employeeResponseDto.setUsername(employeeDetail.getUsername());
            employeeResponseDto.setEmail(employeeDetail.getEmail());
            employeeResponseDto.setJoiningDate(employeeDetail.getJoiningDate());
            employeeResponseDto.setRelievingDate(employeeDetail.getRelievingDate());
            employeeResponseDto.setUpdatedBy(employeeDetail.getUpdatedBy());
            employeeResponseDto.setUpdatedDate(employeeDetail.getUpdatedDate());
            employeeResponseDto.setProjectAssignments(employeeDetail.getProjectAssignments());

            List<String> temp = new ArrayList<>();
            if (roleFunctions != null) {
                roleFunctions.forEach(each -> {
                    employeeResponseDto.setEmpRole(each.getRoleName());
                    temp.add(each.getDescription());
                });

                employeeResponseDto.setEmpFunctions(temp);
            }

            return employeeResponseDto;
        } else {
            throw new BadCredentialsException("Invalid username");
        }
    }

    public List<EmployeeResponseDto> getAllEmployees() {
        List<EmployeeDetail> employeeDetails = employeeDetailRepository.findAll();

        List<EmployeeResponseDto> employeeResponseDtos = new ArrayList<>();

        for (EmployeeDetail employeeDetail : employeeDetails) {

            List<EmployeeRoleDTO> roleFunctions = employeeDetailRepository
                    .getEmpRoleFunctions(employeeDetail.getEmployeeId());
            EmployeeResponseDto employeeResponseDto = new EmployeeResponseDto();

            employeeResponseDto.setEmployeeId(employeeDetail.getEmployeeId());
            employeeResponseDto.setCreatedBy(employeeDetail.getCreatedBy());
            employeeResponseDto.setCreatedDate(employeeDetail.getCreatedDate());
            employeeResponseDto.setEmployeeName(employeeDetail.getEmployeeName());
            employeeResponseDto.setUsername(employeeDetail.getUsername());
            employeeResponseDto.setEmail(employeeDetail.getEmail());
            employeeResponseDto.setJoiningDate(employeeDetail.getJoiningDate());
            employeeResponseDto.setRelievingDate(employeeDetail.getRelievingDate());
            employeeResponseDto.setUpdatedBy(employeeDetail.getUpdatedBy());
            employeeResponseDto.setUpdatedDate(employeeDetail.getUpdatedDate());
            employeeResponseDto.setProjectAssignments(employeeDetail.getProjectAssignments());

            List<String> temp = new ArrayList<>();
            if (roleFunctions != null) {
                roleFunctions.forEach(each -> {
                    employeeResponseDto.setEmpRole(each.getRoleName());
                    temp.add(each.getDescription());
                });

                employeeResponseDto.setEmpFunctions(temp);
            }
            employeeResponseDtos.add(employeeResponseDto);
        }
        employeeResponseDtos.sort(Comparator.comparing(EmployeeResponseDto::getEmployeeName,
                Comparator.nullsFirst(Comparator.naturalOrder())));
        return employeeResponseDtos;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

  
}
