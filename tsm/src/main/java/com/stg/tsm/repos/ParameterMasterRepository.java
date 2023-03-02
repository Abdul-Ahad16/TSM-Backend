package com.stg.tsm.repos;


import org.springframework.data.jpa.repository.JpaRepository;

import com.stg.tsm.entity.ParameterMaster;

public interface ParameterMasterRepository extends JpaRepository<ParameterMaster, Integer> {
    
    ParameterMaster findByparameterDescription(String parameterDescription);
    
}