package com.stg.tsm.service;

import com.stg.tsm.entity.ParameterMaster;
import com.stg.tsm.exception.TsmException;

public interface ParameterService {
    
    ParameterMaster getActivity(String activity) throws TsmException;

}
