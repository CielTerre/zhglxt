package com.seezoon.framework.modules.zhfw.service;

import com.seezoon.framework.common.http.HttpRequestUtils;
import com.seezoon.framework.modules.zhfw.entity.DxFshjb;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import com.seezoon.framework.common.service.CrudService;
import com.seezoon.framework.modules.zhfw.dao.WtSjyzmxxDao;
import com.seezoon.framework.modules.zhfw.entity.WtSjyzmxx;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ������أ��ֻ�ע������Ϣ��Service
 * Copyright &copy; 2018 powered by huangdf, All rights reserved.
 * @author hdf 2018-9-12 16:35:17
 */
@Service
public class WtSjyzmxxService extends CrudService<WtSjyzmxxDao, WtSjyzmxx>{

//    /** 验证手机验证码  */
    public JSONObject checkVerificationCode(String qdid, String sjhm, String dtmyz, String verifcode) {
        JSONObject JObject = new JSONObject();
        //获取该手机号码最近一次的此短息模板的验证信息
        WtSjyzmxx wtSjyzmxx = new WtSjyzmxx();
        wtSjyzmxx.setQdid(qdid);
        wtSjyzmxx.setSjhm(sjhm);
        wtSjyzmxx.setDtmyz(dtmyz);
        wtSjyzmxx.setSortField("yzmfssj");
        wtSjyzmxx.setDirection("desc");
        List<WtSjyzmxx> list = this.findList(wtSjyzmxx);
        if (list == null || list.size()<1){
            JObject.put("success", "false");
            JObject.put("msg", "验证信息不存在");
            return JObject;
        }
        WtSjyzmxx wtSjyzmxxOld = list.get(0);
        //验证"验证码"信息
        if (wtSjyzmxxOld == null || !wtSjyzmxxOld.getYzm().equals(verifcode)) {
            JObject.put("success", "false");
            JObject.put("msg", "验证码输入不正确");
            return JObject;
        }
        if (!wtSjyzmxxOld.getYzmzt().equals("Y")) {
            JObject.put("success", "false");
            JObject.put("msg", "验证码已过期");
            return JObject;
        }
        //验证码超过10分钟没验证则自动作废
        if ((new Date().getTime() - wtSjyzmxxOld.getYzmfssj().getTime()) > 10 * 60 * 1000) {
            //作废原有的验证
            WtSjyzmxx wtSjyzmxxNew = new WtSjyzmxx();
            wtSjyzmxxNew.setYwlsh(wtSjyzmxxOld.getYwlsh());
            wtSjyzmxxNew.setYzmzt("F");
            d.updateByPrimaryKeySelective(wtSjyzmxxNew);

            JObject.put("success", "false");
            JObject.put("msg", "验证码已过期");
            return JObject;
        }
        //验证通过更新验证码为已验证
        WtSjyzmxx wtSjyzmxxNew = new WtSjyzmxx();
        wtSjyzmxxNew.setYwlsh(wtSjyzmxxOld.getYwlsh());
        wtSjyzmxxNew.setYzmzt("N");
        d.updateByPrimaryKeySelective(wtSjyzmxxNew);

        JObject.put("success", "true");
        return JObject;
    }
//
//    /** 发送短信新方法(承大) */
    public JSONObject sendDxMsgNewCd(DxFshjb dxfshjb){
        JSONObject JObject = new JSONObject();

        try {
            String ip = "192.168.101.127";
            String port = "7001";
            Map<String,String> map = new HashMap();
            map.put("ywlsh",dxfshjb.getYwlsh());    //短信业务流水号
            map.put("sjhm",dxfshjb.getSjhm());      //手机号码
            map.put("sjssqy",dxfshjb.getSjssqy());  //手机所属区域
            map.put("yhwybh","szgjj");              //客户编号（12329唯一标识）
            map.put("dxnr",dxfshjb.getDxnr());      //短信内容

            String result = HttpRequestUtils.doGet("http://"+ip+":"+port+ "/szdxinterface/dxfsInterfaceServlet",map);
            System.out.println("短信结果："+result);
            JObject = JSONObject.fromObject(result);
            if (JObject.get("result") != null && JObject.getString("result").equals("false")) {
                JObject.put("success", "false");
                JObject.put("msg", "短信发送失败，"+JObject.get("msg"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            JObject.put("success", "false");
            JObject.put("msg", "中心方：连接异常！");
        }
        return JObject;
    }
}
