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
 * The persistent class for the parameter_master database table.
 * 
 */
@Entity
@Table(name="parameter_master")
@NamedQuery(name="ParameterMaster.findAll", query="SELECT p FROM ParameterMaster p")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParameterMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="PARAMETER_ID")
	private int parameterId;

	@Column(name="CREATED_BY")
	private String createdBy;

	@Temporal(TemporalType.DATE)
	@Column(name="CREATED_DATE")
	private Date createdDate;

	@Column(name="PARAMETER_DESCRIPTION")
	private String parameterDescription;

	@Column(name="UPDATED_BY")
	private String updatedBy;

	@Temporal(TemporalType.DATE)
	@Column(name="UPDATED_DATE")
	private Date updatedDate;

	//bi-directional many-to-one association to ParameterDetail
	@OneToMany(mappedBy="parameterMaster")
	@JsonManagedReference(value="parameter-master")
	private List<ParameterDetail> parameterDetails;
	
	@OneToMany(mappedBy="parameterMaster")
    @JsonManagedReference(value="parameter-project-association")
	private List<ProjectAssociation> projectAssociations;


	public ParameterDetail addParameterDetail(ParameterDetail parameterDetail) {
		getParameterDetails().add(parameterDetail);
		parameterDetail.setParameterMaster(this);

		return parameterDetail;
	}

	public ParameterDetail removeParameterDetail(ParameterDetail parameterDetail) {
		getParameterDetails().remove(parameterDetail);
		parameterDetail.setParameterMaster(null);

		return parameterDetail;
	}

}