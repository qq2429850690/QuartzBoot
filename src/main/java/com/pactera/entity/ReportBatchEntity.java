package com.pactera.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by hx on 2021/1/29
 */
@Entity
@Table(name = "IOMP_REPORTBATCH")
@Data
@Accessors(chain = true)
public class ReportBatchEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "TASKID")
    private String taskId;
    @Column(name = "REPORTID")
    private String reportId;
    @Column(name = "CREATETASKDATE")
    private String createTaskDate;
    @Column(name = "STATUS")
    private String status;
    @Column(name = "MESSAGE")
    private String message;
    @Column(name = "TARGETWD")
    private String targetWD;
    @Column(name = "REPORTDATA")
    private String reportData;
}
