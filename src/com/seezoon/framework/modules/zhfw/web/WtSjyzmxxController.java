package com.seezoon.framework.modules.zhfw.web;

import java.io.Serializable;

import com.seezoon.framework.modules.zhfw.entity.DxFshjb;
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
import com.seezoon.framework.modules.zhfw.entity.WtSjyzmxx;
import com.seezoon.framework.modules.zhfw.service.WtSjyzmxxService;

/**
 * ������أ��ֻ�ע������Ϣ��controller
 * Copyright &copy; 2018 powered by huangdf, All rights reserved.
 * @author hdf 2018-9-12 16:35:17
 */
@RestController
@RequestMapping("${admin.path}/zfhw/sjyzmxx")
public class WtSjyzmxxController extends BaseController {

	@Autowired
	private WtSjyzmxxService wtSjyzmxxService;

	@RequiresPermissions("zfhw:sjyzmxx:qry")
	@PostMapping("/qryPage.do")
	public ResponeModel qryPage(WtSjyzmxx wtSjyzmxx) {
		PageInfo<WtSjyzmxx> page = wtSjyzmxxService.findByPage(wtSjyzmxx, wtSjyzmxx.getPage(), wtSjyzmxx.getPageSize());
		return ResponeModel.ok(page);
	}
	@RequiresPermissions("zfhw:sjyzmxx:qry")
	@RequestMapping("/get.do")
	public ResponeModel get(@RequestParam Serializable id) {
		WtSjyzmxx wtSjyzmxx = wtSjyzmxxService.findById(id);
		//���ı�����
		return ResponeModel.ok(wtSjyzmxx);
	}
	@RequiresPermissions("zfhw:sjyzmxx:save")
	@PostMapping("/save.do")
	public ResponeModel save(@Validated WtSjyzmxx wtSjyzmxx, BindingResult bindingResult) {
		int cnt = wtSjyzmxxService.save(wtSjyzmxx);
		return ResponeModel.ok(cnt);
	}
	@RequiresPermissions("zfhw:sjyzmxx:update")
	@PostMapping("/update.do")
	public ResponeModel update(@Validated WtSjyzmxx wtSjyzmxx, BindingResult bindingResult) {
		int cnt = wtSjyzmxxService.updateSelective(wtSjyzmxx);
		return ResponeModel.ok(cnt);
	}
	@RequiresPermissions("zfhw:sjyzmxx:delete")
	@PostMapping("/delete.do")
	public ResponeModel delete(@RequestParam Serializable id) {
		int cnt = wtSjyzmxxService.deleteById(id);
		return ResponeModel.ok(cnt);
	}

	@PostMapping("/sendYzm.do")
	public ResponeModel sendYzm(DxFshjb dxFshjb){

		return ResponeModel.ok();
	}
}
