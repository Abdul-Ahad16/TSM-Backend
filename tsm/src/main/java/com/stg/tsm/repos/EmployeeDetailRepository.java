package com.stg.tsm.repos;

import com.stg.tsm.dto.EmployeeRoleDTO;
import com.stg.tsm.entity.EmployeeDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeDetailRepository extends JpaRepository<EmployeeDetail, Integer> {
    EmployeeDetail findByUsername(String username);

   EmployeeDetail  findByEmail(String email);
   
////   @Query(value="select * from stg_tsm.employee_detail where EMPLOYEE_ID=?1",nativeQuery = true)
//   EmployeeDetail findById(int empId);
    
    
    @Query("SELECT new com.stg.tsm.dto.EmployeeRoleDTO(ed.employeeId, rm.roleName,pd.description) \n" +
            "FROM ParameterDetail pd, ParameterMaster pm, FunctionRole fr, \n" +
            "UserRole ur, RoleMaster rm, EmployeeDetail ed \n" +
            "WHERE ur.employeeDetail =  ed.employeeId \n" +
            "and rm.roleId = ur.roleMaster \n" +
            "and fr.roleMaster = ur.roleMaster \n" +
            "and fr.parameterDetail = pd.parameterDetailId \n" +
            "and pm.parameterId=pd.parameterMaster \n" +
            "and ed.employeeId=?1")
    List<EmployeeRoleDTO> getEmpRoleFunctions(int employeeId);
    
    
    
}