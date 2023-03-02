package com.stg.tsm.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MobileApplicationSprintResponseDTO {

	private int applicationId;
	private String applicationName;
	private String applicationNickName;
	private List<MobileSprintMasterDTO> sprints;

}
