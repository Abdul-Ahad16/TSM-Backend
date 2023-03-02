package com.stg.tsm.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the project_master database table.
 * 
 */
@Entity
@Table(name="project_master")
@NamedQuery(name="ProjectMaster.findAll", query="SELECT p FROM ProjectMaster p")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="PROJECT_ID")
	private int projectId;

	@Column(name="CREATED_BY")
	private String createdBy;

	@Temporal(TemporalType.DATE)
	@Column(name="CREATED_DATE")
	private Date createdDate;

	@Column(name="FTE_COUNT_FORD_OFFSHORE")
	private Integer fteCountFordOffshore;

	@Column(name="FTE_COUNT_FORD_ONSITE")
	private Integer fteCountFordOnsite;

	@Column(name="FTE_COUNT_OTHER_VENDOR_OFFSHORE")
	private Integer fteCountOtherVendorOffshore;

	@Column(name="FTE_COUNT_OTHER_VENDOR_ONSITE")
	private Integer fteCountOtherVendorOnsite;

	@Column(name="FTE_COUNT_STG_OFFSHORE")
	private Integer fteCountStgOffshore;

	@Column(name="FTE_COUNT_STG_ONSITE")
	private Integer fteCountStgOnsite;

	@Column(name="LEVEL1_MANAGER")
	private String level1Manager;

	@Column(name="LEVEL2_MANAGER")
	private String level2Manager;

	@Column(name="LEVEL3_MANAGER")
	private String level3Manager;

	@Temporal(TemporalType.DATE)
	@Column(name="PROJECT_END_DATE")
	private Date projectEndDate;

	@Column(name="PROJECT_NAME")
	private String projectName;

	@Temporal(TemporalType.DATE)
	@Column(name="PROJECT_START_DATE")
	private Date projectStartDate;

	@Column(name="UPDATED_BY")
	private String updatedBy;

	@Temporal(TemporalType.DATE)
	@Column(name="UPDATED_DATE")
	private Date updatedDate;
	
	@Column(name="PROJECT_NICK_NAME")
	private String projectNickName;

	//bi-directional many-to-one association to ProjectApplicationDetail
	@OneToMany(mappedBy="projectMaster")
	@JsonManagedReference(value = "project-master")
	private List<ProjectApplicationDetail> projectApplicationDetails;


	//bi-directional many-to-one association to CustomerMaster
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CUSTOMER_ID")
	@JsonBackReference(value="customer-master")
	private CustomerMaster customerMaster;

	//bi-directional many-to-one association to SprintMaster
	@OneToMany(mappedBy="projectMaster")
	@JsonManagedReference(value = "project-master")
	private List<SprintMaster> sprintMasters;
	
	@OneToMany(mappedBy="projectMaster")
	@JsonManagedReference(value = "project-association")
    private List<ProjectAssociation> projectAssociations;


	public ProjectApplicationDetail addProjectApplicationDetail(ProjectApplicationDetail projectApplicationDetail) {
		getProjectApplicationDetails().add(projectApplicationDetail);
		projectApplicationDetail.setProjectMaster(this);

		return projectApplicationDetail;
	}

	public ProjectApplicationDetail removeProjectApplicationDetail(ProjectApplicationDetail projectApplicationDetail) {
		getProjectApplicationDetails().remove(projectApplicationDetail);
		projectApplicationDetail.setProjectMaster(null);

		return projectApplicationDetail;
	}


	

	public SprintMaster addSprintMaster(SprintMaster sprintMaster) {
		getSprintMasters().add(sprintMaster);
		sprintMaster.setProjectMaster(this);

		return sprintMaster;
	}

	public SprintMaster removeSprintMaster(SprintMaster sprintMaster) {
		getSprintMasters().remove(sprintMaster);
		sprintMaster.setProjectMaster(null);

		return sprintMaster;
	}

}