package com.stg.tsm.entity;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the employee_detail database table.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employee_detail")
@NamedQuery(name = "EmployeeDetail.findAll", query = "SELECT e FROM EmployeeDetail e")
public class EmployeeDetail implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EMPLOYEE_ID")
    private int employeeId;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Temporal(TemporalType.DATE)
    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "EMPLOYEE_NAME")
    private String employeeName;

    @Column(length = 20, name = "USERNAME")
    @NotBlank(message = "Enter username")
    private String username;

    @Column(name = "PASSWORD")
    @Size(min = 5, message = "min-length of the password must be 5")
    @NotEmpty(message = "Enter password")
    private String password;


    @Column(name = "EMAIL")
    @Email(message = "Enter valid Email")
    private String email;

    @Temporal(TemporalType.DATE)
    @Column(name = "JOINING_DATE")
    private Date joiningDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "RELIEVING_DATE")
    private Date relievingDate;

    @Column(name = "UPDATED_BY")
    private String updatedBy;

    @Temporal(TemporalType.DATE)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;


    //bidirectional many-to-one association to ProjectAssignment
    @OneToMany(mappedBy = "employeeDetail")
    @JsonManagedReference(value = "employee-detail")
    private List<ProjectAssignment> projectAssignments;

    //bidirectional many-to-one association to UserRole
    @OneToMany(mappedBy = "employeeDetail", fetch = FetchType.EAGER)
    @JsonManagedReference(value = "employee-role")
    private List<UserRole> userRoles;


    public ProjectAssignment addProjectAssignment(ProjectAssignment projectAssignment) {
        getProjectAssignments().add(projectAssignment);
        projectAssignment.setEmployeeDetail(this);

        return projectAssignment;
    }

    public ProjectAssignment removeProjectAssignment(ProjectAssignment projectAssignment) {
        getProjectAssignments().remove(projectAssignment);
        projectAssignment.setEmployeeDetail(null);

        return projectAssignment;
    }

    public UserRole addUserRole(UserRole userRole) {
        getUserRoles().add(userRole);
        userRole.setEmployeeDetail(this);

        return userRole;
    }

    public UserRole removeUserRole(UserRole userRole) {
        getUserRoles().remove(userRole);
        userRole.setEmployeeDetail(null);

        return userRole;
    }

}