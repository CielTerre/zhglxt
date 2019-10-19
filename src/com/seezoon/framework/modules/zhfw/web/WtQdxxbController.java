package com.seezoon.framework.modules.zhfw.web;

import com.seezoon.framework.modules.system.entity.SysUser;
import com.seezoon.framework.modules.system.service.SysUserService;
import com.seezoon.framework.modules.zhfw.entity.WtQdfwjrxx;
import com.seezoon.framework.modules.zhfw.service.WtQdfwjrxxService;
import com.seezoon.framework.modules.zhfw.util.TokenUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
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
import com.seezoon.framework.modules.zhfw.entity.WtQdxxb;
import com.seezoon.framework.modules.zhfw.service.WtQdxxbService;

/**
 * 渠道设置controller
 */
@RestController
@RequestMapping("${admin.path}/zhfw/qdxxb")
public class WtQdxxbController extends BaseController {

	@Autowired
	private WtQdxxbService wtQdxxbService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private WtQdfwjrxxService wtQdfwjrxxService;
	@Autowired
	private StringRedisTemplate redisTemplate; //缓存

	@RequiresPermissions("zhfw:qdxxb:qry")
	@PostMapping("/qryPage.do")
	public ResponeModel qryPage(WtQdxxb wtQdxxb) {
		wtQdxxb.setSortField("qdid");
		wtQdxxb.setDirection("asc");
		PageInfo<WtQdxxb> page = wtQdxxbService.findByPage(wtQdxxb, wtQdxxb.getPage(), wtQdxxb.getPageSize());
		return ResponeModel.ok(page);
	}

	@RequestMapping("/qryAll.do")
	public ResponeModel qryAll(WtQdxxb wtQdxxb) {
	//	wtQdxxb.setDsf(" qdid <> 'QD000'");
		wtQdxxb.setSortField("qdid");
		wtQdxxb.setDirection("asc");
		List<WtQdxxb> list = wtQdxxbService.findList(wtQdxxb);
		return ResponeModel.ok(list);
	}

	@RequiresPermissions("zhfw:qdxxb:qry")
	@RequestMapping("/get.do")
	public ResponeModel get(@RequestParam Serializable id) {
		WtQdxxb wtQdxxb = wtQdxxbService.findById(id);
		//富文本处理
		String tmfs = wtQdxxb.getTmfs();
		List<String> tmfsids = new ArrayList<String>();
		if(StringUtil.isNotEmpty(tmfs)){
			String[] ids =tmfs.split(",");
			for(String i:ids){
				tmfsids.add(i);
			}
		}
		wtQdxxb.setTmfsids(tmfsids);
		return ResponeModel.ok(wtQdxxb);
	}
	@RequiresPermissions("zhfw:qdxxb:save")
	@PostMapping("/save.do")
	public ResponeModel save(@Validated WtQdxxb wtQdxxb, BindingResult bindingResult) {

		String userid= wtQdxxbService.getOperatorUserId();
		SysUser sysUser = sysUserService.findById(userid);
		wtQdxxb.setZhczrzh(sysUser.getFroaid());
		wtQdxxb.setZhczrxm(sysUser.getName());
		wtQdxxb.setZhczrq(new Date());
		wtQdxxb.setQdktrq(new Date());
		int cnt = wtQdxxbService.save(wtQdxxb);
		if (cnt > 0) { //保存成功 就写入缓存
			updateCache(wtQdxxb.getQdid());
		}
		return ResponeModel.ok(cnt);
	}

	@RequiresPermissions("zhfw:qdxxb:save")
	@PostMapping("/genToken.do")
	public ResponeModel genToken(@RequestParam String qdid){

		return ResponeModel.ok(TokenUtil.genToke(qdid));
	}

	@RequiresPermissions("zhfw:qdxxb:update")
	@PostMapping("/update.do")
	public ResponeModel update(@Validated WtQdxxb wtQdxxb, BindingResult bindingResult) {
		wtQdxxb.setId(wtQdxxb.getQdid());
		String userid= wtQdxxbService.getOperatorUserId();
		SysUser sysUser = sysUserService.findById(userid);
		wtQdxxb.setZhczrzh(sysUser.getFroaid());
		wtQdxxb.setZhczrxm(sysUser.getName());
		wtQdxxb.setZhczrq(new Date());
		int cnt = wtQdxxbService.updateSelective(wtQdxxb);
		if (cnt > 0) {//保存成功 就写入缓存
			updateCache(wtQdxxb.getQdid());
		}
		return ResponeModel.ok(cnt);
	}

	@RequiresPermissions("zhfw:qdxxb:update")
	 @PostMapping("/updateDqzt.do")
	 public ResponeModel updateDqzt(@RequestParam String id,@RequestParam String qdzt) {
		WtQdxxb wtQdxxb = new WtQdxxb();
		wtQdxxb.setQdid(id);
		wtQdxxb.setId(id);
		wtQdxxb.setQdzt(qdzt);
		int cnt = wtQdxxbService.updateSelective(wtQdxxb);
		return ResponeModel.ok(cnt);
	}


	@RequiresPermissions("zhfw:qdxxb:update")
	@PostMapping("/bindServiceList.do")
	public ResponeModel bindServiceList(@RequestParam List<String> fwid,@RequestParam String qdid,@RequestParam List<String> status) {
		int cnt = 0;
		int i=0;
		WtQdfwjrxx wtQdfwjrxx = new WtQdfwjrxx();		
		for (String statu : status) {
			wtQdfwjrxx.setFwid(fwid.get(i));
			wtQdfwjrxx.setQdid(qdid);
			i++;
			if("1".equals(statu)){//解绑
				List<WtQdfwjrxx> list = wtQdfwjrxxService.findList(wtQdfwjrxx);
				if(list.size()>0){
					cnt = wtQdfwjrxxService.deleteById(list.get(0).getYwlsh());
				}
			}else { //绑定
				String ywlsh = wtQdfwjrxxService.getYwlsh();
				wtQdfwjrxx.setYwlsh(ywlsh);
				String userid= wtQdxxbService.getOperatorUserId();
				SysUser sysUser = sysUserService.findById(userid);
				wtQdfwjrxx.setId(wtQdfwjrxx.getId());
				wtQdfwjrxx.setZhczrzh(sysUser.getFroaid());
				wtQdfwjrxx.setZhczrxm(sysUser.getName());
				wtQdfwjrxx.setZhczrq(new Date());
				wtQdfwjrxx.setQyrq(new Date());
				cnt = wtQdfwjrxxService.save(wtQdfwjrxx);
			}	
		}



		return ResponeModel.ok(cnt);
	}

	@RequiresPermissions("zhfw:qdxxb:update")
	@PostMapping("/bindService.do")
	public ResponeModel bindService(@RequestParam String fwid,@RequestParam String qdid,@RequestParam String status) {

		WtQdfwjrxx wtQdfwjrxx = new WtQdfwjrxx();
		wtQdfwjrxx.setFwid(fwid);
		wtQdfwjrxx.setQdid(qdid);
		int cnt = 0;
		if("1".equals(status)){//解绑
			List<WtQdfwjrxx> list = wtQdfwjrxxService.findList(wtQdfwjrxx);
			if(list.size()>0){
				cnt = wtQdfwjrxxService.deleteById(list.get(0).getYwlsh());
			}
		}else { //绑定
			String ywlsh = wtQdfwjrxxService.getYwlsh();
			wtQdfwjrxx.setYwlsh(ywlsh);
			String userid= wtQdxxbService.getOperatorUserId();
			SysUser sysUser = sysUserService.findById(userid);
			wtQdfwjrxx.setId(wtQdfwjrxx.getId());
			wtQdfwjrxx.setZhczrzh(sysUser.getFroaid());
			wtQdfwjrxx.setZhczrxm(sysUser.getName());
			wtQdfwjrxx.setZhczrq(new Date());
			wtQdfwjrxx.setQyrq(new Date());
			cnt = wtQdfwjrxxService.save(wtQdfwjrxx);
		}

		return ResponeModel.ok(cnt);
	}

	@RequiresPermissions("zhfw:qdxxb:delete")
	@PostMapping("/delete.do")
	public ResponeModel delete(@RequestParam Serializable id) {
		int cnt = wtQdxxbService.deleteById(id);
		return ResponeModel.ok(cnt);
	}

	//渠道控制信息 写入缓存
	private void updateCache(String qdid){
		WtQdxxb wtQdxxb = wtQdxxbService.findById(qdid);
        System.out.println(qdid);
		if (wtQdxxb != null) {
			if (wtQdxxb.getZdbfl() != null) {
				redisTemplate.opsForValue().set(qdid + "_zdbfl", wtQdxxb.getZdbfl().toString()); //最大并发量
			}
			if (wtQdxxb.getDrfwsx() != null) {
				redisTemplate.opsForValue().set(qdid + "_drfwsx", wtQdxxb.getDrfwsx().toString()); //单日访问上限
			}
			if (wtQdxxb.getDrywlsx() != null) {
				redisTemplate.opsForValue().set(qdid + "_drywlsx", wtQdxxb.getDrywlsx().toString()); //单日业务量上限
			}
		}
	}
}
