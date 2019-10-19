package com.seezoon.framework.modules.zhfw.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.ss.usermodel.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.seezoon.framework.common.context.beans.ResponeModel;
import com.seezoon.framework.common.web.BaseController;
import com.seezoon.framework.modules.zhfw.entity.WtYhsdrzcx;
import com.seezoon.framework.modules.zhfw.service.WtYhsdrzcxService;


/**
 * 
 * 用户锁定日志查询controller
 *
 */
@RestController
@RequestMapping("${admin.path}/zhfw/yhsdrzcx")
public class WtYhsdrzcxController extends BaseController{
	
	@Autowired
	private WtYhsdrzcxService wtYhsdrzcxService;
	
	@PostMapping("/qryPage.do")
	public ResponeModel qryPage(WtYhsdrzcx wtYhsdrzcx) {
		PageInfo<WtYhsdrzcx> page = wtYhsdrzcxService.findByPage(wtYhsdrzcx, wtYhsdrzcx.getPage(), wtYhsdrzcx.getPageSize());
		return ResponeModel.ok(page);
	}

}
