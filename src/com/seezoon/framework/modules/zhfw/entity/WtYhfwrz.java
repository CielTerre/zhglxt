package com.seezoon.framework.modules.zhfw.entity;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.seezoon.framework.common.entity.BaseEntity;
/**
 * 
 * 用户访问日志
 *
 */
public class WtYhfwrz extends BaseEntity<String>{

	private static final long serialVersionUID = 1L;
	/**
	 * 流水号
	 */
	@NotNull
    @Length(min = 1, max = 20)
	private String ywlsh;
	
	/**
     * 用户ID
     */
    @Length(max = 20)
	private String yhid;
	
    /**
     * 服务ID
     */
    @Length(max = 20)
	private String fwid;
	
    /**
     * 服务名称
     */
    @Length(max = 400)
	private String fwmc;
	
	/**
     * 渠道ID
     */
    @Length(max = 20)
	private String qdid;
	
	private Date rq;
	
	private String startTime;
	
	private String endTime;
	
	private String yhzh;
	
	private String qqlx;
	
	private String fwfl;

	public String getYwlsh() {
		return ywlsh;
	}

	public void setYwlsh(String ywlsh) {
		this.ywlsh = ywlsh;
	}

	public String getYhid() {
		return yhid;
	}

	public void setYhid(String yhid) {
		this.yhid = yhid;
	}

	public String getFwid() {
		return fwid;
	}

	public void setFwid(String fwid) {
		this.fwid = fwid;
	}
	
	public String getFwmc() {
		return fwmc;
	}

	public void setFwmc(String fwmc) {
		this.fwmc = fwmc;
	}

	public String getQdid() {
		return qdid;
	}

	public void setQdid(String qdid) {
		this.qdid = qdid;
	}

	public Date getRq() {
		return rq;
	}

	public void setRq(Date rq) {
		this.rq = rq;
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

	public String getYhzh() {
		return yhzh;
	}

	public void setYhzh(String yhzh) {
		this.yhzh = yhzh;
	}

	public String getQqlx() {
		return qqlx;
	}

	public void setQqlx(String qqlx) {
		this.qqlx = qqlx;
	}

	public String getFwfl() {
		return fwfl;
	}

	public void setFwfl(String fwfl) {
		this.fwfl = fwfl;
	}
	
	
	
}
