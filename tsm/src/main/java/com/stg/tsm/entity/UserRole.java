package com.stg.tsm.entity;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


/**
 * The persistent class for the user_role database table.
 * 
 */
@Entity
@Table(name="user_role")
@NamedQuery(name="UserRole.findAll", query="SELECT u FROM UserRole u")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRole implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private UserRolePK userRoleId;

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

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATED_ON")
	private Date createdOn;

	@Column(name="EMP_ID")
	private Integer empId;

	@Column(name="I_EMPL_INFO_SEQ")
	private Integer iEmplInfoSeq;

	@Column(name="I_LOGON")
	private String iLogon;

	@Column(name="I_LOGON_UPD")
	private String iLogonUpd;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="T_STMP_ADD")
	private Date tStmpAdd;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="T_STMP_UPD")
	private Date tStmpUpd;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="UPDATED_ON")
	private Date updatedOn;

	@Column(name="USER_ROLE_STATUS")
	private String userRoleStatus;

	//bi-directional many-to-one association to EmployeeDetail
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="EMPLOYEE_ID", updatable = false)
	@JsonBackReference(value = "employee-role")
	private EmployeeDetail employeeDetail;

	//bi-directional many-to-one association to RoleMaster
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ROLE_ID", updatable = false)
	@JsonBackReference(value = "userRole-roleMaster")
	private RoleMaster roleMaster;

	
}