package com.stg.tsm.service.Impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Tuple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stg.tsm.dto.ProjectAssociationResponseDTO;
import com.stg.tsm.exception.MobileTsmException;
import com.stg.tsm.repos.ProjectAssociationRepo;
import com.stg.tsm.service.MobileProjectAssociationService;

@Service
public class MobileProjectAssociationServiceImpl implements MobileProjectAssociationService {

	@Autowired
    private ProjectAssociationRepo projectAssociationRepo;
	
	@Override
	public List<ProjectAssociationResponseDTO> getAllActivityByproject(int projectId) throws MobileTsmException {
		List<Tuple> activites = projectAssociationRepo.findAllActivityByProjectId(projectId);
		if(activites.isEmpty()) {
			throw new MobileTsmException(null, "activities not found");
		}
        List<ProjectAssociationResponseDTO> associationResponseDTOs = new ArrayList<>();
        for (Tuple activity : activites) {
            ProjectAssociationResponseDTO associationResponseDTO = new ProjectAssociationResponseDTO();
            associationResponseDTO.setParameterDetailId(activity.get(0) != null ? (int) activity.get(0) : 0);
            associationResponseDTO.setDescription(activity.get(1) != null ? (String) activity.get(1) : null);
            associationResponseDTOs.add(associationResponseDTO);
        }
        return associationResponseDTOs;
	}

}
