package com.stg.tsm.dto;

import org.springframework.data.domain.Sort;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pagination {

	private int pageNumber = 0;
	private int pageSize = 10;
	private Sort.Direction direction = Sort.Direction.ASC;
	private String sortBy = "sprintName";
	
}
