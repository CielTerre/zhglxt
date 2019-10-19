package com.seezoon.framework.modules.zhfw.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seezoon.framework.common.service.CrudService;
import com.seezoon.framework.modules.zhfw.dao.IndexQdjbxxDao;
import com.seezoon.framework.modules.zhfw.entity.IndexQdjbxx;

@Service
public class IndexQdjbxxService extends CrudService<IndexQdjbxxDao, IndexQdjbxx>{
	
	@Transactional(readOnly = true)
	public List<IndexQdjbxx> selectIndex(){
		return d.selectIndex();
	}
	
	@Transactional(readOnly = true)
	public List<IndexQdjbxx> selectIndexQdfwl(Serializable timeStr){
		return d.selectIndexQdfwl(timeStr);
	}
	
	
	
}
