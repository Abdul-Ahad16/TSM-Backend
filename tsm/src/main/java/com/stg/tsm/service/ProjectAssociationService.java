package com.stg.tsm.service;

import java.util.List;

import com.stg.tsm.dto.ProjectAssociationResponseDTO;

public interface ProjectAssociationService {
    
    List<ProjectAssociationResponseDTO>  getAllActivityByproject(int projectId);

}
