package com.seezoon.framework.modules.zhfw.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.seezoon.framework.common.context.beans.ResponeModel;
import com.seezoon.framework.common.web.BaseController;
import com.seezoon.framework.modules.zhfw.service.YhqyxxService;

/**
 * 用户签约信息Controller
 */
@RestController
@RequestMapping("${admin.path}/zhfw/yhqyxx")
public class YhqyxxController extends BaseController {
	
	@Autowired
	private YhqyxxService yhqyxxService;
	
	@PostMapping("/qryPage.do")
	public ResponeModel qryPage(@RequestParam(required=false) String qylx,@RequestParam(required=false) String zh,
			@RequestParam(required=false) String mc,@RequestParam(required=false) String zjh,
			@RequestParam(required=false) String qyrq,int page,int pageSize) {
		
		if(StringUtil.isEmpty(qylx)){
			return ResponeModel.error("签约类型不能为空");
		}
		PageInfo<Map<String,Object>> pageInfo = 
				yhqyxxService.getPageInfo(qylx, zh, mc, zjh,qyrq, page, pageSize);
		return ResponeModel.ok(pageInfo);
	}

}
