package com.stg.tsm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the function_role database table.
 * 
 */
@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FunctionRolePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="role_id")
	private int roleId;

	@Column(name="function_id")
	private String function_id;


}