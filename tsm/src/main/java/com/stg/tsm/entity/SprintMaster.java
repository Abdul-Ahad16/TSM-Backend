package com.stg.tsm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the sprint_master database table.
 * 
 */
@Entity
@Table(name="sprint_master")
@NamedQuery(name="SprintMaster.findAll", query="SELECT s FROM SprintMaster s")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SprintMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="SPRINT_ID")
	private int sprintId;

	@Column(name="CREATED_BY")
	private String createdBy;

	@Temporal(TemporalType.DATE)
	@Column(name="CREATED_DATE")
	private Date createdDate;

	@Temporal(TemporalType.DATE)
	@Column(name="END_DATE")
	private Date endDate;

	@Column(name="SPRINT_DESCRIPTION")
	private String sprintDescription;

	@Column(name="SPRINT_NAME")
	private String sprintName;

	@Temporal(TemporalType.DATE)
	@Column(name="START_DATE")
	private Date startDate;

	@Column(name="UPDATED_BY")
	private String updatedBy;

	@Temporal(TemporalType.DATE)
	@Column(name="UPDATED_DATE")
	private Date updatedDate;

	//bi-directional many-to-one association to ProjectApplicationDetail
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="APPLICATION_ID")
	@JsonBackReference(value = "project-application-detail")
	private ProjectApplicationDetail projectApplicationDetail;

	//bi-directional many-to-one association to ProjectMaster
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="PROJECT_ID")
	@JsonBackReference(value = "project-master")
	private ProjectMaster projectMaster;

	//bi-directional many-to-one association to UserstoryMaster
	@OneToMany(mappedBy="sprintMaster")
	@JsonManagedReference(value="userStroy-master")
	private List<UserstoryMaster> userstoryMasters;


	public UserstoryMaster addUserstoryMaster(UserstoryMaster userstoryMaster) {
		getUserstoryMasters().add(userstoryMaster);
		userstoryMaster.setSprintMaster(this);

		return userstoryMaster;
	}

	public UserstoryMaster removeUserstoryMaster(UserstoryMaster userstoryMaster) {
		getUserstoryMasters().remove(userstoryMaster);
		userstoryMaster.setSprintMaster(null);

		return userstoryMaster;
	}

}