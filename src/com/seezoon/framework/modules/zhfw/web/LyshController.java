package com.seezoon.framework.modules.zhfw.web;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.seezoon.framework.common.context.beans.ResponeModel;
import com.seezoon.framework.common.web.BaseController;
import com.seezoon.framework.modules.zhfw.entity.Lycl;
import com.seezoon.framework.modules.zhfw.entity.WtGdxx;
import com.seezoon.framework.modules.zhfw.entity.WtQdfwjrxx;
import com.seezoon.framework.modules.zhfw.service.LyclService;
import com.seezoon.framework.modules.zhfw.service.WtGdxxService;

/**
 * 
 * 留言审核
 *
 */

@RestController
@RequestMapping("${admin.path}/zhfw/lysh")
public class LyshController extends BaseController {
	@Autowired
	private LyclService lyclService;
	@Autowired
	private WtGdxxService wtGdxxService;

	@RequestMapping("/getgd.do")
	public ResponeModel getgd(@RequestParam Serializable id) {
		WtGdxx wtGdxx = wtGdxxService.findById(id);
		return ResponeModel.ok(wtGdxx);
	}

	@RequestMapping("/get.do")
	public ResponeModel get(@RequestParam Serializable id) {
		Lycl lycl = lyclService.findById(id);
		return ResponeModel.ok(lycl);
	}

	@PostMapping("/qryPage.do")
	public ResponeModel qrypage(Lycl lycl) {
		lycl.setSortField("addtime");
		lycl.setShzt("02");
		PageInfo<Lycl> page = lyclService.findByPage(lycl, lycl.getPage(), lycl.getPageSize());
		return ResponeModel.ok(page);
	}

	// 通过
	@RequestMapping("/adopt.do")
	public ResponeModel adopt(@RequestParam List<String> Ids) {
		int cnt = 0;
		for (String id : Ids) {
			Lycl lycl = lyclService.findById(id);
			lycl.setShzt("01");
			cnt = lyclService.updateSelective(lycl);
		}
		return ResponeModel.ok(cnt);
	}

	// 驳回
	@RequestMapping("/dismiss.do")
	public ResponeModel dismiss(@RequestParam List<String> Ids) {
		int cnt = 0;
		for (String id : Ids) {
			Lycl lycl = lyclService.findById(id);
			lycl.setShzt("04");
			cnt = lyclService.updateSelective(lycl);
		}
		return ResponeModel.ok(cnt);
	}

}
