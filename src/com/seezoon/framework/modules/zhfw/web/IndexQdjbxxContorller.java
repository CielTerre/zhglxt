package com.seezoon.framework.modules.zhfw.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.seezoon.framework.common.context.beans.ResponeModel;
import com.seezoon.framework.modules.zhfw.entity.IndexQdjbxx;
import com.seezoon.framework.modules.zhfw.service.IndexQdjbxxService;

@RestController
@RequestMapping("${admin.path}/main")
public class IndexQdjbxxContorller {
	
	@Autowired
	private IndexQdjbxxService indexQdjbxxService;
	
	@Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private JedisConnectionFactory jedisConnectionFactory;	//注入JedisPool

	@PostMapping("/index.do")
	public ResponeModel index() {
		List<IndexQdjbxx> list = indexQdjbxxService.selectIndex();
		return ResponeModel.ok(list);
	}
	
	@PostMapping("/indexbffwl.do")
	public ResponeModel indexbffwl() {
		//redisTemplate.opsForValue().set("wt_bffwl",new Integer(1378888));
		String tmpWz  = redisTemplate.opsForValue().get("wz_bffwl");
		String tmpWt  = redisTemplate.opsForValue().get("wt_bffwl");
		String tmpWx  = redisTemplate.opsForValue().get("wx_bffwl");
		
		/*System.out.println(tmpWz);
		System.out.println(tmpWt);
		System.out.println(tmpWx);
		System.out.println("------------");*/

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("tmpWz", tmpWz);
		jsonObject.put("tmpWt", tmpWt);
		jsonObject.put("tmpWx", tmpWx);
		return ResponeModel.ok(jsonObject);
	}
	
	@PostMapping("/indexqdfwl.do")
	public ResponeModel indexQdfwl(@RequestParam Serializable timeStr) {
		List<IndexQdjbxx> list = indexQdjbxxService.selectIndexQdfwl(timeStr);
		return ResponeModel.ok(list);
	}
	
	
	
	
	
	
	
}
