package com.seezoon.framework.modules.zhfw.web;

import java.io.Serializable;
import java.util.Date;

import com.seezoon.framework.modules.zhfw.entity.WtZhfwptGuestbook;
import com.seezoon.framework.modules.zhfw.entity.WtZskmx;
import com.seezoon.framework.modules.zhfw.service.WtZhfwptGuestbookService;
import com.seezoon.framework.modules.zhfw.service.WtZskmxService;

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

import com.seezoon.framework.modules.zhfw.entity.Lycl;
import com.seezoon.framework.modules.zhfw.entity.WtGdxx;
import com.seezoon.framework.modules.zhfw.service.LyclService;
import com.seezoon.framework.modules.zhfw.service.WtGdxxService;

import net.sf.json.JSONObject;
@RestController
@RequestMapping("${admin.path}/zhfw/lycl")
public class LyclController extends BaseController{
 
	@Autowired
	private LyclService lyclService;
	@Autowired
	private WtGdxxService wtGdxxService;
	@Autowired
	private WtZhfwptGuestbookService wtZhfwptGuestbookService;
    @Autowired
    private WtZskmxService wtZskmxService;
	
	@RequestMapping("/getgd.do")
	public ResponeModel getgd(@RequestParam Serializable id) {
		WtGdxx wtGdxx = wtGdxxService.findById(id);
		return ResponeModel.ok(wtGdxx);
	}
	@RequestMapping("/get.do")
	public ResponeModel getly(@RequestParam Serializable id) {
		Lycl lycl = lyclService.findById(id);
		return ResponeModel.ok(lycl);
	}
	@PostMapping("/save")
	public ResponeModel save(@Validated WtGdxx wtGdxx, BindingResult bindingResult,@RequestParam Serializable id,@RequestParam String gdbhs) {
        System.out.println(gdbhs);
        String[] gdbh = gdbhs.split(",");
		if(gdbh[0]!=null & !("undefined".equals(gdbh[0])) & id.equals(wtGdxx.getXgywlsh())){			
			return ResponeModel.error("1", "");
		}
		JSONObject jsonObject = wtGdxxService.saveGdxx(wtGdxx);
		if (jsonObject.get("saveInt")!=null){
			return ResponeModel.ok(jsonObject.getInt("saveInt"));
		}else {
			return ResponeModel.error(jsonObject.get("msg") != null ? jsonObject.getString("msg") : "");
		}
	}
	@PostMapping("update")
	public ResponeModel update(@RequestParam Serializable id,@RequestParam(required = false) String recontent){
		  WtZhfwptGuestbook lycl = wtZhfwptGuestbookService.findById(id);
		  lycl.setShzt("02");
		  lycl.setRecontent(recontent);
		  lycl.setRetime(new Date());
		  int cnt = wtZhfwptGuestbookService.updateSelective(lycl);
		  return  ResponeModel.ok(cnt);
	}
	
	@PostMapping("/delete.do")
	public ResponeModel delete(@RequestParam Serializable id) {
		int cnt = lyclService.deleteid(id);
		return ResponeModel.ok(cnt);
	}
	
	@PostMapping("/qryPage.do")
	public ResponeModel qrypage(Lycl lycl){		
		lycl.setSortField("addtime");
		lycl.setDsf(" and shzt in('00','04')");
		PageInfo<Lycl> page = lyclService.findByPage(lycl, lycl.getPage(), lycl.getPageSize());
		return ResponeModel.ok(page);		
	}
	
    @RequestMapping("/submission.do")
	public ResponeModel submission(@RequestParam Serializable id ) {
		Lycl lycl = lyclService.findById(id);		
		lycl.setShzt("02");			
		int cnt = lyclService.updateSelective(lycl);
		return ResponeModel.ok(cnt);
	}
    @RequestMapping("/zsnr.do")
   	public ResponeModel zsnr(WtZskmx wtZskmx) {   	
    	
    	PageInfo<WtZskmx> page = wtZskmxService.findByPage(wtZskmx, wtZskmx.getPage(), wtZskmx.getPageSize());
		return ResponeModel.ok(page);
   		
   	}
}
