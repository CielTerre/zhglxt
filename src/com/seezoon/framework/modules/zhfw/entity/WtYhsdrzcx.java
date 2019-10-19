package com.seezoon.framework.modules.zhfw.entity;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.seezoon.framework.common.entity.BaseEntity;
/**
 * 
 * 用户锁定日志查询
 *
 */
public class WtYhsdrzcx extends BaseEntity<String>{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 日志id
	 */
	@NotNull
    @Length(min = 1, max = 20)
	private String logid;
	
	@Length(max=2)
	private String yhlbdm;
	
	@NotNull
	@Length(max=20)
	private String yhdlzh;
	
	@NotNull
	private Date lockTime;
	
	@Length(max=50)
	private String lockRemark;
	
	private String startTime;
	
	private String endTime;

	public String getLogid() {
		return logid;
	}

	public void setLogid(String logid) {
		this.logid = logid;
	}

	public String getYhlbdm() {
		return yhlbdm;
	}

	public void setYhlbdm(String yhlbdm) {
		this.yhlbdm = yhlbdm;
	}

	public String getYhdlzh() {
		return yhdlzh;
	}

	public void setYhdlzh(String yhdlzh) {
		this.yhdlzh = yhdlzh;
	}

	public Date getLockTime() {
		return lockTime;
	}

	public void setLockTime(Date lockTime) {
		this.lockTime = lockTime;
	}

	public String getLockRemark() {
		return lockRemark;
	}

	public void setLockRemark(String lockRemark) {
		this.lockRemark = lockRemark;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
}
