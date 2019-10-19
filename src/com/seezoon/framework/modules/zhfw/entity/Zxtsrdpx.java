package com.seezoon.framework.modules.zhfw.entity;

import org.hibernate.validator.constraints.Length;

import com.seezoon.framework.common.entity.BaseEntity;

public class Zxtsrdpx  extends BaseEntity<String>{
	private static final long serialVersionUID = 1L;
	/**
	 * 标题
	 */
	@Length(max=255)
	private String fxmc;
	/**
	 * 分类
	 */
	@Length(max=10)
	private String flid;
	/**
	 * 访问数量
	 */
	private String hits;
	/**
	 * 发布时间
	 */
	private String llrq;
	/**
	 * 最后访问日期
	 */
	private String zhfwrq;

	/**
	 * 页数
	 *
	 */
	private Integer ys;

	/**
	 *
	 *
	 */


	public String getFxmc() {
		return fxmc;
	}
	public void setFxmc(String fxmc) {
		this.fxmc = fxmc;
	}
	public String getFlid() {
		return flid;
	}
	public void setFlid(String flid) {
		this.flid = flid;
	}
	public String getHits() {
		return hits;
	}
	public void setHits(String hits) {
		this.hits = hits;
	}
	public String getLlrq() {
		return llrq;
	}
	public void setLlrq(String llrq) {
		this.llrq = llrq;
	}
	public String getZhfwrq() {
		return zhfwrq;
	}
	public void setZhfwrq(String zhfwrq) {
		this.zhfwrq = zhfwrq;
	}



}
