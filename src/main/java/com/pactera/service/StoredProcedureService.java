package com.pactera.service;

import com.pactera.dao.JobEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * Created by hx on 2021/1/25
 */
@Service
public class StoredProcedureService {
    @Autowired
    private JobEntityRepository repository;

    public void createTargetData(String date,String psType,Object param,String composeTargetTypeId,String groupType){
        repository.createTargetData(date,psType,param,composeTargetTypeId,groupType);
    }

    public void updateExeStatus(String exeStatus,String failDesc, Timestamp lastTime, Integer id){
        repository.updateJobExeStatus(exeStatus,failDesc,lastTime,id);
    }
}
