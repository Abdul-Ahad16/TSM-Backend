package com.stg.tsm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the user_role database table.
 * 
 */
@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRolePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="USER_ROLE")
	private int userRole;


}