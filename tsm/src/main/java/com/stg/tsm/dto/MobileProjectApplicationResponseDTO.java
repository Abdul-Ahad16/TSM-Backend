package com.stg.tsm.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MobileProjectApplicationResponseDTO {
	private int projectId;

	private String projectName;

	private String projectNickName;

	private List<MobileApplicationSprintResponseDTO> applicationSprintResponseDTOs;
}
