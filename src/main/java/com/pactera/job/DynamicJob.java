package com.pactera.job;

import com.pactera.service.StoredProcedureService;
import com.pactera.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Created by hx on 2021/1/24
 * :@DisallowConcurrentExecution : 此标记用在实现Job的类上面,意思是不允许并发执行.
 * :注意org.quartz.threadPool.threadCount线程池中线程的数量至少要多个,否则@DisallowConcurrentExecution不生效
 * :假如Job的设置时间间隔为3秒,但Job执行时间是5秒,设置@DisallowConcurrentExecution以后程序会等任务执行完毕以后再去执行,否则会在3秒时再启用新的线程执行
 */
@DisallowConcurrentExecution
@Component
@Slf4j
public class DynamicJob implements Job {

    @Autowired
    private StoredProcedureService storedProcedureService;

    /**
     * 核心方法,Quartz Job真正的执行逻辑.
     *
     * @param executorContext executorContext JobExecutionContext中封装有Quartz运行所需要的所有信息
     * @throws JobExecutionException execute()方法只允许抛出JobExecutionException异常
     */
    @Override
    public void execute(JobExecutionContext executorContext) {

        //JobDetail中的JobDataMap是共用的,从getMergedJobDataMap获取的JobDataMap是全新的对象
        JobDataMap map = executorContext.getMergedJobDataMap();
        String composeTargetTypeId = map.getString("name");
        try {
        storedProcedureService.createTargetData(new SimpleDateFormat("yyyy-MM-dd").format(new Date()),"I","",composeTargetTypeId,"");
        storedProcedureService.updateExeStatus("success","",new Timestamp(new Date().getTime()),map.getInt("id"));
        } catch (Exception e){
            log.error("printStackTrace",e);
            storedProcedureService.updateExeStatus("fail",e.getMessage()+"",new Timestamp(new Date().getTime()),map.getInt("id"));
        }

        log.info("Running Job name : {} ", map.getString("name"));
        log.info("Running Job group: {} ", map.getString("jobGroup"));
        log.info("Running Job description : {}", map.getString("jobDescription"));
        log.info(String.format("Running Job cron : %s", map.getString("cronExpression")));
        long startTime = System.currentTimeMillis();

        long endTime = System.currentTimeMillis();
        log.info(">>>>>>>>>>>>> Running Job has been completed , cost time : {}ms\n ", (endTime - startTime));
    }

}
