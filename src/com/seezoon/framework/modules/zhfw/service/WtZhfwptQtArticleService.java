package com.seezoon.framework.modules.zhfw.service;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seezoon.framework.common.service.CrudService;
import com.seezoon.framework.common.utils.IdGen;
import com.seezoon.framework.modules.zhfw.dao.IndexQdjbxxDao;
import com.seezoon.framework.modules.zhfw.dao.OracleToolDao;
import com.seezoon.framework.modules.zhfw.dao.WtArticleQdgxDao;
import com.seezoon.framework.modules.zhfw.dao.WtZhfwptQtArticleDao;
import com.seezoon.framework.modules.zhfw.dao.WtZskmxDao;
import com.seezoon.framework.modules.zhfw.entity.WtArticleQdgx;
import com.seezoon.framework.modules.zhfw.entity.WtZhfwptQtArticle;
import com.seezoon.framework.modules.zhfw.entity.WtZhfwptQtArticleEdit;
import com.seezoon.framework.modules.zhfw.entity.WtZskmx;

/**
 * 综合服务平台前台文章信息Service
 */
@Service
public class WtZhfwptQtArticleService extends CrudService<WtZhfwptQtArticleDao, WtZhfwptQtArticleEdit>{
	
	@Autowired
    private WtZhfwptQtArticleDao wtZhfwptQtArticleDao;
	
	@Autowired
	private WtArticleQdgxDao wtArticleQdgxDao;
	
	@Autowired
	private WtZskmxDao wtZskmxDao;
	
	@Autowired
	private OracleToolDao oracleToolDao;
	
	
	//private WtArticleQdgx wtArticleQdgx;
	
	public String getId() {
		return oracleToolDao.selectGKeyByTableName("ALL");
	}
	
	
	@Transactional(readOnly = true)
	public PageInfo<WtZhfwptQtArticleEdit> findByPage(WtZhfwptQtArticleEdit wtZhfwptQtArticleEdit, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize, Boolean.TRUE);
		List<WtZhfwptQtArticleEdit> list = this.selectZhfwptQtArticle(wtZhfwptQtArticleEdit);
		PageInfo<WtZhfwptQtArticleEdit> pageInfo = new PageInfo<WtZhfwptQtArticleEdit>(list);
		return pageInfo;
	}
	
	@Transactional(readOnly = true)
	public List<WtZhfwptQtArticleEdit> selectZhfwptQtArticle(WtZhfwptQtArticleEdit wtZhfwptQtArticleEdit) {
		return wtZhfwptQtArticleDao.selectZhfwptQtArticle(wtZhfwptQtArticleEdit);
	}
	
	@Transactional(readOnly = true)
	public WtZhfwptQtArticleEdit findById(Serializable id) {
		Assert.notNull(id, "id为空");
		Assert.hasLength(id.toString(), "id为空");
		return wtZhfwptQtArticleDao.selectWzid(id);
	}
	
	
	public int save(WtZhfwptQtArticleEdit wtZhfwptQtArticleEdit, WtArticleQdgx wtArticleQdgx) {
		wtZhfwptQtArticleEdit.setCreateDate(new Date());
		wtZhfwptQtArticleEdit.setUpdateDate(wtZhfwptQtArticleEdit.getCreateDate());
		if (StringUtils.isEmpty(wtZhfwptQtArticleEdit.getCreateBy())) {
			wtZhfwptQtArticleEdit.setCreateBy(this.getOperatorUserId());
		}
		wtZhfwptQtArticleEdit.setUpdateBy(wtZhfwptQtArticleEdit.getCreateBy());
		//插入文章信息
		String wzid = getId();
		wtZhfwptQtArticleEdit.setWzid(wzid);
		int cnt = wtZhfwptQtArticleDao.insertZhfwptQtArticle(wtZhfwptQtArticleEdit);
		if (wtZhfwptQtArticleEdit.isNeedBak()) {
			this.saveBak(wtZhfwptQtArticleEdit.getId());
		}
		//插入文章关系信息
		wtArticleQdgx.setXgid(wzid);
		wtArticleQdgx.setHits(wtZhfwptQtArticleEdit.getHits());
		wtArticleQdgx.setRefType("1");
		wtArticleQdgx.setZhfwrq(new Date());
		String qdid = wtZhfwptQtArticleEdit.getQdid();
		if(qdid.indexOf(",") == -1) {
			String ywlsh = getId();
			wtArticleQdgx.setYwlsh(ywlsh);
			wtArticleQdgx.setQdid(qdid);
			wtArticleQdgxDao.insert(wtArticleQdgx);
		}else {
			String[] qdidArr = qdid.split(",");
			for(String qdidStr : qdidArr) {
				String ywlsh = getId();
				wtArticleQdgx.setYwlsh(ywlsh);
				wtArticleQdgx.setQdid(qdidStr);
				wtArticleQdgxDao.insert(wtArticleQdgx);
			}
		}
		
		if (wtArticleQdgx.isNeedBak()) {
			this.saveBak(wtArticleQdgx.getId());
		}
		return cnt;
	}
	
	
	public int updateSelective(WtZhfwptQtArticleEdit wtZhfwptQtArticleEdit, WtArticleQdgx wtArticleQdgx) {
		Assert.notNull(wtZhfwptQtArticleEdit, "更新对象为空");
		wtZhfwptQtArticleEdit.setUpdateDate(new Date());
		if (StringUtils.isEmpty(wtZhfwptQtArticleEdit.getUpdateBy())) {
			wtZhfwptQtArticleEdit.setUpdateBy(this.getOperatorUserId());
		}
		int cnt = wtZhfwptQtArticleDao.updateZhfwptQtArticle(wtZhfwptQtArticleEdit);
		
		String wzid = wtZhfwptQtArticleEdit.getWzid();
		String qdid = wtZhfwptQtArticleEdit.getQdid();
		System.out.println("qdid:"+qdid);
		if(qdid != null) {
			if(qdid.indexOf(",") != -1) {
				String[] qdidArr = qdid.split(",");
				//清除该文章存在表中没有被选中的渠道信息
				String qdidString = "";
				for(String qdidArrStr : qdidArr) {
					qdidString += ("'"+qdidArrStr+"'" + ",");
				}
				qdidString = qdidString.substring(0, qdidString.lastIndexOf(","));
				wtArticleQdgx.setXgid(wzid);
				wtArticleQdgx.setQdid(qdidString);
				wtArticleQdgxDao.deleteNotInQdid(wtArticleQdgx);
				//增添新选中的渠道信息
				for(String qdidStr : qdidArr) {
					wtArticleQdgx.setQdid(qdidStr);
					wtArticleQdgx.setYwlsh(null);
					List<WtArticleQdgx> list = wtArticleQdgxDao.selectWtArticleQdgx(wtArticleQdgx);
					if(list.size() == 0) {
						String ywlsh = getId();
						wtArticleQdgx.setYwlsh(ywlsh);
						wtArticleQdgx.setQdid(qdidStr);
						wtArticleQdgx.setHits(0);
						wtArticleQdgx.setRefType("1");
						wtArticleQdgx.setZhfwrq(new Date());
						wtArticleQdgxDao.insert(wtArticleQdgx);
					}
				}
			}else {
				String qdidString = "'"+qdid+"'";
				wtArticleQdgx.setXgid(wzid);
				wtArticleQdgx.setQdid(qdidString);
				wtArticleQdgxDao.deleteNotInQdid(wtArticleQdgx);
				wtArticleQdgx.setQdid(qdid);
				List<WtArticleQdgx> list = wtArticleQdgxDao.selectWtArticleQdgx(wtArticleQdgx);
				if(list.size() == 0) {
					String ywlsh = getId();
					wtArticleQdgx.setYwlsh(ywlsh);
					wtArticleQdgx.setQdid(qdid);
					wtArticleQdgx.setHits(0);
					wtArticleQdgx.setRefType("1");
					wtArticleQdgx.setZhfwrq(new Date());
					wtArticleQdgxDao.insert(wtArticleQdgx);
				}
				
			}
		}
		if (wtZhfwptQtArticleEdit.isNeedBak()) {
			this.saveBak(wtZhfwptQtArticleEdit.getId());
		}
		return cnt;
	}
	
	
	public int deleteById(Serializable id) {
		Assert.notNull(id, "id为空");
		Assert.hasLength(id.toString(), "id为空");
		WtZhfwptQtArticleEdit wtZhfwptQtArticleEdit = this.findById(id);
		if (null != wtZhfwptQtArticleEdit) {
			wtZhfwptQtArticleEdit.setUpdateDate(new Date());
			wtZhfwptQtArticleEdit.setUpdateBy(this.getOperatorUserId());
			if (wtZhfwptQtArticleEdit.isNeedBak()) {
				this.wtZhfwptQtArticleDao.insertBak(wtZhfwptQtArticleEdit);
			}
			//删除文章表中的信息
			int num = wtZhfwptQtArticleDao.deleteByPrimaryKey(id,wtZhfwptQtArticleEdit.getDsf());
			//删除文章关系表中的信息
			wtArticleQdgxDao.deleteWtArticleQdgxXgid(id);
			return num;
		}
		return 0;
	}
	
	
	public int savezsk(WtZhfwptQtArticleEdit wtZhfwptQtArticleEdit, WtArticleQdgx wtArticleQdgx, WtZskmx wtZskmx) {
		wtZhfwptQtArticleEdit.setCreateDate(new Date());
		wtZhfwptQtArticleEdit.setUpdateDate(wtZhfwptQtArticleEdit.getCreateDate());
		if (StringUtils.isEmpty(wtZhfwptQtArticleEdit.getCreateBy())) {
			wtZhfwptQtArticleEdit.setCreateBy(this.getOperatorUserId());
		}
		wtZhfwptQtArticleEdit.setUpdateBy(wtZhfwptQtArticleEdit.getCreateBy());
		//插入知识库信息
		String id = getId();
		wtZskmx.setId(id);
		int cnt = wtZskmxDao.insert(wtZskmx);
		if (wtZskmx.isNeedBak()) {
			this.saveBak(wtZskmx.getId());
		}
		//更新文章表中的知识库id
		wtZhfwptQtArticleEdit.setZskid(id);
		wtZhfwptQtArticleDao.updateZhfwptQtArticle(wtZhfwptQtArticleEdit);
		
		if (wtZhfwptQtArticleEdit.isNeedBak()) {
			this.saveBak(wtZhfwptQtArticleEdit.getId());
		}
		return cnt;
	}
	
}
