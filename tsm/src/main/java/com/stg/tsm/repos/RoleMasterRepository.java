package com.stg.tsm.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stg.tsm.entity.RoleMaster;

public interface RoleMasterRepository extends JpaRepository<RoleMaster, Integer> {
    
    RoleMaster findByRoleName(String roleName);
}