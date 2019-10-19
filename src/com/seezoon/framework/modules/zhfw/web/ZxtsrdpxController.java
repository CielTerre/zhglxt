package com.seezoon.framework.modules.zhfw.web;



import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.github.pagehelper.PageInfo;
import com.seezoon.framework.common.context.beans.ResponeModel;
import com.seezoon.framework.common.web.BaseController;
import com.seezoon.framework.modules.zhfw.entity.Zxtsrdpx;
import com.seezoon.framework.modules.zhfw.service.ZxtsrdpxService;

/**
 * 
 * 咨询投诉热点排序controller
 *
 */
@RestController
@RequestMapping("${admin.path}/zhfw/statistics/zxtsrdpx")
public class ZxtsrdpxController extends BaseController{
 @Autowired
 private ZxtsrdpxService zxtsrdpxService;
 
 
    @PostMapping("/query.do")
	public ResponeModel qryPage( Zxtsrdpx zxtsrdpx,HttpServletRequest request) {
    	 String cx= request.getParameter("cx");
    	 if("1".equals(cx)){
    		 zxtsrdpx.setHits("hits"); 
    	 }
    	 if("2".equals(cx)){
    		 zxtsrdpx.setLlrq("llrq"); 
    	 }
    	 if("3".equals(cx)){
    		 zxtsrdpx.setZhfwrq("zhfwrq"); 
    	 }
    	
		PageInfo<Zxtsrdpx> page = zxtsrdpxService.findByPage(zxtsrdpx, zxtsrdpx.getPage(), zxtsrdpx.getPageSize());
		return ResponeModel.ok(page);
	}
}
