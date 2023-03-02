package com.stg.tsm.service.Impl;

import com.stg.tsm.dto.AllTaskDTO;
import com.stg.tsm.dto.TaskListDTO;
import com.stg.tsm.dto.UserstorySearchDTO;
import com.stg.tsm.entity.*;
import com.stg.tsm.exception.TsmException;
import com.stg.tsm.repos.ProjectAssignmentRepository;
import com.stg.tsm.repos.TaskMasterRepository;
import com.stg.tsm.repos.UserstoryMasterRepository;
import com.stg.tsm.service.TaskMasterService;
import com.stg.tsm.utils.ExcelSheet;

import java.io.ByteArrayInputStream;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;

/**
 * 
 * @author saikrishnan
 * @author jenifer
 *
 */

@Service
public class TaskMasterServiceImpl implements TaskMasterService {

    @Autowired
    private TaskMasterRepository taskMasterRepository;

    @Autowired
    private UserstoryMasterRepository userstoryMasterRepository;

    @Autowired
    private ProjectAssignmentRepository projectAssignmentRepository;

    private  static  final  String TASK_NOT_FOUND = "Task Master is not found";

    @Override
    public TaskMaster createTask(int userstoryId, int assignmentId, TaskMaster taskMaster) throws TsmException {
        UserstoryMaster userstoryMaster = userstoryMasterRepository.findById(userstoryId)
                .orElseThrow(() -> new TsmException("User story not found: " + userstoryId));
        ProjectAssignment projectAssignment = projectAssignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new TsmException("Project Assignment is not found : " + assignmentId));
        if (userstoryMaster != null && projectAssignment != null) {
            try {
                taskMaster.setProjectAssignment(projectAssignment);
                taskMaster.setUserstoryMaster(userstoryMaster);
                return taskMasterRepository.save(taskMaster);
            } catch (Exception e) {
                throw new TsmException(TASK_NOT_FOUND);
            }
        } else {
            throw new TsmException(TASK_NOT_FOUND);
        }
    }

    @Override
    public List<Tuple> readAll(UserstorySearchDTO userstorySearchDTO) throws TsmException {

        Pageable sorting = null;
        int month = LocalDate.now().getMonthValue();

        int year = LocalDate.now().getYear();
        
        int endDay = LocalDate.now().lengthOfMonth();

        String startDate = year + "-" + month + "-" + "01";

        String endDate = year + "-" + month + "-" + endDay;

        if (userstorySearchDTO.getSortOrder().equals("ASC")) {
            sorting = PageRequest.of(userstorySearchDTO.getStartingRecordNumber(), userstorySearchDTO.getPageSize(),
                    Sort.by(userstorySearchDTO.getSortField()).ascending());
        } else {
            sorting = PageRequest.of(userstorySearchDTO.getStartingRecordNumber(), userstorySearchDTO.getPageSize(),
                    Sort.by(userstorySearchDTO.getSortField()).descending());
        }

        return this.taskMasterRepository.allTasks(userstorySearchDTO.getAssignmnetId(), startDate, endDate, sorting );

    }

    @Override
    public List<Tuple> readUserstoryBySprintAndApplication(UserstorySearchDTO searchDTO) throws TsmException {

        Pageable sorting = null;

        if (searchDTO.getSortOrder().equals("ASC")) {
            sorting = PageRequest.of(searchDTO.getStartingRecordNumber(), searchDTO.getPageSize(),
                    Sort.by(searchDTO.getSortField()).ascending());
        } else {
            sorting = PageRequest.of(searchDTO.getStartingRecordNumber(), searchDTO.getPageSize(),
                    Sort.by(searchDTO.getSortField()).descending());
        }

        return this.taskMasterRepository.searchTask(searchDTO.getAssignmnetId(), searchDTO.getSprintId(),
                sorting);
    }

    @Override
    public TaskListDTO taskList(UserstorySearchDTO searchDTO) throws TsmException {
        if (searchDTO.getSprintId() == 0) {
            List<Tuple> result = this.readAll(searchDTO);
            return  setValuesToDto(result,searchDTO);
        } else {
            List<Tuple> result = this.readUserstoryBySprintAndApplication(searchDTO);
            return setValuesToDto(result,searchDTO);
        }

    }

    @Override
    public TaskMaster updateTaskMaster(int userstoryId, int assignmentId, int taskId, TaskMaster taskMaster)
            throws TsmException {
        UserstoryMaster userstoryMaster = userstoryMasterRepository.findById(userstoryId)
                .orElseThrow(() -> new TsmException("User story not found: " + userstoryId));
        ProjectAssignment projectAssignment = projectAssignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new TsmException("Project Assignment is not found : " + assignmentId));

        TaskMaster master = taskMasterRepository.findById(taskMaster.getId())
                .orElseThrow(() -> new TsmException(TASK_NOT_FOUND));

        if (userstoryMaster != null && projectAssignment != null) {
            try {
                taskMaster.setCreatedDate(master.getCreatedDate());
                taskMaster.setCreatedBy(master.getCreatedBy());
                taskMaster.setProjectAssignment(projectAssignment);
                taskMaster.setUserstoryMaster(userstoryMaster);
                return taskMasterRepository.save(taskMaster);
            } catch (Exception e) {
                throw new TsmException(TASK_NOT_FOUND);
            }
        } else {
            throw new TsmException(TASK_NOT_FOUND);
        }
    }

    @Override
    public int deleteTaskmasterById(int id) throws TsmException {
        return taskMasterRepository.removeById(id);
    }

    @Override
    public int countAll(UserstorySearchDTO userstorySearchDTO) throws TsmException {
        
        int month = LocalDate.now().getMonthValue();

        int year = LocalDate.now().getYear();
        
        int endDay = LocalDate.now().lengthOfMonth();

        String startDate = year + "-" + month + "-" + "01";

        String endDate = year + "-" + month + "-" + endDay;

        return taskMasterRepository.countallTasks(userstorySearchDTO.getAssignmnetId(),startDate,endDate, userstorySearchDTO.getSortField(), userstorySearchDTO.getSortOrder());
    }

    @Override
    public int countAllBySprintId(UserstorySearchDTO userstorySearchDTO) throws TsmException {

        return taskMasterRepository.countsearchTask(userstorySearchDTO.getAssignmnetId(),
                userstorySearchDTO.getSprintId());
    }

    public TaskListDTO setValuesToDto( List<Tuple> result, UserstorySearchDTO searchDTO){
        List<AllTaskDTO> allTaskDTOs = new ArrayList<>();
        for (Tuple taskMaster : result) {
            if (taskMaster.get(0) != null && taskMaster.get(2) != null && taskMaster.get(3) != null) {
                AllTaskDTO allTaskDTO = new AllTaskDTO();
                extracted(taskMaster, allTaskDTO);
                allTaskDTOs.add(allTaskDTO);
            }
        }
        TaskListDTO dto = new TaskListDTO();
        dto.setAllTaskDTOs(allTaskDTOs);
        dto.setSortField(searchDTO.getSortField());
        dto.setSortType(searchDTO.getSortOrder());
        dto.setTotalCount(searchDTO.getSprintId() > 0? this.countAllBySprintId(searchDTO) : this.countAll(searchDTO));
        return dto;
    }

    private static void extracted(Tuple taskMaster, AllTaskDTO allTaskDTO) {
        allTaskDTO.setUserstoryId(taskMaster.get(0) != null ? (int) taskMaster.get(0) : 0);
        allTaskDTO.setAssignmentId(taskMaster.get(1) != null ? (int) taskMaster.get(1) : 0);
        allTaskDTO.setTaskId(taskMaster.get(2) != null ? (int) taskMaster.get(2) : 0);
        allTaskDTO.setUserstoryName(taskMaster.get(3) != null ? (String) taskMaster.get(3) : "");
        allTaskDTO.setTaskName(taskMaster.get(4) != null ? (String) taskMaster.get(4) : "");
        allTaskDTO.setEfforts(toLocalTime(taskMaster.get(5) != null ? (Time) taskMaster.get(5) : null));
        allTaskDTO.setCreatedDate(taskMaster.get(6) != null ? (Date) taskMaster.get(6) : null);
        allTaskDTO.setDate(taskMaster.get(7) != null ? (Date) taskMaster.get(7) : null);
        allTaskDTO.setUserstoryDescription(taskMaster.get(8) != null ? (String) taskMaster.get(8) : "");
        allTaskDTO.setSprintName(taskMaster.get(9) != null ? (String) taskMaster.get(9) : "");
    }

    @Override
    public ByteArrayInputStream load(List<AllTaskDTO> taskDTO) {

        return ExcelSheet.tutorialsToExcel(taskDTO);
    }

    public static LocalTime toLocalTime(java.sql.Time time) {
        if(time != null){
            return time.toLocalTime();
        }else {
            return null;
        }
    }

}
