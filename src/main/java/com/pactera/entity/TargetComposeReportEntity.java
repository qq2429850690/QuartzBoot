package com.pactera.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by hx on 2021/1/12
 */
@Entity
@Table(name = "IOMP_TARGETRCOMPOSEREPORT")
@Data
@Accessors(chain = true)
public class TargetComposeReportEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;//该ID为自增ID，其值由数据库维护，能唯一标识某一条数据
    @Column(name = "REPORTID")
    private String reportId;
    @Column(name = "COMPOSETARGETTYPEID")
    private String composeTargetTypeId;
    @Column(name = "TARGETWD")
    private String targetWD;
    @Column(name = "SERIALNUMBER")
    private String serialNumber;
    @Column(name = "UPDATETIME")
    private String updateTime;
}
