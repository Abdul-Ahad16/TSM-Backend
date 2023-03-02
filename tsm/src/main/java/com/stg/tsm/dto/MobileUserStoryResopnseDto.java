package com.stg.tsm.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MobileUserStoryResopnseDto {
	private List<UserstoryCreateMobileDTO> data;
	private String messagae;
	private int status;
}
