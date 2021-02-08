package com.pactera.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by XXXXXX on 2018/6/4 14:09
 */
@Entity
@Table(name = "JOB_ENTITY")
@Data
@Accessors(chain = true)
@NamedStoredProcedureQuery(name = "ps_business_index_cross",procedureName = "ps_business_index_cross",parameters = {
        @StoredProcedureParameter(mode = ParameterMode.IN,name = "date",type = String.class),
        @StoredProcedureParameter(mode = ParameterMode.IN,name = "psType",type = String.class),
        @StoredProcedureParameter(mode = ParameterMode.IN,name = "param",type = String.class),
        @StoredProcedureParameter(mode = ParameterMode.IN,name = "composeTargetTypeId",type = String.class),
        @StoredProcedureParameter(mode = ParameterMode.IN,name = "groupType",type = String.class),
})
public class JobEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;            //job名称
    private String jobGroup;        //job组名
    private String cron;            //执行的cron
    private String jobClass;        //程序执行该job的逻辑实体类
    private String description;     //job描述信息
    private String failDesc;        //失败信息
    private String status;          //job的状态,这里设置为OPEN/CLOSE且只有该值为OPEN才会执行该Job
    private String exeStatus;       //job的执行状态，展示该job的执行结果 success-成功 fail-失败
    private Timestamp lastTime;     //job的最后执行时间
}
