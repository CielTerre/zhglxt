package com.seezoon.framework.modules.zhfw.web;
/**
 * 
 * 互动交流日志controller
 *
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.seezoon.framework.common.context.beans.ResponeModel;
import com.seezoon.framework.common.web.BaseController;
import com.seezoon.framework.modules.zhfw.entity.Hdjlrz;
import com.seezoon.framework.modules.zhfw.service.HdjlrzService;
@RestController
@RequestMapping("${admin.path}/zhfw/hdjlrz")
public class HdjlrzController extends BaseController{
	@Autowired
	private HdjlrzService hdjlrzService;
	
	@PostMapping("/query.do")
	public ResponeModel qryPage(Hdjlrz hdjlrz ){
		PageInfo<Hdjlrz> page = hdjlrzService.findByPage(hdjlrz, hdjlrz.getPage(), hdjlrz.getPageSize());
		return ResponeModel.ok(page);		
	}

	
}
