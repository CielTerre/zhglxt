package com.seezoon.framework.modules.zhfw.service;





import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seezoon.framework.common.service.CrudService;
import com.seezoon.framework.modules.zhfw.dao.LyclDao;
import com.seezoon.framework.modules.zhfw.entity.Lycl;



/**
 * 
 * 留言处理
 *
 */
@Service
public class LyclService extends CrudService<LyclDao, Lycl> {
	
	
	@Transactional(readOnly = true)
	public PageInfo<Lycl> findByZsnr(Lycl lycl, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize, Boolean.TRUE);
		List<Lycl> list = this.findZsnr(lycl);
		PageInfo<Lycl> pageInfo = new PageInfo<Lycl>(list);
		return pageInfo;
	}
	@Transactional(readOnly = true)
	private List<Lycl> findZsnr(Lycl lycl) {
		return d.findZsnr(lycl);
	}
    
	public int deleteid(Serializable id) {
		Assert.notNull(id, "id为空");
		Assert.hasLength(id.toString(), "id为空");
		Lycl lycl = this.findById(id);
		if (null != lycl) {
			lycl.setUpdateDate(new Date());
			lycl.setUpdateBy(this.getOperatorUserId());
			if (lycl.isNeedBak()) {
				this.d.insertBak(lycl);
			}
			return d.deleteByid(id);
		}
		return 0;
	}
	public int zskupdateSelective(Lycl lycl) {
		Assert.notNull(lycl, "更新对象为空");
	//	Assert.notNull(t.getId(), "更新对象id为空");
	//	Assert.hasLength(t.getId().toString(), "更新对象id为空");
		lycl.setUpdateDate(new Date());
		if (StringUtils.isEmpty(lycl.getUpdateBy())) {
			lycl.setUpdateBy(this.getOperatorUserId());
		}
		int cnt = d.zskupdateByPrimaryKeySelective(lycl);
		if (lycl.isNeedBak()) {
			this.saveBak(lycl.getId());
		}
		return cnt;
	}
	
	public int hflyupdateSelective(Lycl lycl) {
		Assert.notNull(lycl, "更新对象为空");
	//	Assert.notNull(t.getId(), "更新对象id为空");
	//	Assert.hasLength(t.getId().toString(), "更新对象id为空");
		lycl.setUpdateDate(new Date());
		if (StringUtils.isEmpty(lycl.getUpdateBy())) {
			lycl.setUpdateBy(this.getOperatorUserId());
		}
		int cnt = d.hflyupdateByPrimaryKeySelective(lycl);
		if (lycl.isNeedBak()) {
			this.saveBak(lycl.getId());
		}
		return cnt;
	}
	

}
