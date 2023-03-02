package com.stg.tsm.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the parameter_detail database table.
 * 
 */
@Entity
@Table(name="parameter_detail")
@NamedQuery(name="ParameterDetail.findAll", query="SELECT p FROM ParameterDetail p")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParameterDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="PARAMETER_DETAIL_ID")
	private int parameterDetailId;

	@Column(name="CREATED_BY")
	private String createdBy;

	@Temporal(TemporalType.DATE)
	@Column(name="CREATED_DATE")
	private Date createdDate;

	private String description;

	@Column(name="UPDATED_BY")
	private String updatedBy;

	@Temporal(TemporalType.DATE)
	@Column(name="UPDATED_DATE")
	private Date updatedDate;

	//bi-directional many-to-one association to FunctionRole
	@OneToMany(mappedBy="parameterDetail")
	@JsonManagedReference(value = "parameter-detail-function")
	private List<FunctionRole> functionRoles;

	//bi-directional many-to-one association to ParameterMaster
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="PARAMETER_ID")
	@JsonBackReference(value="parameter-master")
	private ParameterMaster parameterMaster;



	public FunctionRole addFunctionRole(FunctionRole functionRole) {
		getFunctionRoles().add(functionRole);
		//functionRole.setParameterDetail(this);

		return functionRole;
	}

	public FunctionRole removeFunctionRole(FunctionRole functionRole) {
		getFunctionRoles().remove(functionRole);
		//functionRole.setParameterDetail(null);

		return functionRole;
	}


}