package com.seezoon.framework.modules.zhfw.dao;

import java.io.Serializable;
import java.util.List;

import com.seezoon.framework.common.dao.CrudDao;
import com.seezoon.framework.modules.zhfw.entity.WtArticleQdgx;
import com.seezoon.framework.modules.zhfw.entity.WtZhfwptQtArticleEdit;

/**
 * 文章渠道关系表Dao
 * 2018-8-12 17:28:15
 */
public interface WtArticleQdgxDao extends CrudDao<WtArticleQdgx> {
	
	//文章添加
	//public int insertWtArticleQdgx(WtArticleQdgx wtArticleQdgx);
	
	//删除不包含该渠道的渠道信息
	public int deleteNotInQdid(WtArticleQdgx wtArticleQdgx);
	
	public List<WtArticleQdgx> selectWtArticleQdgx(WtArticleQdgx wtArticleQdgx);
	
	//通过相关id删除
	public int deleteWtArticleQdgxXgid(Serializable id);
	
	
}
