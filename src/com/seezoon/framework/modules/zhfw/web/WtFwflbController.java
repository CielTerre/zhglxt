package com.seezoon.framework.modules.zhfw.web;

import java.io.Serializable;
import java.util.List;

import com.seezoon.framework.common.Constants;
import com.seezoon.framework.common.utils.TreeHelper;
import com.seezoon.framework.modules.system.entity.SysDept;
import org.apache.commons.lang3.StringUtils;
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
import com.seezoon.framework.modules.zhfw.entity.WtFwflb;
import com.seezoon.framework.modules.zhfw.service.WtFwflbService;

/**
 * 服务分类表controller
 */
@RestController
@RequestMapping("${admin.path}/zhfw/fwflb")
public class WtFwflbController extends BaseController {

	@Autowired
	private WtFwflbService wtFwflbService;
	private TreeHelper<WtFwflb> treeHelper = new TreeHelper<WtFwflb>();

	@RequiresPermissions("zhfw:fwflb:qry")
	@PostMapping("/qryPage.do")
	public ResponeModel qryPage(WtFwflb wtFwflb) {
		wtFwflb.setSortField("flcx");
		wtFwflb.setDirection(Constants.ASC);
		List<WtFwflb> list = wtFwflbService.findList(wtFwflb);
//		for(WtFwflb fwflb:list){
//			wtFwflb.setId(fwflb.getFwid());
//		}
		//数据机构调整
		return ResponeModel.ok(treeHelper.treeGridList(list));

//		PageInfo<WtFwflb> page = wtFwflbService.findByPage(wtFwflb, wtFwflb.getPage(), wtFwflb.getPageSize());
//		return ResponeModel.ok(page);
	}
	@RequiresPermissions("zhfw:fwflb:qry")
	@RequestMapping("/get.do")
	public ResponeModel get(@RequestParam Serializable id) {
		WtFwflb wtFwflb = wtFwflbService.findById(id);
		//富文本处理
		return ResponeModel.ok(wtFwflb);
	}
	@RequiresPermissions("zhfw:fwflb:save")
	@PostMapping("/save.do")
	public ResponeModel save(@Validated WtFwflb wtFwflb, BindingResult bindingResult) {
		WtFwflb parent = null;
		if (StringUtils.isNotEmpty(wtFwflb.getParentId())) {
			parent = wtFwflbService.findById(wtFwflb.getParentId());
		}
		treeHelper.setParent(wtFwflb, parent);
		int cnt = wtFwflbService.save(wtFwflb);
		return ResponeModel.ok(cnt);
	}
	@RequiresPermissions("zhfw:fwflb:update")
	@PostMapping("/update.do")
	public ResponeModel update(@Validated WtFwflb wtFwflb, BindingResult bindingResult) {
		wtFwflb.setId(wtFwflb.getFwid());
		WtFwflb parent = null;
		if (StringUtils.isNotEmpty(wtFwflb.getParentId())) {
			parent = wtFwflbService.findById(wtFwflb.getParentId());
		}
		treeHelper.setParent(wtFwflb, parent);
		int cnt = wtFwflbService.updateSelective(wtFwflb);
		return ResponeModel.ok(cnt);
	}
	@RequiresPermissions("zhfw:fwflb:delete")
	@PostMapping("/delete.do")
	public ResponeModel delete(@RequestParam Serializable id) {
		int cnt = wtFwflbService.deleteById(id);
		return ResponeModel.ok(cnt);
	}
}
