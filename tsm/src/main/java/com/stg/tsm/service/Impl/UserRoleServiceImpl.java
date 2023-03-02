package com.stg.tsm.service.Impl;

import com.stg.tsm.entity.EmployeeDetail;
import com.stg.tsm.entity.RoleMaster;
import com.stg.tsm.entity.UserRole;
import com.stg.tsm.exception.TsmException;
import com.stg.tsm.repos.EmployeeDetailRepository;
import com.stg.tsm.repos.RoleMasterRepository;
import com.stg.tsm.repos.UserRoleRepository;
import com.stg.tsm.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private EmployeeDetailRepository employeeDetailRepository;

    @Autowired
    private RoleMasterRepository roleMasterRepository;

    @Override
    public UserRole createUserRoleAssignment(UserRole userRole) throws TsmException {

        EmployeeDetail employeeDetail = employeeDetailRepository.findById(userRole.getEmployeeDetail().getEmployeeId()).orElseThrow(() -> new TsmException("employeeId doesn't exist"));

        RoleMaster roleMaster = roleMasterRepository.findById(userRole.getRoleMaster().getRoleId()).orElseThrow(() -> new TsmException("roleId doesn't exist"));

        if(employeeDetail != null && roleMaster != null){
            return userRoleRepository.save(userRole);
        }else{
            return null;
        }

    }
}
