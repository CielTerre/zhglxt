package com.seezoon.framework.modules.zhfw.dao;





import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.seezoon.framework.common.dao.CrudDao;
import com.seezoon.framework.modules.zhfw.entity.Lycl;
/**
 * 
 * 留言处理
 *
 */
public interface LyclDao extends CrudDao<Lycl> {

	
	public List<Lycl> findZsnr(Lycl lycl);
    
	public int zskupdateByPrimaryKeySelective(Lycl lycl);
	
	public int hflyupdateByPrimaryKeySelective(Lycl lycl);
	
	public int deleteByid( Serializable id);
		
}
