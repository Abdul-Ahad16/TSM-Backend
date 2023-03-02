package com.stg.tsm.repos;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stg.tsm.entity.ProjectApplicationDetail;


@Repository
public interface ProjectApplicationDetailRepository extends JpaRepository<ProjectApplicationDetail, Integer> {
	
	
 

}