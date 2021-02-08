package com.pactera.service;

import com.pactera.dao.ComposeTargetEntityRepository;
import com.pactera.dao.JobEntityRepository;
import com.pactera.dao.TargetComposeReportEntityRepository;
import com.pactera.dao.TargetDataEntityRepository;
import com.pactera.entity.ComposeTargetEntity;
import com.pactera.entity.TargetComposeReportEntity;
import com.pactera.entity.TargetDataEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by hx on 2021/1/29
 */
@Service
public class ReportDataBuildService {

    @Autowired
    private TargetComposeReportEntityRepository targetComposeReportEntityRepository;
    @Autowired
    private ComposeTargetEntityRepository composeTargetEntityRepository;
    @Autowired
    private TargetDataEntityRepository targetDataEntityRepository;
    @Autowired
    private JobEntityRepository jobEntityRepository;

    public List<TargetComposeReportEntity> getReportInfo(String reportId,String targetWD){
        return targetComposeReportEntityRepository.findAllByParams(reportId,targetWD);
    }

    public List<ComposeTargetEntity> getComposeTargetInfo(String composeTargetTypeId,String isReport,String status){
        return composeTargetEntityRepository.findAllByParams(composeTargetTypeId,isReport,status);
    }

    public List<TargetDataEntity> getTargetData(String composeTargetId, String dateTime){
        return targetDataEntityRepository.getTargetData(composeTargetId,dateTime);
    }

    public void updateExeStatus(String exeStatus, String failDesc, Timestamp lastTime, Integer id){
        jobEntityRepository.updateJobExeStatus(exeStatus,failDesc,lastTime,id);
    }
}
