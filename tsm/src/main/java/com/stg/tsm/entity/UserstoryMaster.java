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
 * The persistent class for the userstory_master database table.
 * 
 */
@Entity
@Table(name="userstory_master")
@NamedQuery(name="UserstoryMaster.findAll", query="SELECT u FROM UserstoryMaster u")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserstoryMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="USERSTORY_ID")
	private int userstoryId;

	@Column(name="CREATED_BY")
	private String createdBy;

	@Temporal(TemporalType.DATE)
	@Column(name="CREATED_DATE")
	private Date createdDate;

	@Column(name="ESTIMATED_STORYPOINTS")
	private String estimatedStorypoints;

	@Column(name="PLANNED_EFFORTS")
	private String plannedEfforts;

	@Column(name="UPDATED_BY")
	private String updatedBy;

	@Temporal(TemporalType.DATE)
	@Column(name="UPDATED_DATE")
	private Date updatedDate;

	@Column(name="USERSTORY_DESCRIPTION")
	private String userstoryDescription;

	@Column(name="USERSTORY_NAME")
	private String userstoryName;

	//bi_directional many-to-one association to TaskMaster
	@OneToMany(mappedBy="userstoryMaster")
	private List<TaskMaster> taskMasters;

	//bi_directional many-to-one association to SprintMaster
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="SPRINT_ID")
	@JsonBackReference(value="userStroy-master")
	private SprintMaster sprintMaster;

}