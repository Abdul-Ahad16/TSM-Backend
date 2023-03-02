package com.stg.tsm.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MobileProjectAssociationResponseDTO {

	private List<ProjectAssociationResponseDTO> data;
	private String message;
	private int status;
	
}
