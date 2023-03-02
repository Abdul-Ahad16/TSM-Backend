package com.stg.tsm.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * The persistent class for the project_association database table.
 * 
 */
@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectAssociation implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="PROJECT_ASSOCIATION_ID")
    private int projectAssociationId;

    //bi-directional many-to-one association to ParameterMaster
    @ManyToOne
    @JoinColumn(name="PARAMETER_MASTER_ID")
    @JsonBackReference(value="parameter-project-association")
    private ParameterMaster parameterMaster;

    //bi-directional many-to-one association to ProjectMaster
    @ManyToOne
    @JoinColumn(name="PROJECT_MASTER_ID")
    @JsonBackReference(value = "project-association")
    private ProjectMaster projectMaster;

  

}