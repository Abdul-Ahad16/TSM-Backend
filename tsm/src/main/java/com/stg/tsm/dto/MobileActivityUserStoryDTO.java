package com.stg.tsm.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MobileActivityUserStoryDTO {
	private List<UserstoryCreateMobileDTO> userstories;
	private List<ProjectAssociationResponseDTO> projectActivity;
}
