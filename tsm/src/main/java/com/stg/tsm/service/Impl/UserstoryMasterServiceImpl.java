package com.stg.tsm.service.Impl;

import com.stg.tsm.dto.UserstoryCreateDTO;
import com.stg.tsm.entity.SprintMaster;
import com.stg.tsm.entity.UserstoryMaster;
import com.stg.tsm.exception.TsmException;
import com.stg.tsm.repos.SprintMasterRepository;
import com.stg.tsm.repos.UserstoryMasterRepository;
import com.stg.tsm.service.UserstoryMasterService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author saikrishnan
 * @author jenifer
 *
 */

@Service
public class UserstoryMasterServiceImpl implements  UserstoryMasterService{
    @Autowired
    private UserstoryMasterRepository userstoryMasterRepository;

    @Autowired
    private SprintMasterRepository sprintMasterRepository;

    @Override
    public UserstoryMaster createUserstoryMaster(int sprintId, UserstoryCreateDTO userstoryMasterDTO) throws TsmException {
        SprintMaster sprintMaster = sprintMasterRepository.findById(sprintId).
                orElseThrow(()-> new TsmException("Sprint not Found with given id : "+sprintId));
        
        UserstoryMaster userstoryMaster = new UserstoryMaster();

        if(sprintMaster != null){
            userstoryMaster.setSprintMaster(sprintMaster);
            userstoryMaster.setCreatedBy(userstoryMasterDTO.getCreatedBy());
            userstoryMaster.setCreatedDate(userstoryMasterDTO.getCreatedDate());
            userstoryMaster.setEstimatedStorypoints(userstoryMasterDTO.getEstimatedStorypoints());
            userstoryMaster.setUserstoryDescription(userstoryMasterDTO.getUserstoryDescription());
            userstoryMaster.setPlannedEfforts(userstoryMasterDTO.getPlannedEfforts());
            userstoryMaster.setUserstoryName(userstoryMasterDTO.getUserstoryName());
            try {
                return  userstoryMasterRepository.save(userstoryMaster);
            }catch (Exception e){
                throw new TsmException("Userstory is not Created");
            }
        }else{
            throw new TsmException("Sprint not Found with given id : "+sprintId);
        }
    }

	@Override
	public List<UserstoryMaster> getUserstoryMasterList(int sprintId) throws TsmException {
		 SprintMaster sprintMasterTemp = sprintMasterRepository.findById(sprintId).
	                orElseThrow(()-> new TsmException("Sprint not Found with given id : "+sprintId));
		List<UserstoryMaster> userstoryListTemp=sprintMasterTemp.getUserstoryMasters();
		
        if (userstoryListTemp!=null) {
              return userstoryListTemp;
        } else {
              throw new TsmException("No userstoryList Found");

        }

	}
	
    @Override
    public List<UserstoryMaster> deleteUserstoryMaster(int userstoryId) throws TsmException {
                    Optional<UserstoryMaster> userstoryMasterTemp=userstoryMasterRepository.findById(userstoryId);
                    if (userstoryMasterTemp!=null) {
                    	userstoryMasterRepository.deleteById(userstoryId);
                    } else {
                                    throw new TsmException("No Userstory found with the given ID");
                    }
                    return userstoryMasterRepository.findAll();
    }
    @Override
    public String updateUserstoryMaster(UserstoryMaster userstoryMaster) throws TsmException {
                    Optional<UserstoryMaster> userstoryMasterTemp=userstoryMasterRepository.findById(userstoryMaster.getUserstoryId());
                    if(userstoryMasterTemp!=null) {
                    	userstoryMasterRepository.save(userstoryMaster);
                    }
                    else {
                                    throw new TsmException("No UserstoryMaster found to update");
                    }
                    return "UserstoryMaster Updated Successfully";
                    
    }

}
