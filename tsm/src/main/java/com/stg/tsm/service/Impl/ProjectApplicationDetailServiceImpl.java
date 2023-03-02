package com.stg.tsm.service.Impl;

import com.stg.tsm.entity.ProjectApplicationDetail;
import com.stg.tsm.entity.SprintMaster;
import com.stg.tsm.exception.TsmException;
import com.stg.tsm.repos.ProjectApplicationDetailRepository;
import com.stg.tsm.service.ProjectApplicationDetailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 
 * @author saikrishnan
 * @author jenifer
 *
 */

@Service
public class ProjectApplicationDetailServiceImpl implements ProjectApplicationDetailService{

    @Autowired
    private ProjectApplicationDetailRepository projectApplicationDetailRepository;

    
	@Override
	public List<ProjectApplicationDetail> readAll() {
		return projectApplicationDetailRepository.findAll();
	}

	@Override
	public List<SprintMaster> readSprintMastersById(int projectApplicationId) throws TsmException {

		ProjectApplicationDetail projectApplicationDetail = projectApplicationDetailRepository.findById(projectApplicationId).orElseThrow(
				() -> new TsmException("ID not found "  +projectApplicationId )
		);
		return  projectApplicationDetail.getSprintMasters();

	}


}
