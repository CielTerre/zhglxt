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
import com.seezoon.framework.modules.zhfw.entity.WtQdxxb;
import com.seezoon.framework.modules.zhfw.entity.WtZhfwptQtClass;
import com.seezoon.framework.modules.zhfw.service.WtZhfwptQtClassService;

/**
 * 综合服务平台前台栏目controller
 */
@RestController
@RequestMapping("${admin.path}/zhfw/zhfwptqtclass")
public class WtZhfwptQtClassController extends BaseController {

	@Autowired
	private WtZhfwptQtClassService wtZhfwptQtClassService;

	@RequiresPermissions("zhfw:zhfwptqtclass:qry")
	@PostMapping("/qryPage.do")
	public ResponeModel qryPage(WtZhfwptQtClass wtZhfwptQtClass) {
		PageInfo<WtZhfwptQtClass> page = wtZhfwptQtClassService.findByPage(wtZhfwptQtClass, wtZhfwptQtClass.getPage(), wtZhfwptQtClass.getPageSize());
		return ResponeModel.ok(page);
	}
	
	@RequestMapping("/qryAll.do")
	public ResponeModel qryAll(WtZhfwptQtClass wtZhfwptQtClass) {
		wtZhfwptQtClass.setSortField("classid");
		wtZhfwptQtClass.setDirection("asc");
		List<WtZhfwptQtClass> list = wtZhfwptQtClassService.findList(wtZhfwptQtClass);
		return ResponeModel.ok(list);
	}
		
	@RequiresPermissions("zhfw:zhfwptqtclass:qry")
	@RequestMapping("/get.do")
	public ResponeModel get(@RequestParam Serializable id) {
		WtZhfwptQtClass wtZhfwptQtClass = wtZhfwptQtClassService.findById(id);
		//富文本处理
		return ResponeModel.ok(wtZhfwptQtClass);
	}
	@RequiresPermissions("zhfw:zhfwptqtclass:save")
	@PostMapping("/save.do")
	public ResponeModel save(@Validated WtZhfwptQtClass wtZhfwptQtClass, BindingResult bindingResult) {
		int cnt = wtZhfwptQtClassService.save(wtZhfwptQtClass);
		return ResponeModel.ok(cnt);
	}
	@RequiresPermissions("zhfw:zhfwptqtclass:update")
	@PostMapping("/update.do")
	public ResponeModel update(@Validated WtZhfwptQtClass wtZhfwptQtClass, BindingResult bindingResult) {
		int cnt = wtZhfwptQtClassService.updateSelective(wtZhfwptQtClass);
		return ResponeModel.ok(cnt);
	}
	@RequiresPermissions("zhfw:zhfwptqtclass:delete")
	@PostMapping("/delete.do")
	public ResponeModel delete(@RequestParam Serializable id) {
		int cnt = wtZhfwptQtClassService.deleteById(id);
		return ResponeModel.ok(cnt);
	}
}
