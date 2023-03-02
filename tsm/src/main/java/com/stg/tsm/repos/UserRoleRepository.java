package com.stg.tsm.repos;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.stg.tsm.entity.UserRole;
import com.stg.tsm.entity.UserRolePK;

public interface UserRoleRepository extends JpaRepository<UserRole, UserRolePK> {
	  @Query(value="select * from stg_tsm.user_role where EMPLOYEE_ID=?1",nativeQuery=true)
	UserRole  findByEmpId(Integer empId);
}