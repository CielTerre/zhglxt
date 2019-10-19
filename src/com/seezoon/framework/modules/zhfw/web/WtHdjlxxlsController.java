package com.seezoon.framework.modules.zhfw.web;

import java.io.Serializable;
import java.util.List;

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
import com.seezoon.framework.modules.zhfw.entity.WtHdjlxxls;
import com.seezoon.framework.modules.zhfw.service.WtHdjlxxlsService;

/**
 * 互动交流历史表controller
 */
@RestController
@RequestMapping("${admin.path}/zhfw/hdjlxxls")
public class WtHdjlxxlsController extends BaseController {

	@Autowired
	private WtHdjlxxlsService wtHdjlxxlsService;

	@RequiresPermissions("zhfw:hdjlxxls:qry")
	@PostMapping("/qryPage.do")
	public ResponeModel qryPage(WtHdjlxxls wtHdjlxxls) {
		PageInfo<WtHdjlxxls> page = wtHdjlxxlsService.findByPage(wtHdjlxxls, wtHdjlxxls.getPage(), wtHdjlxxls.getPageSize());
		return ResponeModel.ok(page);
	}
	@RequiresPermissions("zhfw:hdjlxxls:qry")
	@RequestMapping("/get.do")
	public ResponeModel get(@RequestParam Serializable id) {
		WtHdjlxxls wtHdjlxxls = wtHdjlxxlsService.findById(id);
		//富文本处理
		return ResponeModel.ok(wtHdjlxxls);
	}
	@RequiresPermissions("zhfw:hdjlxxls:save")
	@PostMapping("/save.do")
	public ResponeModel save(@Validated WtHdjlxxls wtHdjlxxls, BindingResult bindingResult) {
		int cnt = wtHdjlxxlsService.save(wtHdjlxxls);
		return ResponeModel.ok(cnt);
	}
	@RequiresPermissions("zhfw:hdjlxxls:update")
	@PostMapping("/update.do")
	public ResponeModel update(@Validated WtHdjlxxls wtHdjlxxls, BindingResult bindingResult) {
		int cnt = wtHdjlxxlsService.updateSelective(wtHdjlxxls);
		return ResponeModel.ok(cnt);
	}
	@RequiresPermissions("zhfw:hdjlxxls:delete")
	@PostMapping("/delete.do")
	public ResponeModel delete(@RequestParam Serializable id) {
		int cnt = wtHdjlxxlsService.deleteById(id);
		return ResponeModel.ok(cnt);
	}

	@PostMapping("/getByXgywlsh.do")
	public ResponeModel getByXgywlsh(WtHdjlxxls wtHdjlxxls) {
		wtHdjlxxls.setSortField("fysj");
		wtHdjlxxls.setDirection("asc");
		List<WtHdjlxxls> list = wtHdjlxxlsService.findList(wtHdjlxxls);
		return ResponeModel.ok(list);
	}
}
