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
import com.seezoon.framework.modules.zhfw.entity.WtZhfwptGuestbook;
import com.seezoon.framework.modules.zhfw.service.WtZhfwptGuestbookService;

/**
 * 综合服务平台留言管理controller
 */
@RestController
@RequestMapping("${admin.path}/zhfw/zhfwptguestbook")
public class WtZhfwptGuestbookController extends BaseController {

	@Autowired
	private WtZhfwptGuestbookService wtZhfwptGuestbookService;

	@RequiresPermissions("zhfw:zhfwptguestbook:qry")
	@PostMapping("/qryPage.do")
	public ResponeModel qryPage(WtZhfwptGuestbook wtZhfwptGuestbook) {
		PageInfo<WtZhfwptGuestbook> page = wtZhfwptGuestbookService.findByPage(wtZhfwptGuestbook, wtZhfwptGuestbook.getPage(), wtZhfwptGuestbook.getPageSize());
		return ResponeModel.ok(page);
	}
	@RequiresPermissions("zhfw:zhfwptguestbook:qry")
	@RequestMapping("/get.do")
	public ResponeModel get(@RequestParam Serializable id) {
		WtZhfwptGuestbook wtZhfwptGuestbook = wtZhfwptGuestbookService.findById(id);
		//富文本处理
		return ResponeModel.ok(wtZhfwptGuestbook);
	}
	@RequiresPermissions("zhfw:zhfwptguestbook:save")
	@PostMapping("/save.do")
	public ResponeModel save(@Validated WtZhfwptGuestbook wtZhfwptGuestbook, BindingResult bindingResult) {
		int cnt = wtZhfwptGuestbookService.save(wtZhfwptGuestbook);
		return ResponeModel.ok(cnt);
	}
	@RequiresPermissions("zhfw:zhfwptguestbook:update")
	@PostMapping("/update.do")
	public ResponeModel update(@Validated WtZhfwptGuestbook wtZhfwptGuestbook, BindingResult bindingResult) {
		int cnt = wtZhfwptGuestbookService.updateSelective(wtZhfwptGuestbook);
		return ResponeModel.ok(cnt);
	}
	@RequiresPermissions("zhfw:zhfwptguestbook:delete")
	@PostMapping("/delete.do")
	public ResponeModel delete(@RequestParam Serializable id) {
		int cnt = wtZhfwptGuestbookService.deleteById(id);
		return ResponeModel.ok(cnt);
	}
}
