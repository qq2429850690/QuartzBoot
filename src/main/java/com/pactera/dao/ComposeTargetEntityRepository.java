package com.pactera.dao;

import com.pactera.entity.ComposeTargetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ComposeTargetEntityRepository extends JpaRepository<ComposeTargetEntity,Long> {


    @Transactional
    @Modifying
    @Query(value = "select * from IOMP_COMPOSETARGET where IsReport = :isReport and status = :status and ComposeTargetTypeId = :composeTargetTypeId order by SerialNumber;",nativeQuery = true)
    List<ComposeTargetEntity> findAllByParams(String composeTargetTypeId,String isReport,String status);
}
