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
import com.seezoon.framework.modules.zhfw.entity.WtArticleQdgx;
import com.seezoon.framework.modules.zhfw.service.WtArticleQdgxService;

/**
 * 文章渠道关系表controller
 */
@RestController
@RequestMapping("${admin.path}/zhfw/articleqdgx")
public class WtArticleQdgxController extends BaseController {

	@Autowired
	private WtArticleQdgxService wtArticleQdgxService;

	@RequiresPermissions("zhfw:articleqdgx:qry")
	@PostMapping("/qryPage.do")
	public ResponeModel qryPage(WtArticleQdgx wtArticleQdgx) {
		PageInfo<WtArticleQdgx> page = wtArticleQdgxService.findByPage(wtArticleQdgx, wtArticleQdgx.getPage(), wtArticleQdgx.getPageSize());
		return ResponeModel.ok(page);
	}
	@RequiresPermissions("zhfw:articleqdgx:qry")
	@RequestMapping("/get.do")
	public ResponeModel get(@RequestParam Serializable id) {
		WtArticleQdgx wtArticleQdgx = wtArticleQdgxService.findById(id);
		//富文本处理
		return ResponeModel.ok(wtArticleQdgx);
	}
	@RequiresPermissions("zhfw:articleqdgx:save")
	@PostMapping("/save.do")
	public ResponeModel save(@Validated WtArticleQdgx wtArticleQdgx, BindingResult bindingResult) {
		int cnt = wtArticleQdgxService.save(wtArticleQdgx);
		return ResponeModel.ok(cnt);
	}
	@RequiresPermissions("zhfw:articleqdgx:update")
	@PostMapping("/update.do")
	public ResponeModel update(@Validated WtArticleQdgx wtArticleQdgx, BindingResult bindingResult) {
		int cnt = wtArticleQdgxService.updateSelective(wtArticleQdgx);
		return ResponeModel.ok(cnt);
	}
	@RequiresPermissions("zhfw:articleqdgx:delete")
	@PostMapping("/delete.do")
	public ResponeModel delete(@RequestParam Serializable id) {
		int cnt = wtArticleQdgxService.deleteById(id);
		return ResponeModel.ok(cnt);
	}
}
