package com.seezoon.framework.modules.zhfw.web;

import java.io.Serializable;
import java.util.Date;

import com.seezoon.framework.modules.system.entity.SysUser;
import com.seezoon.framework.modules.system.service.SysUserService;
import com.seezoon.framework.modules.zhfw.dao.OracleToolDao;
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
import com.seezoon.framework.modules.zhfw.entity.WtZskmx;
import com.seezoon.framework.modules.zhfw.service.WtZskmxService;

/**
 * 知识库详细内容controller
 */
@RestController
@RequestMapping("${admin.path}/zhfw/zskmx")
public class WtZskmxController extends BaseController {

	@Autowired
	private WtZskmxService wtZskmxService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private OracleToolService oracleToolService;

	@RequiresPermissions("zhfw:zskmx:qry")
	@PostMapping("/qryPage.do")
	public ResponeModel qryPage(WtZskmx wtZskmx) {
		PageInfo<WtZskmx> page = wtZskmxService.findByPage(wtZskmx, wtZskmx.getPage(), wtZskmx.getPageSize());
		return ResponeModel.ok(page);
	}
	@RequiresPermissions("zhfw:zskmx:qry")
	@RequestMapping("/get.do")
	public ResponeModel get(@RequestParam Serializable id) {
		WtZskmx wtZskmx = wtZskmxService.findById(id);
		//富文本处理

		return ResponeModel.ok(wtZskmx);
	}
	@RequiresPermissions("zhfw:zskmx:save")
	@PostMapping("/save.do")
	public ResponeModel save(@Validated WtZskmx wtZskmx, BindingResult bindingResult) {
		String userid= wtZskmxService.getOperatorUserId();
		SysUser sysUser = sysUserService.findById(userid);

		wtZskmx.setId(oracleToolService.getCommonKey());
		wtZskmx.setLlrxm(sysUser.getName());
		wtZskmx.setLlr(userid);
		wtZskmx.setLlrq(new Date());

		wtZskmx.setZsnr(SystemUtil.htmlspecialchars(wtZskmx.getZsnr()));//富文本保存处理

		int cnt = wtZskmxService.save(wtZskmx);
		return ResponeModel.ok(cnt);
	}

	@RequiresPermissions("zhfw:zskmx:update")
	@PostMapping("/update.do")
	public ResponeModel update(@Validated WtZskmx wtZskmx, BindingResult bindingResult) {
		wtZskmx.setZsnr(SystemUtil.htmlspecialchars(wtZskmx.getZsnr()));//富文本保存处理
		int cnt = wtZskmxService.updateSelective(wtZskmx);
		return ResponeModel.ok(cnt);
	}

	@RequiresPermissions("zhfw:zskmx:delete")
	@PostMapping("/delete.do")
	public ResponeModel delete(@RequestParam Serializable id) {
		int cnt = wtZskmxService.deleteById(id);
		return ResponeModel.ok(cnt);
	}
}
