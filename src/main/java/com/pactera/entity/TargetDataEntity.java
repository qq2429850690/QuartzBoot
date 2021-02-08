package com.pactera.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

/**
 * Created by hx on 2021/2/1
 */
@Entity
@Table(name = "IOMP_TARGETDATA")
@Data
@Accessors(chain = true)
public class TargetDataEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                            //自增ID
    @Column(name = "DATAGROUP")
    private String dataGroup;                   //数据维度
    @Column(name = "COMPOSETARGETID")
    private String composeTargetId;             //合成指标ID
    @Column(name = "COMPOSETARGETDATA")
    private String composeTargetData;           //合成指标值
    @Column(name = "STATISTICALDATE")
    private String statisticalDate;             //统计日期
}
