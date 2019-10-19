package com.seezoon.framework.modules.zhfw.web;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.seezoon.framework.modules.system.entity.SysUser;
import com.seezoon.framework.modules.system.service.SysUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.github.pagehelper.PageInfo;
import com.seezoon.framework.common.context.beans.ResponeModel;
import com.seezoon.framework.common.web.BaseController;
import com.seezoon.framework.modules.zhfw.entity.WtFwdjxx;
import com.seezoon.framework.modules.zhfw.service.WtFwdjxxService;

/**
 * 网厅相关（核心服务登记信息）controller
 */
@RestController
@RequestMapping("${admin.path}/zhfw/fwdjxx")
public class WtFwdjxxController extends BaseController {

	@Autowired
	private WtFwdjxxService wtFwdjxxService;

	@Autowired
	private SysUserService sysUserService;

	@RequiresPermissions("zhfw:fwdjxx:qry")
	@PostMapping("/qryPage.do")
	public ResponeModel qryPage(WtFwdjxx wtFwdjxx) {
		PageInfo<WtFwdjxx> page = wtFwdjxxService.findByPage(wtFwdjxx, wtFwdjxx.getPage(), wtFwdjxx.getPageSize());
		return ResponeModel.ok(page);
	}

	@RequiresPermissions("zhfw:fwdjxx:qry")
	@PostMapping("/qryBindService.do")
	public ResponeModel qryBindService(@RequestParam(required=false) String qdid,@RequestParam(required=false) String fwfl,@RequestParam(required=false) String fwsm) {
		WtFwdjxx wtFwdjxx = new WtFwdjxx();
		List<Map<String,Object>> list = wtFwdjxxService.selectBindService(qdid, fwfl, fwsm);
		return ResponeModel.ok(list);
	}

	@RequiresPermissions("zhfw:fwdjxx:qry")
	@RequestMapping("/get.do")
	public ResponeModel get(@RequestParam Serializable id) {
		WtFwdjxx wtFwdjxx = wtFwdjxxService.findById(id);
		//富文本处理
		return ResponeModel.ok(wtFwdjxx);
	}
	@RequiresPermissions("zhfw:fwdjxx:save")
	@PostMapping("/save.do")
	public ResponeModel save(@Validated WtFwdjxx wtFwdjxx, BindingResult bindingResult) {


		String fwid = wtFwdjxxService.genFwid();
		wtFwdjxx.setFwid(fwid);

		String userid= wtFwdjxxService.getOperatorUserId();
		SysUser sysUser = sysUserService.findById(userid);
		wtFwdjxx.setZhczrzh(sysUser.getFroaid());
		wtFwdjxx.setZhczrxm(sysUser.getName());
		wtFwdjxx.setZhczrq(new Date());
		wtFwdjxx.setFwdjrq(new Date());

		int cnt = wtFwdjxxService.save(wtFwdjxx);
		return ResponeModel.ok(cnt);
	}
	@RequiresPermissions("zhfw:fwdjxx:update")
	@PostMapping("/update.do")
	public ResponeModel update(@Validated WtFwdjxx wtFwdjxx, BindingResult bindingResult) {


		String userid= wtFwdjxxService.getOperatorUserId();
		SysUser sysUser = sysUserService.findById(userid);
		wtFwdjxx.setZhczrzh(sysUser.getFroaid());
		wtFwdjxx.setZhczrxm(sysUser.getName());
		wtFwdjxx.setZhczrq(new Date());

		wtFwdjxx.setId(wtFwdjxx.getFwid());
		int cnt = wtFwdjxxService.updateSelective(wtFwdjxx);
		return ResponeModel.ok(cnt);
	}
	@RequiresPermissions("zhfw:fwdjxx:delete")
	@PostMapping("/delete.do")
	public ResponeModel delete(@RequestParam Serializable id) {
		int cnt = wtFwdjxxService.deleteById(id);
		return ResponeModel.ok(cnt);
	}

	@GetMapping("/qryYwmx.do")
	public ResponeModel qryYwmx() {
		WtFwdjxx wtFwdjxx = new WtFwdjxx();
		wtFwdjxx.setFwxz("1");
		List<WtFwdjxx> list = wtFwdjxxService.findList(wtFwdjxx);
		return ResponeModel.ok(list);
	}
}
