package com.stg.tsm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.List;


/**
 * The persistent class for the project_application_detail database table.
 * 
 */
@Entity
@Table(name="project_application_detail")
@NamedQuery(name="ProjectApplicationDetail.findAll", query="SELECT p FROM ProjectApplicationDetail p")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectApplicationDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="APPLICATION_ID")
	private int applicationId;

	@Column(name="APPLICATION_NAME")
	private String applicationName;

	@Column(name="CREATED_BY")
	private String createdBy;

	@Column(name="CREATED_DATE")
	private String createdDate;

	@Column(name="UPDATED_BY")
	private String updatedBy;

	@Column(name="UPDATED_DATE")
	private String updatedDate;
	
	@Column(name="APPLICATION_NICK_NAME")
    private String applicationNickName;

	//bi-directional many-to-one association to ProjectMaster
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="PROJECT_ID")
	@JsonBackReference(value = "project-master")
	private ProjectMaster projectMaster;

	//bi-directional many-to-one association to SprintMaster
	@OneToMany(mappedBy="projectApplicationDetail")
	@JsonManagedReference(value = "project-application-detail")
	private List<SprintMaster> sprintMasters;

	@OneToMany(mappedBy="projectApplicationDetail")
    @JsonManagedReference(value="project-assignment")
    private List<ProjectAssignment> projectAssignments;

	public SprintMaster addSprintMaster(SprintMaster sprintMaster) {
		getSprintMasters().add(sprintMaster);
		sprintMaster.setProjectApplicationDetail(this);

		return sprintMaster;
	}

	public SprintMaster removeSprintMaster(SprintMaster sprintMaster) {
		getSprintMasters().remove(sprintMaster);
		sprintMaster.setProjectApplicationDetail(null);

		return sprintMaster;
	}
	public int getProjectId(){
		return  this.projectMaster.getProjectId();
	}
	
	public String getProjectName() {
	    return this.projectMaster.getProjectName();
	}
	
	public String getProjectNickName() {
	    return this.projectMaster.getProjectNickName();
	}
	
	
	public ProjectAssignment addProjectAssignment(ProjectAssignment projectAssignment) {
        getProjectAssignments().add(projectAssignment);
        projectAssignment.setProjectApplicationDetail(this);

        return projectAssignment;
    }

    public ProjectAssignment removeProjectAssignment(ProjectAssignment projectAssignment) {
        getProjectAssignments().remove(projectAssignment);
        projectAssignment.setProjectApplicationDetail(null);

        return projectAssignment;
    }

}