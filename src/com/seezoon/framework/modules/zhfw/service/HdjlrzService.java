package com.seezoon.framework.modules.zhfw.service;




import java.util.List;

import org.springframework.stereotype.Service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seezoon.framework.common.service.CrudService;
import com.seezoon.framework.modules.zhfw.dao.HdjlrzDao;
import com.seezoon.framework.modules.zhfw.entity.Hdjlrz;


/**
 * 
 * 互动交流日志Service
 *
 */
@Service
public class HdjlrzService extends CrudService<HdjlrzDao, Hdjlrz>{
	
/*	public List<Hdjlrz> findBysee(Hdjlrz hdjlrz) {
		return  this.d.findBysee(hdjlrz);
	}
	
	
	public PageInfo<Hdjlrz> findByxm(Hdjlrz hdjlrz, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize, Boolean.TRUE);
		List<Hdjlrz> list = this.findBysee(hdjlrz);
		PageInfo<Hdjlrz> pageInfo = new PageInfo<Hdjlrz>(list);
		return pageInfo;
	}*/
  	  
}
