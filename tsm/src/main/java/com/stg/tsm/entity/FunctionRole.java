package com.stg.tsm.entity;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.sql.Timestamp;


/**
 * The persistent class for the function_role database table.
 * 
 */
@Entity
@Table(name="function_role")
@NamedQuery(name="FunctionRole.findAll", query="SELECT f FROM FunctionRole f")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FunctionRole implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FunctionRolePK id;

	@Column(name="CREATED_BY")
	private String createdBy;

	@Column(name="CREATED_DATE")
	private Timestamp createdDate;

	private String status;

	@Column(name="UPDATED_BY")
	private String updatedBy;

	
	@Column(name="UPDATED_DATE")
	private Date updatedDate;

	//bi-directional many-to-one association to ParameterDetail
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="FUNCTION_VALUE", insertable = false, updatable = false)
	@JsonBackReference(value = "parameter-detail-function")
	private ParameterDetail parameterDetail;

	//bi-directional many-to-one association to RoleMaster
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ROLE_ID", insertable = false, updatable = false)
	@JsonBackReference(value = "function-role-master")
	private RoleMaster roleMaster;

	
}