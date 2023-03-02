package com.stg.tsm.repos;

import java.util.List;

import javax.persistence.Tuple;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.stg.tsm.entity.ProjectAssociation;

public interface ProjectAssociationRepo extends JpaRepository<ProjectAssociation, Integer> {
    
    
    @Query(value="select distinct pd.PARAMETER_DETAIL_ID, DESCRIPTION  from project_association pa , parameter_master pm,  parameter_detail pd  \r\n"
            + "where pa.PARAMETER_MASTER_ID = pm.PARAMETER_ID and  pm.PARAMETER_ID = pd.PARAMETER_ID\r\n"
            + " and PROJECT_MASTER_ID = ?1 ",nativeQuery=true)
    List<Tuple> findAllActivityByProjectId(int projectId);

}
