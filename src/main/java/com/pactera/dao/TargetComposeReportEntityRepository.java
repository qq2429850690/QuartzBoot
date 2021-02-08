package com.pactera.dao;

import com.pactera.entity.TargetComposeReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TargetComposeReportEntityRepository extends JpaRepository<TargetComposeReportEntity,Long> {

    @Transactional
    @Modifying//增删改必须有这个注解
    @Query(value = "select * from IOMP_TARGETRCOMPOSEREPORT where ReportId = :reportId and TargetWD = :targetWD order by SerialNumber",nativeQuery = true)
    List<TargetComposeReportEntity> findAllByParams(String reportId,String targetWD);
}
