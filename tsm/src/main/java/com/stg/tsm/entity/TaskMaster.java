package com.stg.tsm.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalTime;

import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the task_master database table.
 * 
 */
@Entity
@Table(name="task_master")
@NamedQuery(name="TaskMaster.findAll", query="SELECT t FROM TaskMaster t")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	@GeneratedValue(strategy = GenerationType.AUTO)
	private TaskMasterPK id;

	@Column(name="CREATED_BY")
	private String createdBy;


	@Column(name="CREATED_DATE")
	private Date createdDate;

	@Temporal(TemporalType.DATE)
	private Date date;

	private LocalTime efforts;

	@Column(name="TASK_NAME")
	private String taskName;

	@Column(name="UPDATED_BY")
	private String updatedBy;


	@Column(name="UPDATED_DATE")
	private Date updatedDate;

	//bi-directional many-to-one association to ProjectAssignment
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ASSIGNMENT_ID", insertable = false, updatable = false)
	@JsonBackReference(value = "project-assignment")
	private ProjectAssignment projectAssignment;

	//bi-directional many-to-one association to UserstoryMaster
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="USER_STORY_ID", insertable = false, updatable = false)
	@JsonBackReference(value = "userstory-master")
	private UserstoryMaster userstoryMaster;


}