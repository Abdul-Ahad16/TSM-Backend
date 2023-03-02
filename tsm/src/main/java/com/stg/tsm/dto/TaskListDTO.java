package com.stg.tsm.dto;



import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskListDTO {
	
	private long totalCount;
	private int limit;
	private int offset;
	private String sortField;
	private String sortType;
	private  List<AllTaskDTO> allTaskDTOs ;

	
	
	
	

}
