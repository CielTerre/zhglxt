package com.seezoon.framework.modules.zhfw.dao;

import java.io.Serializable;
import java.util.List;

import com.seezoon.framework.common.dao.CrudDao;
import com.seezoon.framework.modules.zhfw.entity.IndexQdjbxx;


public interface IndexQdjbxxDao extends CrudDao<IndexQdjbxx>{
	
	//查询当日访问量，当日业务受理量，当日咨询人数，当日投诉人数，图表数据
	public List<IndexQdjbxx> selectIndex();
	
	//查询渠道一周访问量
	public List<IndexQdjbxx> selectIndexQdfwl(Serializable timeStr);
	
	
	
}
