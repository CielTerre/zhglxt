package com.seezoon.framework.modules.zhfw.entity;

import com.seezoon.framework.common.entity.BaseEntity;
import java.sql.Timestamp;

/**
 * Created with Ciel.
 * User: jar
 * Date: 2018-09-08 18:23
 */

/**
*综合服务平台投票主题管理
*/
public class WtZhfwptThem extends BaseEntity<String> {
	private String themeid; //投票主题ID
	private String themetitle; //投票项目名称（标题）
	private String lrrzh; //录入人账号
	private Timestamp lrrq; //录入日期
	private String dqzt; //当前状态

	public String getThemeid() {
 		return themeid; 
	}

	public void setThemeid(String themeid) {
		 this.themeid = themeid; 
	}

	public String getThemetitle() {
 		return themetitle; 
	}

	public void setThemetitle(String themetitle) {
		 this.themetitle = themetitle; 
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

	public String getDqzt() {
 		return dqzt; 
	}

	public void setDqzt(String dqzt) {
		 this.dqzt = dqzt; 
	}

}