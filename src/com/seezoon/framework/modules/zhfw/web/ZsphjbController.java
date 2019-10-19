package com.seezoon.framework.modules.zhfw.web;

import java.io.Serializable;
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
import com.seezoon.framework.modules.zhfw.entity.Zsphjb;
import com.seezoon.framework.modules.zhfw.service.ZsphjbService;

/**
 * 审批痕迹表controller
 */
@RestController
@RequestMapping("${admin.path}/zhfw/sphjb")
public class ZsphjbController extends BaseController {

	@Autowired
	private ZsphjbService zsphjbService;

	@RequiresPermissions("zhfw:sphjb:qry")
	@PostMapping("/qryPage.do")
	public ResponeModel qryPage(Zsphjb zsphjb) {
		PageInfo<Zsphjb> page = zsphjbService.findByPage(zsphjb, zsphjb.getPage(), zsphjb.getPageSize());
		return ResponeModel.ok(page);
	}
	@RequiresPermissions("zhfw:sphjb:qry")
	@RequestMapping("/get.do")
	public ResponeModel get(@RequestParam Serializable id) {
		Zsphjb zsphjb = zsphjbService.findById(id);
		//富文本处理
		return ResponeModel.ok(zsphjb);
	}
	@RequiresPermissions("zhfw:sphjb:save")
	@PostMapping("/save.do")
	public ResponeModel save(@Validated Zsphjb zsphjb, BindingResult bindingResult) {
		int cnt = zsphjbService.save(zsphjb);
		return ResponeModel.ok(cnt);
	}
	@RequiresPermissions("zhfw:sphjb:update")
	@PostMapping("/update.do")
	public ResponeModel update(@Validated Zsphjb zsphjb, BindingResult bindingResult) {
		int cnt = zsphjbService.updateSelective(zsphjb);
		return ResponeModel.ok(cnt);
	}
	@RequiresPermissions("zhfw:sphjb:delete")
	@PostMapping("/delete.do")
	public ResponeModel delete(@RequestParam Serializable id) {
		int cnt = zsphjbService.deleteById(id);
		return ResponeModel.ok(cnt);
	}
}
