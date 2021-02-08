package com.pactera.dao;

import com.pactera.entity.TargetDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface TargetDataEntityRepository extends JpaRepository<TargetDataEntity,Long> {

    @Transactional
    @Modifying
    @Query(value = "SELECT * FROM iomp_targetdata WHERE COMPOSETARGETID = :composeTargetId AND STATISTICALDATE LIKE :dateTime GROUP BY DATAGROUP;",nativeQuery = true)
    List<TargetDataEntity> getTargetData(String composeTargetId, String dateTime);


}
