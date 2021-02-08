package com.pactera.dao;

import com.pactera.entity.JobEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

/**
 * Created by XXXXXX on 2018/6/4 14:27
 */
public interface JobEntityRepository extends JpaRepository<JobEntity, Long> {

    JobEntity getById(Integer id);

    @Transactional
    @Procedure(name = "ps_business_index_cross")
    void createTargetData(String date,String psType,Object param,String composeTargetTypeId,String groupType);

    @Transactional
    @Modifying//增删改必须有这个注解
    @Query(value = "update job_entity set exe_status =:exeStatus,fail_desc = :failDesc,last_time = :lastTime  where id = :id",nativeQuery = true)
    void updateJobExeStatus(String exeStatus,String failDesc, Timestamp lastTime, Integer id);

}
