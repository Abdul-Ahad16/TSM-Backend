package com.stg.tsm.service;

import java.util.List;

import com.stg.tsm.dto.ProjectAssociationResponseDTO;
import com.stg.tsm.exception.MobileTsmException;

public interface MobileProjectAssociationService {
	
	public abstract List<ProjectAssociationResponseDTO>  getAllActivityByproject(int projectId) throws MobileTsmException;
	
}
