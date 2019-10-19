package com.seezoon.framework.modules.zhfw.web;

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
import com.seezoon.framework.modules.zhfw.entity.WtZhfwptVotepotion;
import com.seezoon.framework.modules.zhfw.service.WtZhfwptVotepotionService;

import java.io.Serializable;
/**
 * 
 * 综合服务平台选票内容
 */
@RestController
@RequestMapping("${admin.path}/zhfw/wtzhfwptvotepotion")
public class WtZhfwptVotepotionController extends BaseController {

	@Autowired
	private WtZhfwptVotepotionService wtzhfwptvotepotionService;

	@RequiresPermissions("zhfw:wtzhfwptvotepotion:qry")
	@PostMapping("/qryPage.do")
	public ResponeModel qryPage(WtZhfwptVotepotion wtzhfwptvotepotion) {
		PageInfo<WtZhfwptVotepotion> page = wtzhfwptvotepotionService.findByPage(wtzhfwptvotepotion, wtzhfwptvotepotion.getPage(), wtzhfwptvotepotion.getPageSize());
		return ResponeModel.ok(page);
	}
	@RequiresPermissions("zhfw:wtzhfwptvotepotion:qry")
	@RequestMapping("/get.do")
	public ResponeModel get(@RequestParam Serializable id) {
		WtZhfwptVotepotion wtzhfwptvotepotion = wtzhfwptvotepotionService.findById(id);
		return ResponeModel.ok(wtzhfwptvotepotion);
	}
	@RequiresPermissions("zhfw:wtzhfwptvotepotion:save")
	@PostMapping("/save.do")
	public ResponeModel save(@Validated WtZhfwptVotepotion wtzhfwptvotepotion, BindingResult bindingResult) {
		int cnt = wtzhfwptvotepotionService.save(wtzhfwptvotepotion);
		return ResponeModel.ok(cnt);
	}
	@RequiresPermissions("zhfw:wtzhfwptvotepotion:update")
	@PostMapping("/update.do")
	public ResponeModel update(@Validated WtZhfwptVotepotion wtzhfwptvotepotion, BindingResult bindingResult) {
		int cnt = wtzhfwptvotepotionService.updateSelective(wtzhfwptvotepotion);
		return ResponeModel.ok(cnt);
	}
	@RequiresPermissions("zhfw:wtzhfwptvotepotion:delete")
	@PostMapping("/delete.do")
	public ResponeModel delete(@RequestParam Serializable id) {
		int cnt = wtzhfwptvotepotionService.deleteById(id);
		return ResponeModel.ok(cnt);
	}
}