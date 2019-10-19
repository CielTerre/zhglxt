package com.seezoon.framework.modules.zhfw.dao;

import java.io.Serializable;
import java.util.List;

import com.github.pagehelper.PageInfo;
import com.seezoon.framework.common.dao.CrudDao;
import com.seezoon.framework.modules.zhfw.entity.WtZhfwptQtArticle;
import com.seezoon.framework.modules.zhfw.entity.WtZhfwptQtArticleEdit;

/**
 * 综合服务平台前台文章信息Dao
 * 2018-8-12 17:27:44
 */
public interface WtZhfwptQtArticleDao extends CrudDao<WtZhfwptQtArticleEdit> {
	
	//文章查询
	public List<WtZhfwptQtArticleEdit> selectZhfwptQtArticle(WtZhfwptQtArticleEdit wtZhfwptQtArticleEdit);
	
	//通过id来查询文章
	public WtZhfwptQtArticleEdit selectWzid(Serializable id);
	
	//文章添加
	public int insertZhfwptQtArticle(WtZhfwptQtArticleEdit wtZhfwptQtArticleEdit);
	
	//文章更新
	public int updateZhfwptQtArticle(WtZhfwptQtArticleEdit wtZhfwptQtArticleEdit);
}
