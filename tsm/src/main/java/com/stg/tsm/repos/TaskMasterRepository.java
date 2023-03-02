package com.stg.tsm.repos;


import java.util.Date;
import java.util.List;
import javax.persistence.Tuple;
import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.stg.tsm.entity.TaskMaster;
import com.stg.tsm.entity.TaskMasterPK;

public interface TaskMasterRepository extends JpaRepository<TaskMaster, TaskMasterPK> {

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM task_master  WHERE task_id = :id",nativeQuery = true)
	public int removeById(@Param("id") int id);
	
//	@Query(value = "DELETE FROM task_master  WHERE task_id = :id",nativeQuery = true)
//	public int removeByTaskId(@Param("id") int id);
	
	@Query(value="select  distinct um.userstory_id ,tm.assignment_id ,tm.task_id ,um.userstory_name ,task_name ,tm.efforts,tm.created_date ,tm.date,um.USERSTORY_DESCRIPTION,sm.SPRINT_NAME\r\n"
	        + "        from task_master tm,sprint_master sm, userstory_master um\r\n"
	        + "        where sm.SPRINT_ID = um.SPRINT_ID and tm.USER_STORY_ID = um.USERSTORY_ID\r\n"
	        + "        and tm.ASSIGNMENT_ID = ?1 and sm.SPRINT_ID = ?2  ",nativeQuery = true)
	public List<Tuple> searchTask(int asssignmnetId,int sprintId,Pageable pageable);
	
	@Query(value="select distinct count(*)  \r\n"
	        + "        from task_master tm, sprint_master sm, userstory_master um\r\n"
	        + "        where sm.SPRINT_ID = um.SPRINT_ID and tm.USER_STORY_ID = um.USERSTORY_ID\r\n"
	        + "        and tm.ASSIGNMENT_ID = ?1 and sm.SPRINT_ID = ?2  ;",nativeQuery = true)
	public int countsearchTask(int asssignmnetId,int sprintI);
	
	
	@Query(value="select  distinct um.userstory_id ,tm.assignment_id ,tm.task_id ,um.userstory_name ,task_name ,tm.efforts,tm.created_date ,tm.date,um.USERSTORY_DESCRIPTION,sm.SPRINT_NAME \r\n"
            + "        from task_master tm, sprint_master sm, userstory_master um\r\n"
            + "        where sm.SPRINT_ID = um.SPRINT_ID and tm.USER_STORY_ID = um.USERSTORY_ID\r\n"
            + "        and tm.ASSIGNMENT_ID = ?1 and tm.date between ?2 and ?3",nativeQuery = true)
    public List<Tuple> allTasks(int assignmnetId,String startDate,String endDate,Pageable pageable);
	
	
	@Query(value="select distinct count(*)  \r\n"
            + "        from task_master tm, sprint_master sm, userstory_master um\r\n"
            + "        where sm.SPRINT_ID = um.SPRINT_ID and tm.USER_STORY_ID = um.USERSTORY_ID\r\n"
            + "        and tm.ASSIGNMENT_ID = ?1  and tm.date between ?2 and ?3  \r\n"
            + "        order by ?4 ?5 ;",nativeQuery = true)
    public int countallTasks(int assignmnetId,String startDate ,String endDate ,String sortingFeild, String sortingOrder);
	
	
	 @Query(value = "SELECT  tm.date, um.USERSTORY_NAME, tm.TASK_NAME,SEC_TO_TIME(SUM(time_to_sec(tm.efforts))) as work_in_hours_per_day , um.USERSTORY_ID\r\n"
	            + "FROM task_master tm,\r\n"
	            + "userstory_master um\r\n"
	            + " where tm.USER_STORY_ID = um.USERSTORY_ID and\r\n"
	            + " SPRINT_ID = ?1 \r\n"
	            + " and ASSIGNMENT_ID= ?2 and date between  ?3  And  ?4   \r\n"
	            + " group by tm.DATE;", nativeQuery = true)
	    public List<Tuple> calendarDataBySprintId( int sprintId, @Param("assignmentId") int assignmentId,
	             String startDate,  String endDate);
	 
	 @Query(value = "SELECT  tm.date,SEC_TO_TIME(SUM(time_to_sec(tm.efforts)))  as work_in_hours_per_day\r\n"
	 		+ "FROM task_master tm where tm.ASSIGNMENT_ID = ?1  and tm.date = ?2 group by tm.DATE;",  nativeQuery = true)
	 public Tuple effortsOfDate(int assignmentId,Date date);
	 
	 @Query(value = "SELECT  tm.date, um.USERSTORY_NAME, tm.TASK_NAME,SEC_TO_TIME(SUM(time_to_sec(tm.efforts)))  as work_in_hours_per_day , um.USERSTORY_ID\r\n"
             + "FROM task_master tm,\r\n"
             + "userstory_master um\r\n"
             + " where tm.USER_STORY_ID = um.USERSTORY_ID and\r\n"
             + " ASSIGNMENT_ID= ?1 and date between  ?2  And  ?3   \r\n"
             + " group by tm.DATE;", nativeQuery = true)
	   public List<Tuple>  calendarData(int assignmentId,
               String startDate,  String endDate);

	 @Query(value = "select * from task_master where assignment_id = ?1", nativeQuery = true)
	 public List<TaskMaster> findAllByProjectAssignmnetId(@Param("projectAssignmnetId") int projectAssignmnetId);
	 
}