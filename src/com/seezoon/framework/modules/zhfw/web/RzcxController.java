package com.seezoon.framework.modules.zhfw.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.seezoon.framework.common.context.beans.ResponeModel;
import com.seezoon.framework.common.web.BaseController;
import com.seezoon.framework.modules.zhfw.entity.WtHdjlxx;
import com.seezoon.framework.modules.zhfw.entity.WtYhfwrz;
import com.seezoon.framework.modules.zhfw.service.WtHdjlxxService;
import com.seezoon.framework.modules.zhfw.service.WtYhfwrzService;

/**
 * 
 * 用户访问日志controller
 *
 */
@RestController
@RequestMapping("${admin.path}/zhfw/rzcx")
public class RzcxController extends BaseController{
	
	@Autowired
	private WtYhfwrzService wtYhfwrzService;
	
	@PostMapping("/qryPage.do")
	public ResponeModel qryPage(WtYhfwrz wtYhfwrz) {
		PageInfo<WtYhfwrz> page = wtYhfwrzService.findByPage(wtYhfwrz, wtYhfwrz.getPage(), wtYhfwrz.getPageSize());
		return ResponeModel.ok(page);
	}

}
