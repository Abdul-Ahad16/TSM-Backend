package com.stg.tsm.service;

import com.stg.tsm.dto.MobileTaskEffortsRequestDTO;
import com.stg.tsm.dto.MobileTaskMasterResponseDTO;
import com.stg.tsm.exception.MobileTsmException;

public interface TargetedHoursWithUserStoriesService {

	public abstract MobileTaskMasterResponseDTO getUserStoriesEfforts(MobileTaskEffortsRequestDTO userStoriesDto) throws MobileTsmException;
}
