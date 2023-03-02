package com.stg.tsm.repos;


import org.springframework.data.jpa.repository.JpaRepository;

import com.stg.tsm.entity.FunctionRole;
import com.stg.tsm.entity.FunctionRolePK;

public interface FunctionRoleRepository extends JpaRepository<FunctionRole, FunctionRolePK> {
}