package com.stg.tsm.service;

import java.util.List;

import com.stg.tsm.dto.UserstoryCreateDTO;
import com.stg.tsm.entity.UserstoryMaster;
import com.stg.tsm.exception.TsmException;

public interface UserstoryMasterService {

     UserstoryMaster createUserstoryMaster(int sprintId,UserstoryCreateDTO userstoryMaster) throws TsmException;
     List<UserstoryMaster> getUserstoryMasterList(int sprintId) throws TsmException;
     List<UserstoryMaster> deleteUserstoryMaster(int userstoryId) throws TsmException;
     String updateUserstoryMaster(UserstoryMaster UserstoryMaster) throws TsmException;
}
