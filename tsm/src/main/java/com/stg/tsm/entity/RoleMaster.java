package com.stg.tsm.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the role_master database table.
 */
@Entity
@Table(name = "role_master")
@NamedQuery(name = "RoleMaster.findAll", query = "SELECT r FROM RoleMaster r")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleMaster implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ROLE_ID")
    private int roleId;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Temporal(TemporalType.DATE)
    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "ROLE_NAME")
    private String roleName;

    @Column(name = "UPDATED_BY")
    private String updatedBy;

    @Temporal(TemporalType.DATE)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    //bi-directional many-to-one association to FunctionRole
    @OneToMany(mappedBy = "roleMaster")
    @JsonManagedReference(value = "function-role-master")
    private List<FunctionRole> functionRoles;

    //bi-directional many-to-one association to UserRole
    @OneToMany(mappedBy = "roleMaster")
    @JsonManagedReference(value = "userRole-roleMaster")
    private List<UserRole> userRoles;


    public FunctionRole addFunctionRole(FunctionRole functionRole) {
        getFunctionRoles().add(functionRole);
        functionRole.setRoleMaster(this);

        return functionRole;
    }

    public FunctionRole removeFunctionRole(FunctionRole functionRole) {
        getFunctionRoles().remove(functionRole);
        functionRole.setRoleMaster(null);

        return functionRole;
    }


    public UserRole addUserRole(UserRole userRole) {
        getUserRoles().add(userRole);
        userRole.setRoleMaster(this);

        return userRole;
    }

    public UserRole removeUserRole(UserRole userRole) {
        getUserRoles().remove(userRole);
        userRole.setRoleMaster(null);

        return userRole;
    }

}