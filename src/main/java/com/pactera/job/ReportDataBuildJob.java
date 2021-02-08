package com.pactera.job;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pactera.controller.param.CellDefineDTO;
import com.pactera.dao.ReportBatchEntityRepository;
import com.pactera.entity.ComposeTargetEntity;
import com.pactera.entity.ReportBatchEntity;
import com.pactera.entity.TargetComposeReportEntity;
import com.pactera.entity.TargetDataEntity;
import com.pactera.service.ReportDataBuildService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by hx on 2021/1/29
 */
@Component
@Slf4j
public class ReportDataBuildJob implements Job {

    @Autowired
    private ReportDataBuildService reportDataBuildService;
    @Autowired
    private ReportBatchEntityRepository reportBatchEntityRepository;

    private Integer totalColumns = 1;
    private Integer totalHeadRows = 1;
    private List reportHead1 = new ArrayList<>();
    private List<String> reportHead2 = new ArrayList<>();
    private List<String> reportDataParam = new ArrayList<>();
    private Map<String,Object> reportData = new HashMap<>();

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        long startTime = System.currentTimeMillis();
        JobDataMap map = jobExecutionContext.getMergedJobDataMap();
        String targetWD = map.getString("jobGroup");
        String reportId = map.getString("name");

        List<Map> firstHeadRow = new ArrayList<>();
        List<Map> secondHeadRow = new ArrayList<>();
        List<Map> thirdHeadRow = new ArrayList<>();
        List<Map> fourthHeadRow = new ArrayList<>();
        ReportBatchEntity reportBatchEntity = new ReportBatchEntity();
        try {
            reportHead2.add("");
            List<TargetComposeReportEntity> targetComposeReportEntities = reportDataBuildService.getReportInfo(reportId,targetWD);//查询 报表配置表 获得指定报表中包含哪些指标类型

            totalColumns = Integer.valueOf(getTotalColumnsAndRows(targetComposeReportEntities).get("totalColumns").toString());
            totalHeadRows = Integer.valueOf(getTotalColumnsAndRows(targetComposeReportEntities).get("totalRows").toString());
            targetComposeReportEntities = (List<TargetComposeReportEntity>) getTotalColumnsAndRows(targetComposeReportEntities).get("targetComposeReportEntities_new");

            int colNo = 1;
            thirdHeadRow.add(new CellDefineDTO().setTitle("支行").setNum(1).setCol(1).setRow(3).setZhanRow(3).getCellMap());
            for (int i = 0;i < targetComposeReportEntities.size(); i++) {
                String composeTargetTypeId = targetComposeReportEntities.get(i).getComposeTargetTypeId();
                List<ComposeTargetEntity> composeTargetList = reportDataBuildService.getComposeTargetInfo(composeTargetTypeId, "0", "0");//查询 合成指标表 获得用于报表的合成指标列表
                if (i == 0) {
                    thirdHeadRow.add(new CellDefineDTO().setTitle(composeTargetTypeId).setZhanRow(2).setNum(composeTargetList.size()).setRow(3).setCol(2).getCellMap());
                    colNo += composeTargetList.size();
                    if (targetComposeReportEntities.size() > 1){
                        thirdHeadRow.add(new CellDefineDTO().setTitle("其中").setZhanRow(1).setNum(totalColumns-1-composeTargetList.size()).setRow(3).setCol(colNo+1).getCellMap());
                    }
                } else {
                    fourthHeadRow.add(new CellDefineDTO().setTitle(composeTargetTypeId).setZhanRow(1).setNum(composeTargetList.size()).setRow(4).setCol(colNo+1).getCellMap());
                    colNo += composeTargetList.size();
                }
                for (int j = 0;j < composeTargetList.size(); j++) {
                    ComposeTargetEntity composeTargetEntity = composeTargetList.get(j);
                    String composeTargetId = composeTargetEntity.getComposeTargetId();
                    String composeTargetName = composeTargetEntity.getComposeTargetName();
                    reportDataParam.add(composeTargetId);
                    reportHead2.add(composeTargetName);
                }
            }
            int ii = 1/0;
            firstHeadRow.add(new CellDefineDTO().setTitle("零售银行业务分析报表——贷款维度").setNum(this.totalColumns-2).setCol(1).setRow(1).setZhanRow(1).getCellMap());
            firstHeadRow.add(new CellDefineDTO().setTitle(new SimpleDateFormat("yyyy/MM/dd").format(new Date())).setRow(1).setNum(2).setCol(this.totalColumns-1).setZhanRow(1).getCellMap());
            secondHeadRow.add(new CellDefineDTO().setTitle("个人贷款业务分析表（表1）").setRow(2).setNum(this.totalColumns).setCol(1).setZhanRow(1).getCellMap());

            reportHead1.add(firstHeadRow);
            reportHead1.add(secondHeadRow);
            reportHead1.add(thirdHeadRow);
            reportHead1.add(fourthHeadRow);
            reportData.put("dataHead",reportHead1);
            reportData.put("tHeader",reportHead2);
            long headEndTime = System.currentTimeMillis();
            log.info("#####拼装表头数据  完成 >>>>>>>>>>>>>> 耗时 : {}ms \n ",(headEndTime - startTime));

            String dateTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "%";

            int dataRows = 0;
            for (int i = 0; i < reportDataParam.size(); i++) {
                List<TargetDataEntity> tempTargetData0List = reportDataBuildService.getTargetData(reportDataParam.get(i), dateTime);
                if (tempTargetData0List.size() > dataRows){
                    dataRows = tempTargetData0List.size();
                }
            }
            String columns[][] = new String[dataRows][reportDataParam.size()+1];
            for (int i = 0; i < reportDataParam.size(); i++) {
                List<TargetDataEntity> tempTargetDataList = reportDataBuildService.getTargetData(reportDataParam.get(i), dateTime);
                for (int j = 0; j < tempTargetDataList.size(); j++) {
                    columns[j][0] = String.valueOf(tempTargetDataList.get(j).getDataGroup());
                    columns[j][i+1] = String.valueOf(tempTargetDataList.get(j).getComposeTargetData());
                }
            }
            //表体数据组成的二维矩阵columns[][]转换为List的list集合
            List<List<String>> reportDataList = new ArrayList<>();
            for (int i = 0; i < dataRows; i++) {
                List<String> rowList = new ArrayList<>();
                for (String data : columns[i]) {
                    rowList.add(data);
                }
                reportDataList.add(rowList);
            }
            reportData.put("listArr",reportDataList);
            long bodyEndTime = System.currentTimeMillis();
            log.info("#####拼装表体数据  完成 >>>>>>>>>>>>>> 耗时 : {}ms \n ",(bodyEndTime - headEndTime));

            reportBatchEntity.setReportId(reportId).setTargetWD(targetWD).setTaskId(new Date().getTime()+reportId).setStatus("成功")
                    .setCreateTaskDate(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date())).setMessage("11111")
                    .setReportData(new ObjectMapper().writeValueAsString(reportData));
            reportBatchEntityRepository.save(reportBatchEntity);
            reportDataBuildService.updateExeStatus("success","",new Timestamp(new Date().getTime()),map.getInt("id"));

            long endTime = System.currentTimeMillis();
            log.info("#####报表生成  完成 >>>>>>>>>>>>>> 耗时 : {}ms \n ",(endTime - startTime));
            log.info("Running Job name : {} ", map.getString("name"));
            log.info("Running Job group: {} ", map.getString("jobGroup"));
            log.info("Running Job description : {}", map.getString("jobDescription"));
            log.info(String.format("Running Job cron : %s", map.getString("cronExpression")));
        } catch (Exception e) {
            log.error("printStackTrace ", e);
            reportBatchEntityRepository.save(reportBatchEntity.setReportId(reportId).setTargetWD(targetWD).setTaskId(new Date().getTime()+reportId).setStatus("失败")
                    .setCreateTaskDate(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date())).setMessage(e.getMessage()));
            reportDataBuildService.updateExeStatus("fail",e.getMessage()+"",new Timestamp(new Date().getTime()),map.getInt("id"));

            log.info("#####报表生成  失败 ###### \n");
            log.info("Running Job name : {} ", map.getString("name"));
            log.info("Running Job group: {} ", map.getString("jobGroup"));
            log.info("Running Job description : {}", map.getString("jobDescription"));
            log.info(String.format("Running Job cron : %s", map.getString("cronExpression")));
        }

    }
    private Map<String, Object> getTotalColumnsAndRows(List<TargetComposeReportEntity> targetComposeReportEntities) {
        Map<String,Object> map = new HashMap<>();
        int totalColumns = 1;
        int totalRows = 1;
        List<TargetComposeReportEntity> targetComposeReportEntities_new = new ArrayList<>();
        for (TargetComposeReportEntity targetComposeReportEntity : targetComposeReportEntities) {
            String composeTargetTypeId = targetComposeReportEntity.getComposeTargetTypeId();
            List<ComposeTargetEntity> composeTargetList = reportDataBuildService.getComposeTargetInfo(composeTargetTypeId, "0", "0");//查询 合成指标表 获得用于报表的合成指标列表
            if (composeTargetList.size() > 0) {
                targetComposeReportEntities_new.add(targetComposeReportEntity);
                totalColumns += composeTargetList.size();
            }
        }
        if (targetComposeReportEntities.size() > 1){
            totalRows = 5;
        }else if (targetComposeReportEntities.size() == 1){
            totalRows = 4;
        }else {
            totalRows = 0;
            log.info("该表格为空");
            return null;
        }
        if (totalColumns == 1){
            log.info("该表格为空");
            return null;
        }
        map.put("totalColumns",totalColumns);
        map.put("totalRows",totalRows);
        map.put("targetComposeReportEntities_new",targetComposeReportEntities_new);
        return map;
    }

}
