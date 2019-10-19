package com.seezoon.framework.modules.zhfw.entity;

import com.seezoon.framework.common.entity.BaseEntity;
import java.sql.Timestamp;
import java.math.BigDecimal;

/**
 * Created with Ciel.
 * User: jar
 * Date: 2018-09-08 21:33
 */

/**
*综合服务平台选票内容
*/
public class WtZhfwptVotepotion extends BaseEntity<String> {
	private String voteoptionid; //选票内容编号
	private String themeid; //投票主题ID 
	private String lrrzh; //录入人账号
	private Timestamp lrrq; //录入日期
	private String xpnr; //选票内容
	private BigDecimal xpjg; //选票结果
	private String dqzt; //当前状态

	public String getVoteoptionid() {
 		return voteoptionid; 
	}

	public void setVoteoptionid(String voteoptionid) {
		 this.voteoptionid = voteoptionid; 
	}

	public String getThemeid() {
 		return themeid; 
	}

	public void setThemeid(String themeid) {
		 this.themeid = themeid; 
	}

	public String getLrrzh() {
 		return lrrzh; 
	}

	public void setLrrzh(String lrrzh) {
		 this.lrrzh = lrrzh; 
	}

	public Timestamp getLrrq() {
 		return lrrq; 
	}

	public void setLrrq(Timestamp lrrq) {
		 this.lrrq = lrrq; 
	}

	public String getXpnr() {
 		return xpnr; 
	}

	public void setXpnr(String xpnr) {
		 this.xpnr = xpnr; 
	}

	public BigDecimal getXpjg() {
 		return xpjg; 
	}

	public void setXpjg(BigDecimal xpjg) {
		 this.xpjg = xpjg; 
	}

	public String getDqzt() {
 		return dqzt; 
	}

	public void setDqzt(String dqzt) {
		 this.dqzt = dqzt; 
	}

}