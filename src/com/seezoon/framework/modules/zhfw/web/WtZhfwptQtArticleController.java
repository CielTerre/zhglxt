package com.seezoon.framework.modules.zhfw.web;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.seezoon.framework.modules.system.entity.SysUser;
import com.seezoon.framework.modules.system.service.SysUserService;
import com.seezoon.framework.modules.zhfw.service.OracleToolService;
import com.seezoon.framework.modules.zhfw.util.SystemUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
import com.seezoon.framework.modules.zhfw.entity.WtArticleQdgx;
import com.seezoon.framework.modules.zhfw.entity.WtZhfwptQtArticle;
import com.seezoon.framework.modules.zhfw.entity.WtZhfwptQtArticleEdit;
import com.seezoon.framework.modules.zhfw.entity.WtZhfwptQtClass;
import com.seezoon.framework.modules.zhfw.entity.WtZskmx;
import com.seezoon.framework.modules.zhfw.service.WtArticleQdgxService;
import com.seezoon.framework.modules.zhfw.service.WtZhfwptQtArticleService;

/**
 * 综合服务平台前台文章信息controller
 */
@RestController
@RequestMapping("${admin.path}/zhfw/zhfwptqtarticle")
public class WtZhfwptQtArticleController extends BaseController {

	@Autowired
	private WtZhfwptQtArticleService wtZhfwptQtArticleService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private OracleToolService oracleToolService;
	
	@RequiresPermissions("zhfw:zhfwptqtarticle:qry")
	@PostMapping("/qryPage.do")
	public ResponeModel qryPage(WtZhfwptQtArticleEdit wtZhfwptQtArticleEdit) {
		PageInfo<WtZhfwptQtArticleEdit> page = wtZhfwptQtArticleService.findByPage(wtZhfwptQtArticleEdit, wtZhfwptQtArticleEdit.getPage(), wtZhfwptQtArticleEdit.getPageSize());
		return ResponeModel.ok(page);
	}
	
	@RequiresPermissions("zhfw:zhfwptqtarticle:qry")
	@PostMapping("/qryPageSh.do")
	public ResponeModel qryPageSh(WtZhfwptQtArticleEdit wtZhfwptQtArticleEdit) {
		wtZhfwptQtArticleEdit.setDqzt("02");
		PageInfo<WtZhfwptQtArticleEdit> page = wtZhfwptQtArticleService.findByPage(wtZhfwptQtArticleEdit, wtZhfwptQtArticleEdit.getPage(), wtZhfwptQtArticleEdit.getPageSize());
		return ResponeModel.ok(page);
	}
	
	@RequiresPermissions("zhfw:zhfwptqtarticle:qry")
	@PostMapping("/qryPageAddzsk.do")
	public ResponeModel qryPageAddzsk(WtZhfwptQtArticleEdit wtZhfwptQtArticleEdit) {
		wtZhfwptQtArticleEdit.setDqzt("");
		PageInfo<WtZhfwptQtArticleEdit> page = wtZhfwptQtArticleService.findByPage(wtZhfwptQtArticleEdit, wtZhfwptQtArticleEdit.getPage(), wtZhfwptQtArticleEdit.getPageSize());
		return ResponeModel.ok(page);
	}
	
	@RequiresPermissions("zhfw:zhfwptqtarticle:qry")
	@RequestMapping("/get.do")
	public ResponeModel get(@RequestParam Serializable id) {
		WtZhfwptQtArticleEdit wtZhfwptQtArticleEdit = wtZhfwptQtArticleService.findById(id);
		//富文本处理
		return ResponeModel.ok(wtZhfwptQtArticleEdit);
	}
	@RequiresPermissions("zhfw:zhfwptqtarticle:save")
	@PostMapping("/save.do")
	public ResponeModel save(@Validated WtZhfwptQtArticleEdit wtZhfwptQtArticleEdit, WtArticleQdgx wtArticleQdgx, BindingResult bindingResult) {
		//设置上传文件的路径
		wtZhfwptQtArticleEdit.setImages(wtZhfwptQtArticleEdit.getFilePath());
		
		String filePath = wtZhfwptQtArticleEdit.getFilePath();
		if(filePath != null && filePath.length() != 0) {
			wtZhfwptQtArticleEdit.setIsflash("01");
		}
		
		//是否置顶状态
		String istop = wtZhfwptQtArticleEdit.getIstop();
		if(istop == null) {
			wtZhfwptQtArticleEdit.setIstop("02");	//表示否
		}else {
			wtZhfwptQtArticleEdit.setIstop("03");	//表示是
		}
		//是否推荐状态
		String ishot = wtZhfwptQtArticleEdit.getIshot();
		if(ishot == null) {
			wtZhfwptQtArticleEdit.setIshot("02");
		}else {
			wtZhfwptQtArticleEdit.setIshot("01");
		}
		//是否为图片状态
		String isflash = wtZhfwptQtArticleEdit.getIsflash();
		if(isflash == null) {
			wtZhfwptQtArticleEdit.setIsflash("02");
		}else {
			wtZhfwptQtArticleEdit.setIsflash("01");
		}
		
		//文章点击次数
		wtZhfwptQtArticleEdit.setHits(0);
		
		//文章最后操作时间
		wtZhfwptQtArticleEdit.setDateandtime(new Date());
		
		//最后操作日期
		wtZhfwptQtArticleEdit.setZhczrq(new Date());
		
		String content = wtZhfwptQtArticleEdit.getContent();
		wtZhfwptQtArticleEdit.setContent(SystemUtil.htmlspecialchars(content));
		
		int cnt = wtZhfwptQtArticleService.save(wtZhfwptQtArticleEdit,  wtArticleQdgx);
		return ResponeModel.ok(cnt);
	}
	@RequiresPermissions("zhfw:zhfwptqtarticle:update")
	@PostMapping("/update.do")
	public ResponeModel update(@Validated WtZhfwptQtArticleEdit wtZhfwptQtArticleEdit, WtArticleQdgx wtArticleQdgx, BindingResult bindingResult) {
		
		//设置上传文件的路径
		wtZhfwptQtArticleEdit.setImages(wtZhfwptQtArticleEdit.getFilePath());
		String filePath = wtZhfwptQtArticleEdit.getFilePath();
		if("".equals(filePath)) {
			wtZhfwptQtArticleEdit.setImages(null);
		}else {
			wtZhfwptQtArticleEdit.setIsflash("01");
		}
		
		//是否置顶状态
		String istop = wtZhfwptQtArticleEdit.getIstop();
		if(istop == null) {
			wtZhfwptQtArticleEdit.setIstop("02");	//表示否
		}else {
			wtZhfwptQtArticleEdit.setIstop("03");	//表示是
		}
		//是否推荐状态
		String ishot = wtZhfwptQtArticleEdit.getIshot();
		if(ishot == null) {
			wtZhfwptQtArticleEdit.setIshot("02");
		}else {
			wtZhfwptQtArticleEdit.setIshot("01");
		}
		//是否为图片状态
		String isflash = wtZhfwptQtArticleEdit.getIsflash();
		if(isflash == null) {
			wtZhfwptQtArticleEdit.setIsflash("02");
		}else {
			wtZhfwptQtArticleEdit.setIsflash("01");
		}
		
		//文章审核状态
		String dqzt = wtZhfwptQtArticleEdit.getDqzt();
		System.out.println("dqzt:"+dqzt);
		System.out.println("wzid:"+wtZhfwptQtArticleEdit.getWzid());
		
		//文章点击次数
		//wtZhfwptQtArticleEdit.setHits(0);
		
		//文章最后操作时间
		wtZhfwptQtArticleEdit.setDateandtime(new Date());
		
		//最后操作日期
		wtZhfwptQtArticleEdit.setZhczrq(new Date());
		
		String content = wtZhfwptQtArticleEdit.getContent();
		wtZhfwptQtArticleEdit.setContent(SystemUtil.htmlspecialchars(content));
		
		int cnt = wtZhfwptQtArticleService.updateSelective(wtZhfwptQtArticleEdit, wtArticleQdgx);
		return ResponeModel.ok(cnt);
	}
	@RequiresPermissions("zhfw:zhfwptqtarticle:delete")
	@PostMapping("/delete.do")
	public ResponeModel delete(@RequestParam Serializable id) {
		int cnt = wtZhfwptQtArticleService.deleteById(id);
		return ResponeModel.ok(cnt);
	}
	
	@PostMapping("/up.do")
	public ResponeModel up(@Validated WtZhfwptQtArticleEdit wtZhfwptQtArticleEdit, WtArticleQdgx wtArticleQdgx, BindingResult bindingResult) {
		//文章审核状态
		String dqzt = wtZhfwptQtArticleEdit.getDqzt();
		String wzid = wtZhfwptQtArticleEdit.getWzid();
		int cnt = 0;
		
		
		System.out.println("dqzt:"+dqzt);
		System.out.println("wzid:"+wtZhfwptQtArticleEdit.getWzid());
		
		if(wzid != null || wzid != "") {
			if(wzid.indexOf(",") != -1) {
				String[] wzidArr = wzid.split(",");
				for(String wzidStr : wzidArr) {
					WtZhfwptQtArticleEdit wtZhfwptQtArticleEditStr = wtZhfwptQtArticleService.findById(wzidStr);
					//文章审核状态
					wtZhfwptQtArticleEditStr.setDqzt(dqzt);
					//文章最后操作时间
					wtZhfwptQtArticleEditStr.setDateandtime(new Date());
					//最后操作日期
					wtZhfwptQtArticleEditStr.setZhczrq(new Date());
					int num = wtZhfwptQtArticleService.updateSelective(wtZhfwptQtArticleEditStr, wtArticleQdgx);
					cnt += num;
				}
			}else {
				WtZhfwptQtArticleEdit wtZhfwptQtArticleEditStr = wtZhfwptQtArticleService.findById(wzid);
				//文章审核状态
				wtZhfwptQtArticleEditStr.setDqzt(dqzt);
				//文章最后操作时间
				wtZhfwptQtArticleEditStr.setDateandtime(new Date());
				//最后操作日期
				wtZhfwptQtArticleEditStr.setZhczrq(new Date());
				cnt = wtZhfwptQtArticleService.updateSelective(wtZhfwptQtArticleEditStr, wtArticleQdgx);
			}
		}
		return ResponeModel.ok(cnt);
	}
	
	
	@PostMapping("/gd.do")
	public ResponeModel gd(@Validated WtZhfwptQtArticleEdit wtZhfwptQtArticleEdit, WtArticleQdgx wtArticleQdgx, BindingResult bindingResult) {
		//文章审核状态
		String dqzt = wtZhfwptQtArticleEdit.getDqzt();
		String wzid = wtZhfwptQtArticleEdit.getWzid();
		int cnt = 0;
		
		System.out.println("dqzt:"+dqzt);
		System.out.println("wzid:"+wtZhfwptQtArticleEdit.getWzid());
		
		if(wzid != null || wzid != "") {
			if(wzid.indexOf(",") != -1) {
				String[] wzidArr = wzid.split(",");
				for(String wzidStr : wzidArr) {
					WtZhfwptQtArticleEdit wtZhfwptQtArticleEditStr = wtZhfwptQtArticleService.findById(wzidStr);
					//文章审核状态
					wtZhfwptQtArticleEditStr.setDqzt(dqzt);
					//文章最后操作时间
					wtZhfwptQtArticleEditStr.setDateandtime(new Date());
					//最后操作日期
					wtZhfwptQtArticleEditStr.setZhczrq(new Date());
					int num = wtZhfwptQtArticleService.updateSelective(wtZhfwptQtArticleEditStr, wtArticleQdgx);
					cnt += num;
				}
			}else {
				WtZhfwptQtArticleEdit wtZhfwptQtArticleEditStr = wtZhfwptQtArticleService.findById(wzid);
				//文章审核状态
				wtZhfwptQtArticleEditStr.setDqzt(dqzt);
				//文章最后操作时间
				wtZhfwptQtArticleEditStr.setDateandtime(new Date());
				//最后操作日期
				wtZhfwptQtArticleEditStr.setZhczrq(new Date());
				cnt = wtZhfwptQtArticleService.updateSelective(wtZhfwptQtArticleEditStr, wtArticleQdgx);
			}
		}
		return ResponeModel.ok(cnt);
	}
	
	
	@PostMapping("/savezsk.do")
	public ResponeModel savezsk(@Validated WtZhfwptQtArticleEdit wtZhfwptQtArticleEdit, WtArticleQdgx wtArticleQdgx, WtZskmx wtZskmx, BindingResult bindingResult) {
		String userid= wtZhfwptQtArticleService.getOperatorUserId();
		SysUser sysUser = sysUserService.findById(userid);

		wtZskmx.setId(oracleToolService.getCommonKey());
		wtZskmx.setLlrxm(sysUser.getName());
		wtZskmx.setLlr(userid);
		wtZskmx.setLlrq(new Date());
		wtZskmx.setZsnr(SystemUtil.replaceHtmlTag(SystemUtil.htmlspecialchars(wtZhfwptQtArticleEdit.getContent())));
		
		wtZhfwptQtArticleEdit.setZhczrq(new Date());
		wtZhfwptQtArticleEdit.setDateandtime(new Date());
		wtZhfwptQtArticleEdit.setContent(null);	//不更新文章表中的文章详情
		int cnt = wtZhfwptQtArticleService.savezsk(wtZhfwptQtArticleEdit,  wtArticleQdgx, wtZskmx);
		return ResponeModel.ok(cnt);
	}
}
