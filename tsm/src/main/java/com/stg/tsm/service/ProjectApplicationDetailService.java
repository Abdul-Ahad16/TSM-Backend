package com.stg.tsm.service;

import com.stg.tsm.entity.ProjectApplicationDetail;
import com.stg.tsm.entity.SprintMaster;
import com.stg.tsm.exception.TsmException;

import java.util.List;

public interface ProjectApplicationDetailService {

    List<ProjectApplicationDetail> readAll();

    List<SprintMaster> readSprintMastersById(int projectApplicationId) throws TsmException;
}
