package com.stg.tsm.service.Impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.persistence.Tuple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stg.tsm.dto.TaskMasterDTO;
import com.stg.tsm.entity.ProjectAssignment;
import com.stg.tsm.entity.TaskMaster;
import com.stg.tsm.entity.TaskMasterPK;
import com.stg.tsm.entity.UserstoryMaster;
import com.stg.tsm.exception.MobileTsmException;
import com.stg.tsm.exception.TsmException;
import com.stg.tsm.repos.ProjectAssignmentRepository;
import com.stg.tsm.repos.TaskMasterRepository;
import com.stg.tsm.repos.UserstoryMasterRepository;
import com.stg.tsm.service.MobileTaskmasterService;
import com.stg.tsm.service.ProjectAssignmentService;
import com.stg.tsm.service.TaskMasterService;

@Service
public class MobileTaskmasterServiceImpl implements MobileTaskmasterService {
    @Autowired
    private TaskMasterRepository taskMasterRepository;

    @Autowired
    private UserstoryMasterRepository userstoryMasterRepository;

    @Autowired
    private ProjectAssignmentRepository projectAssignmentRepository;
    
    @Autowired
    private ProjectAssignmentService assignmentService;
    
    @Autowired
    private TaskMasterService taskMasterService;
    
    private  static  final  String TASK_NOT_FOUND = "Task Master is not found";
	
    @Override
    public TaskMaster createTasks(int empId, int applicatiopnId,int userStoryId, TaskMaster taskMaster) throws MobileTsmException {
    	if(taskMaster.getEfforts()==null) {
    		throw new MobileTsmException(null, "Efforts asiigned is null");
    	}
    	UserstoryMaster userstoryMaster = userstoryMasterRepository.findById(userStoryId)
                .orElseThrow(() -> new MobileTsmException(null,"User story not found: " + userStoryId));
        
        int projectAssignmentId = assignmentService.projectAssignmentByProjectAndEmployeeId(applicatiopnId, empId);
        
        ProjectAssignment projectAssignment = projectAssignmentRepository.findById(projectAssignmentId)
                .orElseThrow(() -> new MobileTsmException(null,"Project Assignment is not found : " + projectAssignmentId));
        
        TaskMasterPK masterPK=new TaskMasterPK();
        masterPK.setAssignmentId(projectAssignmentId);
        masterPK.setUserStoryId(userStoryId);
        taskMaster.setId(masterPK);
        System.out.println(taskMaster.getDate());
        Tuple result = taskMasterRepository.effortsOfDate(projectAssignmentId, taskMaster.getDate());
        long secs= taskMaster.getEfforts().toSecondOfDay();
        System.out.println(taskMaster.getEfforts());
        System.out.println(secs);
        System.out.println(result.toString());
        String str = result.get(1).toString();
        System.out.println(str);
        LocalTime time = LocalTime.parse(str);
        System.out.println(time);
        long secs2 = time.toSecondOfDay();
        System.out.println(secs + secs2);
        TaskMaster response;
        if((secs+secs2)<86400) {
        	
        try {
        	response = taskMasterService.createTask(userStoryId, projectAssignmentId, taskMaster);
        	return response;
		} catch (TsmException e) {
			throw new MobileTsmException(null, "TASK_NOT_FOUND");
		}
        }else
        {
        	throw new MobileTsmException(null, "Total efforts for day exceeding 24 hrs.");
        }
        
        
    }
        
        @Override
        public TaskMaster updateTaskMaster(int userstoryId, int assignmentId, int taskId, TaskMaster taskMaster)
                throws MobileTsmException {
        	if(taskMaster.getEfforts()==null) {
        		throw new MobileTsmException(null, "Efforts asiigned is null");
        	}
            UserstoryMaster userstoryMaster = userstoryMasterRepository.findById(userstoryId)
                    .orElseThrow(() -> new MobileTsmException(null,"User story not found: " + userstoryId));
            ProjectAssignment projectAssignment = projectAssignmentRepository.findById(assignmentId)
                    .orElseThrow(() -> new MobileTsmException(null,"Project Assignment is not found : " + assignmentId));

            TaskMaster master = taskMasterRepository.findById(taskMaster.getId())
                    .orElseThrow(() -> new MobileTsmException(null,TASK_NOT_FOUND));
            Tuple result = taskMasterRepository.effortsOfDate(assignmentId, taskMaster.getDate());
            long secs= taskMaster.getEfforts().toSecondOfDay();
            String str = result.get(1).toString();
            LocalTime time = LocalTime.parse(str);
            long secs2 = time.toSecondOfDay();
            if((secs+secs2)<86400) {
            if (userstoryMaster != null && projectAssignment != null) {
                try {
                    taskMaster.setCreatedDate(master.getCreatedDate());
                    taskMaster.setCreatedBy(master.getCreatedBy());
                    taskMaster.setProjectAssignment(projectAssignment);
                    taskMaster.setUserstoryMaster(userstoryMaster);
                    return taskMasterRepository.save(taskMaster);
                } catch (Exception e) {
                    throw new MobileTsmException(null,TASK_NOT_FOUND);
                }
            } else {
                throw new MobileTsmException(null,TASK_NOT_FOUND);
            }
            }
            else {
            	throw new MobileTsmException(null, "Total efforts for day exceeding 24 hrs.");
            }
        }
        
        @Override
        public int deleteTaskmasterById(TaskMasterDTO taskMaster) throws MobileTsmException {
        	TaskMasterPK taskMasterPK =  new TaskMasterPK();
        	taskMasterPK.setTaskId(taskMaster.getTaskId());
        	taskMasterPK.setAssignmentId(taskMaster.getAssignmentId());
        	taskMasterPK.setUserStoryId(taskMaster.getUserStoryId());
        	  TaskMaster master = taskMasterRepository.findById(taskMasterPK)
                      .orElseThrow(() -> new MobileTsmException(null,TASK_NOT_FOUND));

        	try {
        		return taskMasterRepository.removeById(taskMasterPK.getTaskId());
			} catch (Exception e) {
				throw new MobileTsmException(null,TASK_NOT_FOUND);
			}
          }
//        	else {
//        		throw new MobileTsmException(null,TASK_NOT_FOUND);
//        	}
//        }
    }	
