package com.stg.tsm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the customer_master database table.
 * 
 */
@Entity
@Table(name="customer_master")
@NamedQuery(name="CustomerMaster.findAll", query="SELECT c FROM CustomerMaster c")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="CUSTOMER_ID")
	private int customerId;

	@Column(name="CREATED_BY")
	private String createdBy;

	@Temporal(TemporalType.DATE)
	@Column(name="CREATED_DATE")
	private Date createdDate;

	@Temporal(TemporalType.DATE)
	@Column(name="CUSTOMER_END_DATE")
	private Date customerEndDate;

	@Column(name="CUSTOMER_NAME")
	private String customerName;

	@Temporal(TemporalType.DATE)
	@Column(name="CUSTOMER_START_DATE")
	private Date customerStartDate;

	@Column(name="UPDATED_BY")
	private String updatedBy;

	@Temporal(TemporalType.DATE)
	@Column(name="UPDATED_DATE")
	private Date updatedDate;

	//bi-directional many-to-one association to ProjectMaster
	@OneToMany(mappedBy="customerMaster")
	@JsonManagedReference(value="customer-master")
	private List<ProjectMaster> projectMasters;


	public ProjectMaster addProjectMaster(ProjectMaster projectMaster) {
		getProjectMasters().add(projectMaster);
		projectMaster.setCustomerMaster(this);
		return projectMaster;
	}

	public ProjectMaster removeProjectMaster(ProjectMaster projectMaster) {
		getProjectMasters().remove(projectMaster);
		projectMaster.setCustomerMaster(null);

		return projectMaster;
	}

}