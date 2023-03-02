package com.stg.tsm.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.stg.tsm.dto.CreateProjectManagerRequestDTO;
import com.stg.tsm.dto.ResetPasswordDTO;
import com.stg.tsm.entity.EmployeeDetail;
import com.stg.tsm.entity.RoleMaster;
import com.stg.tsm.entity.UserRole;
import com.stg.tsm.entity.UserRolePK;
import com.stg.tsm.exception.TsmException;
import com.stg.tsm.repos.EmployeeDetailRepository;
import com.stg.tsm.repos.RoleMasterRepository;
import com.stg.tsm.repos.UserRoleRepository;
import com.stg.tsm.security.CustomUserDetailsService;
import com.stg.tsm.service.EmployeeService;
import com.stg.tsm.service.MailSender;
import com.stg.tsm.utils.Checkpassword;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeDetailRepository employeeDetailRepository;

    @Autowired
    private CustomUserDetailsService customUserDetails;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MailSender mailSender;
    
    @Autowired
    private RoleMasterRepository roleMasterRepository;
    
    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    public EmployeeDetail resetPassword(ResetPasswordDTO resetPasswordDTO) {

        EmployeeDetail oldEmployee = this.employeeDetailRepository.findByEmail(resetPasswordDTO.getEmployeeEmail());

        EmployeeDetail employeeDetail = new EmployeeDetail();

        employeeDetail.setCreatedBy(oldEmployee.getCreatedBy());
        employeeDetail.setCreatedDate(oldEmployee.getCreatedDate());
        employeeDetail.setEmployeeName(oldEmployee.getEmployeeName());
        employeeDetail.setUsername(oldEmployee.getUsername());
        employeeDetail.setPassword(customUserDetails.passwordEncoder().encode(resetPasswordDTO.getNewPassword()));
        employeeDetail.setEmail(oldEmployee.getEmail());
        employeeDetail.setJoiningDate(oldEmployee.getJoiningDate());
        employeeDetail.setRelievingDate(oldEmployee.getRelievingDate());
        employeeDetail.setUpdatedBy(resetPasswordDTO.getAdminName());
        employeeDetail.setUpdatedDate(oldEmployee.getUpdatedDate());
        employeeDetail.setEmployeeId(oldEmployee.getEmployeeId());

        EmployeeDetail updatedEmployee = employeeDetailRepository.saveAndFlush(employeeDetail);
        //sending mail to user
        mailSender.sendEmailUserForPasswordReset(resetPasswordDTO.getAdminEmail(), resetPasswordDTO.getAdminName(), resetPasswordDTO.getEmployeeEmail(), resetPasswordDTO.getEmployeeName());
        //sending mail to manger
        mailSender.sendEmailProjectMangerForPasswordReset(resetPasswordDTO.getAdminEmail(), resetPasswordDTO.getAdminName(), resetPasswordDTO.getEmployeeEmail(), resetPasswordDTO.getEmployeeName());
        return updatedEmployee;

    }

    @Override
    public EmployeeDetail confirmPassword(ResetPasswordDTO resetPasswordDTO) throws TsmException {
        EmployeeDetail oldEmployee = this.employeeDetailRepository.findByEmail(resetPasswordDTO.getEmployeeEmail());
        EmployeeDetail employeeDetail = new EmployeeDetail();


        if (passwordEncoder.matches(resetPasswordDTO.getOldPassword(), oldEmployee.getPassword())) {

            employeeDetail.setCreatedBy(oldEmployee.getCreatedBy());
            employeeDetail.setCreatedDate(oldEmployee.getCreatedDate());
            employeeDetail.setEmployeeName(oldEmployee.getEmployeeName());
            employeeDetail.setUsername(oldEmployee.getUsername());
            employeeDetail.setPassword(customUserDetails.passwordEncoder().encode(resetPasswordDTO.getNewPassword()));
            employeeDetail.setEmail(oldEmployee.getEmail());
            employeeDetail.setJoiningDate(oldEmployee.getJoiningDate());
            employeeDetail.setRelievingDate(oldEmployee.getRelievingDate());
            employeeDetail.setUpdatedBy(resetPasswordDTO.getAdminName());
            employeeDetail.setUpdatedDate(oldEmployee.getUpdatedDate());
            employeeDetail.setEmployeeId(oldEmployee.getEmployeeId());
            EmployeeDetail employeeDetailsUpdated = employeeDetailRepository.saveAndFlush(employeeDetail);

            mailSender.sendEmailUserPasswordChanged(resetPasswordDTO.getEmployeeEmail(), resetPasswordDTO.getEmployeeName());
            return employeeDetailsUpdated;
        } else {
            throw new TsmException("Password not matched please give Correct old password");
        }

    }

    @Override
    public Object createProjectManger(CreateProjectManagerRequestDTO createProjectManagerRequestDTO)
            throws TsmException {
        
        EmployeeDetail oldEmployee = this.employeeDetailRepository.findByEmail(createProjectManagerRequestDTO.getEmail());
        EmployeeDetail employeeDetail = new EmployeeDetail();
        if(Checkpassword.isValidPassword(createProjectManagerRequestDTO.getPassword())) {
            if(oldEmployee == null) {
                employeeDetail.setCreatedBy(createProjectManagerRequestDTO.getCreatedBy());
                employeeDetail.setCreatedDate(createProjectManagerRequestDTO.getCreatedDate());
                employeeDetail.setEmployeeName(createProjectManagerRequestDTO.getEmployeeName());
                employeeDetail.setUsername(createProjectManagerRequestDTO.getUsername());
                employeeDetail.setPassword(customUserDetails.passwordEncoder().encode(createProjectManagerRequestDTO.getPassword()));
                employeeDetail.setEmail(createProjectManagerRequestDTO.getEmail());
                employeeDetail.setJoiningDate(createProjectManagerRequestDTO.getJoiningDate());
                employeeDetail.setRelievingDate(createProjectManagerRequestDTO.getRelievingDate());
      
                EmployeeDetail employeeDetailSaved = employeeDetailRepository.saveAndFlush(employeeDetail);
                if(employeeDetailSaved != null) {
                    mailSender.sendEmailUser(createProjectManagerRequestDTO.getEmail(), createProjectManagerRequestDTO.getCreatorEmail(),
                            createProjectManagerRequestDTO.getEmployeeName());
                    RoleMaster roleMaster  = roleMasterRepository.findByRoleName(createProjectManagerRequestDTO.getRole().toString());
                    if(roleMaster != null ) {
                        UserRole userRole = new UserRole();
                        UserRolePK rolePK = new UserRolePK();
                        userRole.setUserRoleId(rolePK);
                        userRole.setEmployeeDetail(employeeDetailSaved);
                        userRole.setRoleMaster(roleMaster);
                        userRoleRepository.save(userRole); 
                    }else {
                        return "\"Give correct role name i.e.s ADMIN,MANAGER,EMPLOYEE\"";
                    }
                   
                }
            }else {
                throw new TsmException("Employee email Already Exixts");
            }
        }else {
            throw new TsmException("Please give Valid Password.Password Must contains one uppercase,lowercase,sepcial characterand numeric and length-8");
        }
       
        return "\"created\"";
    }

}
