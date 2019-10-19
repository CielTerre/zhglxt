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
import com.seezoon.framework.modules.zhfw.entity.WtHdjlxx;
import com.seezoon.framework.modules.zhfw.service.WtHdjlxxService;

/**
 * 互动交流信息表controller
 */
@RestController
@RequestMapping("${admin.path}/zhfw/hdjlxx")
public class WtHdjlxxController extends BaseController {

	@Autowired
	private WtHdjlxxService wtHdjlxxService;

//	@RequiresPermissions("zhfw:hdjlxx:qry")
	@PostMapping("/qryPage.do")
	public ResponeModel qryPage(WtHdjlxx wtHdjlxx) {
		wtHdjlxx.setDsf(" and kfxm is not null ");
		PageInfo<WtHdjlxx> page = wtHdjlxxService.findByPage(wtHdjlxx, wtHdjlxx.getPage(), wtHdjlxx.getPageSize());
		return ResponeModel.ok(page);
	}
	@RequiresPermissions("zhfw:hdjlxx:qry")
	@RequestMapping("/get.do")
	public ResponeModel get(@RequestParam Serializable id) {
		WtHdjlxx wtHdjlxx = wtHdjlxxService.findById(id);
		//富文本处理
		return ResponeModel.ok(wtHdjlxx);
	}
	@RequiresPermissions("zhfw:hdjlxx:save")
	@PostMapping("/save.do")
	public ResponeModel save(@Validated WtHdjlxx wtHdjlxx, BindingResult bindingResult) {
		int cnt = wtHdjlxxService.save(wtHdjlxx);
		return ResponeModel.ok(cnt);
	}
	@RequiresPermissions("zhfw:hdjlxx:update")
	@PostMapping("/update.do")
	public ResponeModel update(@Validated WtHdjlxx wtHdjlxx, BindingResult bindingResult) {
		int cnt = wtHdjlxxService.updateSelective(wtHdjlxx);
		return ResponeModel.ok(cnt);
	}
	@RequiresPermissions("zhfw:hdjlxx:delete")
	@PostMapping("/delete.do")
	public ResponeModel delete(@RequestParam Serializable id) {
		int cnt = wtHdjlxxService.deleteById(id);
		return ResponeModel.ok(cnt);
	}
}
