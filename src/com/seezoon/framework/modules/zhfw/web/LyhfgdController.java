package com.seezoon.framework.modules.zhfw.web;

import java.io.Serializable;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.seezoon.framework.common.context.beans.ResponeModel;
import com.seezoon.framework.common.web.BaseController;
import com.seezoon.framework.modules.system.entity.SysUser;
import com.seezoon.framework.modules.system.service.SysUserService;
import com.seezoon.framework.modules.zhfw.entity.Lycl;
import com.seezoon.framework.modules.zhfw.entity.WtArticleQdgx;
import com.seezoon.framework.modules.zhfw.entity.WtGdxx;
import com.seezoon.framework.modules.zhfw.entity.WtZskmx;
import com.seezoon.framework.modules.zhfw.service.LyclService;

import com.seezoon.framework.modules.zhfw.service.OracleToolService;
import com.seezoon.framework.modules.zhfw.service.WtArticleQdgxService;
import com.seezoon.framework.modules.zhfw.service.WtGdxxService;
import com.seezoon.framework.modules.zhfw.service.WtZskmxService;
import com.seezoon.framework.modules.zhfw.util.SystemUtil;

/**
 * 
 * 留言回复归档
 *
 */
@RestController
@RequestMapping("${admin.path}/zhfw/lyhfgd")
public class LyhfgdController extends BaseController {
  @Autowired
  private LyclService lyclService;
  @Autowired
  private WtZskmxService wtZskmxService;
    @Autowired
	private SysUserService sysUserService;
	@Autowired
	private OracleToolService oracleToolService;
	@Autowired
	private WtGdxxService wtGdxxService;
  @PostMapping("/qryPage.do")
  public ResponeModel qryPage(Lycl lycl){
	    lycl.setSortField("addtime");
	    lycl.setDirection("desc");
	    lycl.setDsf(" and shzt in('01')");
	    PageInfo<Lycl> page = lyclService.findByPage(lycl, lycl.getPage(), lycl.getPageSize());
		return ResponeModel.ok(page);	
  }
  @RequestMapping("/get.do")
	public ResponeModel get(@RequestParam Serializable id) {
		Lycl lycl = lyclService.findById(id);
		return ResponeModel.ok(lycl);
	}
  @RequestMapping("/getgd.do")
	public ResponeModel getgd(@RequestParam Serializable id) {
		WtGdxx wtGdxx = wtGdxxService.findById(id);
		return ResponeModel.ok(wtGdxx);
	}
	@PostMapping("/update.do")
    public ResponeModel update(@Validated WtZskmx wtZskmx,@RequestParam String id) {
		String userid= wtZskmxService.getOperatorUserId();
		SysUser sysUser = sysUserService.findById(userid);
		wtZskmx.setId(oracleToolService.getCommonKey());
		wtZskmx.setLlrxm(sysUser.getName());
		wtZskmx.setLlr(userid);
		wtZskmx.setLlrq(new Date());
		wtZskmx.setZsnr(SystemUtil.htmlspecialchars(wtZskmx.getZsnr()));
		int cnt = wtZskmxService.save(wtZskmx);
		if(cnt==1){
			    String[] ids = id.split(",");
			  Lycl lycl = lyclService.findById(ids[0]);
		      lycl.setZskid(wtZskmx.getId());	      		      
		      return ResponeModel.ok(lyclService.zskupdateSelective(lycl));
		}
		return ResponeModel.ok(cnt); 
	  
  }
  
}
