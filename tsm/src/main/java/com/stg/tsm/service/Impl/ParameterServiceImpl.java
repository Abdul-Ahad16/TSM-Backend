package com.stg.tsm.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stg.tsm.entity.ParameterMaster;
import com.stg.tsm.exception.TsmException;
import com.stg.tsm.repos.ParameterMasterRepository;
import com.stg.tsm.service.ParameterService;

@Service
public class ParameterServiceImpl implements ParameterService {
    
    @Autowired
    private ParameterMasterRepository parameterMasterRepository;

    @Override
    public ParameterMaster getActivity(String activity) throws TsmException {

        return parameterMasterRepository.findByparameterDescription(activity);
    }

}
