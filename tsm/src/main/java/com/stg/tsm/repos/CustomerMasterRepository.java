package com.stg.tsm.repos;


import org.springframework.data.jpa.repository.JpaRepository;

import com.stg.tsm.entity.CustomerMaster;

public interface CustomerMasterRepository extends JpaRepository<CustomerMaster, Integer> {
}