package com.pactera.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

/**
 * IOMP_COMPOSETARGET合成指标表
 */
@Entity
@Table(name = "IOMP_COMPOSETARGET")
@Data
@Accessors(chain = true)
public class ComposeTargetEntity implements java.io.Serializable {

	private static final long serialVersionUID = 8003611734837919687L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;//ID
	@Column(name = "COMPOSETARGETID")
	private String composeTargetId;//ID
	@Column(name = "TARGETKINDID")
	private String targetKindId;//指标种类
	@Column(name = "COMPOSETARGETTYPEID")
	private String composeTargetTypeId;//合成指标类型
	@Column(name = "COMPOSETARGETNAME")
	private String composeTargetName;//合成指标名称
	@Column(name = "COMPOSETARGETENGNAME")
	private String composeTargetEngName;//合成指标英文文名
	@Column(name = "TARGET")
	private String target;//指标规则（算法）
	@Column(name = "ISREPORT")
	private String isReport;//是否用于报表
	@Column(name = "SERIALNUMBER")
	private String serialNumber;//顺序号
	@Column(name = "STATUS")
	private String status;//状态
	@Column(name = "ISVIEWTABLE")
	private String isViewTable;//是否图标展示
	@Column(name = "OPERATOR")
	private String operator;//创建人
	@Column(name = "LASTUPDATEDATE")
	private String lastUpdateDate;//最后更新时间
	@Column(name = "CALCULATIONPERIOD")
	private String calculationPeriod;//计算周期
	@Column(name = "OPERATIONTYPE")
	private String operationType;//操作类型
	@Column(name = "REVIEWER")
	private String reviewer;//审核人
	@Column(name = "IDEA")
	private String idea;//审核意见
}
