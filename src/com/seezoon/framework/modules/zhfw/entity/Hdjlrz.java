package com.seezoon.framework.modules.zhfw.entity;



import com.seezoon.framework.common.entity.BaseEntity;


public class Hdjlrz extends BaseEntity<String> {

	private static final long serialVersionUID = 1L;
	/**
	 * 用户姓名
	 */
     private String yhxm;
     /**
      * 咨询时间
      */
     private String fqsj;
     /**
      * 客户姓名
      */
     private String kfxm;

	public String getYhxm() {
		return yhxm;
	}
	public void setYhxm(String yhxm) {
		this.yhxm = yhxm;
	}
	public String getFqsj() {
		return fqsj;
	}
	public void setFqsj(String fqsj) {
		this.fqsj = fqsj;
	}
	public String getKfxm() {
		return kfxm;
	}
	public void setKfxm(String kfxm) {
		this.kfxm = kfxm;
	}

     
}
