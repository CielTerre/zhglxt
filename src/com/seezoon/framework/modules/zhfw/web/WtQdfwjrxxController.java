package com.seezoon.framework.modules.zhfw.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.seezoon.framework.modules.system.entity.SysUser;
import com.seezoon.framework.modules.system.service.SysUserService;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.seezoon.framework.common.context.beans.ResponeModel;
import com.seezoon.framework.common.web.BaseController;
import com.seezoon.framework.modules.zhfw.entity.WtQdfwjrxx;
import com.seezoon.framework.modules.zhfw.service.WtQdfwjrxxService;

/**
 * 鎺ュ叆绠＄悊controller
 */
@RestController
@RequestMapping("${admin.path}/zhfw/qdfwjrxx")
public class WtQdfwjrxxController extends BaseController {

	@Autowired
	private WtQdfwjrxxService wtQdfwjrxxService;

	@Autowired
	private SysUserService sysUserService;

	@RequiresPermissions("zhfw:qdfwjrxx:qry")
	@PostMapping("/qryPage.do")
	public ResponeModel qryPage(WtQdfwjrxx wtQdfwjrxx) {
		if ("Y".equals(wtQdfwjrxx.getBgzt())){ //鏌ヨ璧勯噾鍒嗙被鐨�
			wtQdfwjrxx.setDsf(" and r.fwid in (select fwid from wt_fwdjxx where zjbd <> '0' ) ");
		}
		PageInfo<WtQdfwjrxx> page = wtQdfwjrxxService.findByPage(wtQdfwjrxx, wtQdfwjrxx.getPage(),
				wtQdfwjrxx.getPageSize());
		return ResponeModel.ok(page);
	}

	@RequiresPermissions("zhfw:qdfwjrxx:qry")
	@PostMapping("/spxx.do")
	public ResponeModel spxx(WtQdfwjrxx wtQdfwjrxx) {
		wtQdfwjrxx.setSqzt("S");
		PageInfo<WtQdfwjrxx> page = wtQdfwjrxxService.findByPage(wtQdfwjrxx, wtQdfwjrxx.getPage(),
				wtQdfwjrxx.getPageSize());
		return ResponeModel.ok(page);

	}

	@RequiresPermissions("zhfw:qdfwjrxx:qry")
	@RequestMapping("/get.do")
	public ResponeModel get(@RequestParam Serializable id) {
		WtQdfwjrxx wtQdfwjrxx = wtQdfwjrxxService.findById(id);
		// 瀵屾枃鏈鐞�
		String tmfs = wtQdfwjrxx.getTmfs();
		List<String> tmfsids = new ArrayList<String>();
		if(StringUtil.isNotEmpty(tmfs)){
			String[] ids =tmfs.split(",");
			for(String i:ids){
				tmfsids.add(i);
			}
		}
		wtQdfwjrxx.setTmfsids(tmfsids);
		return ResponeModel.ok(wtQdfwjrxx);
	}

	@RequiresPermissions("zhfw:qdfwjrxx:save")
	@PostMapping("/save.do")
	public ResponeModel save(@Validated WtQdfwjrxx wtQdfwjrxx, BindingResult bindingResult) {
		int cnt = wtQdfwjrxxService.save(wtQdfwjrxx);
		return ResponeModel.ok(cnt);
	}

	@RequiresPermissions("zhfw:qdfwjrxx:update")
	@PostMapping("/update.do")
	public ResponeModel update(@Validated WtQdfwjrxx wtQdfwjrxx, BindingResult bindingResult) {

		WtQdfwjrxx wtQdfwjrxx1 = wtQdfwjrxxService.findById(wtQdfwjrxx.getYwlsh());
		String userid = wtQdfwjrxxService.getOperatorUserId();
		SysUser sysUser = sysUserService.findById(userid);
		wtQdfwjrxx1.setZhczrzh(sysUser.getFroaid());
		wtQdfwjrxx1.setZhczrxm(sysUser.getName());
		wtQdfwjrxx1.setZhczrq(new Date());
		wtQdfwjrxx1.setDbsx(wtQdfwjrxx.getDbsx());
		wtQdfwjrxx1.setDbxx(wtQdfwjrxx.getDbxx());
		wtQdfwjrxx1.setDrsx(wtQdfwjrxx.getDrsx());
		wtQdfwjrxx1.setDrywsx(wtQdfwjrxx.getDrywsx());
		wtQdfwjrxx1.setTmfs(wtQdfwjrxx.getTmfs());

		int cnt = wtQdfwjrxxService.updateById(wtQdfwjrxx1);
		return ResponeModel.ok(cnt);
	}

	@RequiresPermissions("zhfw:qdfwjrxx:delete")
	@PostMapping("/delete.do")
	public ResponeModel delete(@RequestParam Serializable id) {
		int cnt = wtQdfwjrxxService.deleteById(id);
		return ResponeModel.ok(cnt);
	}

	// 绂佺敤鏈嶅姟
	@RequiresPermissions("zhfw:qdfwjrxx:update")
	@PostMapping("/Discontinuation.do")
	public ResponeModel edit(@RequestParam List<String> Ids,WtQdfwjrxx wtQdfwjrxx) {
		int cnt = 0;
		for (String id : Ids) {			 
		    wtQdfwjrxx = wtQdfwjrxxService.findById(id);
		    if("02".equals(wtQdfwjrxx.getFwzt())){			
				return ResponeModel.error("1","");
			}
			wtQdfwjrxx.setBgzt("02");
			wtQdfwjrxx.setSqzt("S");
			cnt = wtQdfwjrxxService.updateSelective(wtQdfwjrxx);
		}
		return ResponeModel.ok(cnt);
	}

	// 鍚敤鏈嶅姟
	@RequiresPermissions("zhfw:qdfwjrxx:save")
	@PostMapping("/startUp.do")
	public ResponeModel add(@RequestParam List<String> Ids,WtQdfwjrxx wtQdfwjrxx) {
		int cnt = 0;
		for (String id : Ids) {
		    wtQdfwjrxx = wtQdfwjrxxService.findById(id);
		    if("01".equals(wtQdfwjrxx.getFwzt())){			
				return ResponeModel.error("1","");
			}
			wtQdfwjrxx.setBgzt("01");
			wtQdfwjrxx.setSqzt("S");
			cnt = wtQdfwjrxxService.updateSelective(wtQdfwjrxx);
		}
		return ResponeModel.ok(cnt);
	}

	// 瀹℃壒鏈嶅姟
	@RequiresPermissions("zhfw:qdfwjrxx:update")
	@PostMapping("/adopt.do")
	public ResponeModel adopt(@RequestParam List<String> Ids) {
		int cnt = 0;
		for (String id : Ids) {
			WtQdfwjrxx wtQdfwjrxx = wtQdfwjrxxService.findById(id);
			if ("S".equals(wtQdfwjrxx.getSqzt())) {
				if ("02".equals(wtQdfwjrxx.getBgzt())) {
					wtQdfwjrxx.setFwzt("02");
					wtQdfwjrxx.setBgzt("");
					wtQdfwjrxx.setSqzt("");
				}
				if ("01".equals(wtQdfwjrxx.getBgzt())) {
					wtQdfwjrxx.setFwzt("01");
					wtQdfwjrxx.setBgzt("");
					wtQdfwjrxx.setSqzt("");
				}
			}
			cnt = wtQdfwjrxxService.updateSelective(wtQdfwjrxx);
		}
		return ResponeModel.ok(cnt);
	}

	// 椹冲洖鐢宠
	@RequiresPermissions("zhfw:qdfwjrxx:update")
	@PostMapping("/dismiss.do")
	public ResponeModel dismiss(@RequestParam List<String> Ids) {
		int cnt = 0;
		for (String id : Ids) {
			WtQdfwjrxx wtQdfwjrxx = wtQdfwjrxxService.findById(id);
			wtQdfwjrxx.setSqzt("");
			wtQdfwjrxx.setBgzt("");
			cnt = wtQdfwjrxxService.updateSelective(wtQdfwjrxx);
		}
		return ResponeModel.ok(cnt);
	}

	

}
