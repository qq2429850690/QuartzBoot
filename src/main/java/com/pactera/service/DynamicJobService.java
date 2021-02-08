package com.pactera.service;

import com.pactera.dao.JobEntityRepository;
import com.pactera.entity.JobEntity;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by XXXXXX on 2018/6/4 14:25
 */
@Slf4j
@Service
public class DynamicJobService {

    @Autowired
    private JobEntityRepository repository;

    //通过Id获取Job
    public JobEntity getJobEntityById(Integer id) {
        return repository.getById(id);
    }

    //从数据库中加载获取到所有Job
    public List<JobEntity> loadJobs() {
        return repository.findAll();
    }

    //获取JobDataMap.(Job参数对象)
    public JobDataMap getJobDataMap(JobEntity job) {
        JobDataMap map = new JobDataMap();
        map.put("id",job.getId());
        map.put("name", job.getName());
        map.put("jobGroup", job.getJobGroup());
        map.put("cronExpression", job.getCron());
        map.put("jobClass", job.getJobClass());
        map.put("jobDescription", job.getDescription());
        map.put("failDesc", job.getFailDesc());
        map.put("status", job.getStatus());
        map.put("exeStatus",job.getExeStatus());
        map.put("lastTime",job.getLastTime());
        return map;
    }

    //获取JobDetail,JobDetail是任务的定义,而Job是任务的执行逻辑,JobDetail里会引用一个Job Class来定义
    public JobDetail getJobDetail(JobKey jobKey, String description, JobDataMap map) {
        Class<? extends Job> jobClass = null;
        try {
            jobClass = (Class<? extends Job>) Class.forName(map.get("jobClass").toString());
        } catch (ClassNotFoundException e) {
            log.error("printStackTrace ", e);

        }
        return JobBuilder.newJob(jobClass)
                .withIdentity(jobKey)
                .withDescription(description)
                .setJobData(map)
                .storeDurably()
                .build();
    }

    //获取Trigger (Job的触发器,执行规则)
    public Trigger getTrigger(JobEntity job) {
        return TriggerBuilder.newTrigger()
                .withIdentity(job.getName(), job.getJobGroup())
                .withSchedule(CronScheduleBuilder.cronSchedule(job.getCron()))
                .build();
    }

    //获取JobKey,包含Name和Group
    public JobKey getJobKey(JobEntity job) {
        return JobKey.jobKey(job.getName(), job.getJobGroup());
    }

}
