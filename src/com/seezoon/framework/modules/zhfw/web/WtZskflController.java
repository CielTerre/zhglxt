package com.seezoon.framework.modules.zhfw.web;

import java.io.Serializable;
import java.util.List;

import com.seezoon.framework.common.utils.TreeHelper;
import com.seezoon.framework.modules.zhfw.entity.WtFwflb;
import com.seezoon.framework.modules.zhfw.service.OracleToolService;
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
import com.seezoon.framework.modules.zhfw.entity.WtZskfl;
import com.seezoon.framework.modules.zhfw.service.WtZskflService;

/**
 * 知识库分类controller
 */
@RestController
@RequestMapping("${admin.path}/zhfw/zskfl")
public class WtZskflController extends BaseController {

	@Autowired
	private WtZskflService wtZskflService;
	@Autowired
	private OracleToolService oracleToolService;

	private TreeHelper<WtZskfl> treeHelper = new TreeHelper<WtZskfl>();

	@PostMapping("/qryPage.do")
	public ResponeModel qryPage(WtZskfl wtZskfl) {
		//PageInfo<WtZskfl> page = wtZskflService.findByPage(wtZskfl, wtZskfl.getPage(), wtZskfl.getPageSize());
		wtZskfl.setSortField("flcx,id");
		List<WtZskfl> list  = wtZskflService.findList(wtZskfl);
		return  ResponeModel.ok(treeHelper.treeGridList(list));
	}

	@RequestMapping("/get.do")
	public ResponeModel get(@RequestParam Serializable id) {
		WtZskfl wtZskfl = wtZskflService.findById(id);
		//富文本处理
		return ResponeModel.ok(wtZskfl);
	}

	@PostMapping("/save.do")
	public ResponeModel save(@Validated WtZskfl wtZskfl, BindingResult bindingResult) {
		wtZskfl.setId(oracleToolService.getCommonKey());
		if (StringUtils.isEmpty(wtZskfl.getParentId())){
			wtZskfl.setParentId("0000000000");
			wtZskfl.setParentIds("0000000000,");
		}else {
			WtZskfl parent = wtZskflService.findById(wtZskfl.getParentId());
			wtZskfl.setParentIds(parent.getParentIds() + "," + parent.getId());
		}
		int cnt = wtZskflService.save(wtZskfl);
		return ResponeModel.ok(cnt);
	}

	@PostMapping("/update.do")
	public ResponeModel update(@Validated WtZskfl wtZskfl, BindingResult bindingResult) {
		int cnt = wtZskflService.updateSelective(wtZskfl);
		return ResponeModel.ok(cnt);
	}

	@PostMapping("/delete.do")
	public ResponeModel delete(@RequestParam Serializable id) {
		int cnt = wtZskflService.deleteById(id);
		return ResponeModel.ok(cnt);
	}
}
