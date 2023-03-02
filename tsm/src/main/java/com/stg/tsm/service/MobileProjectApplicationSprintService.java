package com.stg.tsm.service;

import com.stg.tsm.dto.CreateSprintMasterRequestDTO;
import com.stg.tsm.dto.CreateSprintMasterResponseDTO;
import com.stg.tsm.dto.MobileProjectApplicationSprintDTO;
import com.stg.tsm.dto.MobileSprintMasterResponseDTO;
import com.stg.tsm.exception.MobileTsmException;

public interface MobileProjectApplicationSprintService {
	
	public abstract MobileProjectApplicationSprintDTO getProjectApplicationSprint(int emplyoeeId) throws MobileTsmException;
	
	public abstract MobileSprintMasterResponseDTO getSprints(int applicationId) throws MobileTsmException;
	
	public abstract CreateSprintMasterResponseDTO createSprint(CreateSprintMasterRequestDTO createSprintMasterRequestDTO) throws MobileTsmException;

}
