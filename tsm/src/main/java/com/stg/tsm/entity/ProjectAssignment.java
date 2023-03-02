package com.stg.tsm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the project_assignment database table.
 * 
 */
@Entity
@Table(name="project_assignment")
@NamedQuery(name="ProjectAssignment.findAll", query="SELECT p FROM ProjectAssignment p")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectAssignment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ASSIGNMENT_ID")
	private int assignmentId;

	@Temporal(TemporalType.DATE)
	@Column(name="ASSIGNMENT_END_DATE")
	private Date assignmentEndDate;

	@Column(name="ASSIGNMENT_ROLE")
	private Integer assignmentRole;

	@Temporal(TemporalType.DATE)
	@Column(name="ASSIGNMENT_START_DATE")
	private Date assignmentStartDate;

	private Boolean billable;

	@Column(name="CREATED_BY")
	private String createdBy;

	@Temporal(TemporalType.DATE)
	@Column(name="CREATED_DATE")
	private Date createdDate;

	@Column(name="UPDATED_BY")
	private String updatedBy;

	@Temporal(TemporalType.DATE)
	@Column(name="UPDATED_DATE")
	private Date updatedDate;

	//bi-directional many-to-one association to EmployeeDetail
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="EMPLOYEE_ID")
	@JsonBackReference(value="employee-detail")
	private EmployeeDetail employeeDetail;

	//bi-directional many-to-one association to ProjectMaster
	@ManyToOne(fetch=FetchType.LAZY)
	@JsonBackReference(value="project-assignment")
	@JoinColumn(name="APPLICATION_ID")
	private ProjectApplicationDetail projectApplicationDetail;

	//bi-directional many-to-one association to TaskMaster
	@OneToMany(mappedBy="projectAssignment")
	private List<TaskMaster> taskMasters;


	public TaskMaster addTaskMaster(TaskMaster taskMaster) {
		getTaskMasters().add(taskMaster);
		taskMaster.setProjectAssignment(this);

		return taskMaster;
	}

	public TaskMaster removeTaskMaster(TaskMaster taskMaster) {
		getTaskMasters().remove(taskMaster);
		taskMaster.setProjectAssignment(null);

		return taskMaster;
	}

}