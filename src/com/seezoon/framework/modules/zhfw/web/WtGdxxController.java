package com.seezoon.framework.modules.zhfw.web;

import java.io.Serializable;
import java.util.Date;

import com.seezoon.framework.modules.system.shiro.ShiroUtils;
import net.sf.json.JSONObject;
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
import com.seezoon.framework.modules.zhfw.entity.WtGdxx;
import com.seezoon.framework.modules.zhfw.service.WtGdxxService;

/**
 * 工单处理controller
 */
@RestController
@RequestMapping("${admin.path}/zhfw/gdxx")
public class WtGdxxController extends BaseController {

	@Autowired
	private WtGdxxService wtGdxxService;

	@RequiresPermissions("zhfw:gdxx:qry")
	@PostMapping("/qryPage.do")
	public ResponeModel qryPage(WtGdxx wtGdxx) {
		wtGdxx.setSortField("gdbh");
		PageInfo<WtGdxx> page = wtGdxxService.findByPage(wtGdxx, wtGdxx.getPage(), wtGdxx.getPageSize());
		return ResponeModel.ok(page);
	}

	@RequiresPermissions("zhfw:gdxx:qry")
	@PostMapping("/qryPageByUser.do")
	public ResponeModel qryPageByUser(WtGdxx wtGdxx) {
		wtGdxx.setClr(ShiroUtils.getUserId()); //需本人处理
		wtGdxx.setDsf(" and dqzt in(0,1)");//未处理或处理中
		PageInfo<WtGdxx> page = wtGdxxService.findByPage(wtGdxx, wtGdxx.getPage(), wtGdxx.getPageSize());
		return ResponeModel.ok(page);
	}

	@RequiresPermissions("zhfw:gdxx:qry")
	@PostMapping("/qryPageForHf.do")
	public ResponeModel qryPageForHf(WtGdxx wtGdxx) {
		wtGdxx.setDqzt("2"); //处理完毕的
		wtGdxx.setHfzt("0"); //未回访的
		PageInfo<WtGdxx> page = wtGdxxService.findByPage(wtGdxx, wtGdxx.getPage(), wtGdxx.getPageSize());
		return ResponeModel.ok(page);
	}

	@RequiresPermissions("zhfw:gdxx:qry")
	@RequestMapping("/get.do")
	public ResponeModel get(@RequestParam Serializable id) {
		WtGdxx wtGdxx = wtGdxxService.findById(id);
		//富文本处理
		return ResponeModel.ok(wtGdxx);
	}

	@RequiresPermissions("zhfw:gdxx:save")
	@PostMapping("/save.do")
	public ResponeModel save(@Validated WtGdxx wtGdxx, BindingResult bindingResult) {
		JSONObject jsonObject = wtGdxxService.saveGdxx(wtGdxx);
		if (jsonObject.get("saveInt")!=null){
			return ResponeModel.ok(jsonObject.getInt("saveInt"));
		}else {
			return ResponeModel.error(jsonObject.get("msg") != null ? jsonObject.getString("msg") : "");
		}
	}

	//工单管理 处理保存更新
	@RequiresPermissions("zhfw:gdxx:update")
	@PostMapping("/update.do")
	public ResponeModel update(@Validated WtGdxx wtGdxx, BindingResult bindingResult) {
		JSONObject jsonObject = wtGdxxService.updateGdxx(wtGdxx);
		if (jsonObject.get("updateInt")!=null){
			return ResponeModel.ok(jsonObject.getInt("updateInt"));
		}else {
			return ResponeModel.error(jsonObject.get("msg") != null ? jsonObject.getString("msg") : "");
		}
	}

	//回访保存更新
	@RequiresPermissions("zhfw:gdxx:update")
	@PostMapping("/updateHf.do")
	public ResponeModel updateHf(@Validated WtGdxx wtGdxx, BindingResult bindingResult) {
		wtGdxx.setHfsj(new Date());
		int cnt = wtGdxxService.updateSelective(wtGdxx);
		return ResponeModel.ok(cnt);
	}

	@RequiresPermissions("zhfw:gdxx:delete")
	@PostMapping("/delete.do")
	public ResponeModel delete(@RequestParam Serializable id) {
		int cnt = wtGdxxService.deleteById(id);
		return ResponeModel.ok(cnt);
	}
}
