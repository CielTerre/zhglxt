package com.seezoon.framework.modules.zhfw.service;

import com.seezoon.framework.modules.system.dao.SysUserDao;
import com.seezoon.framework.modules.system.entity.SysUser;
import com.seezoon.framework.modules.zhfw.dao.OracleToolDao;
import com.seezoon.framework.modules.zhfw.dao.WtZhfwptGuestbookDao;
import com.seezoon.framework.modules.zhfw.entity.WtZhfwptGuestbook;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.seezoon.framework.common.service.CrudService;
import com.seezoon.framework.modules.zhfw.dao.WtGdxxDao;
import com.seezoon.framework.modules.zhfw.entity.WtGdxx;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 工单处理Service
 */
@Service
public class WtGdxxService extends CrudService<WtGdxxDao, WtGdxx> {
    @Autowired
    private WtZhfwptGuestbookDao guestbookDao;
    @Autowired
    private OracleToolDao oracleToolDao;
    @Autowired
    private SysUserDao sysUserDao;

    //新增工单
    public JSONObject saveGdxx(WtGdxx wtGdxx){
        JSONObject resultObject = new JSONObject();
        //获取工单编号
        String gdbh = bulidGdbh();
        wtGdxx.setGdbh(gdbh); //工单编号
        wtGdxx.setDqzt("0"); //工单状态 未处理
        wtGdxx.setHfzt("0");//回访状态 未回访
        wtGdxx.setYwlsh(oracleToolDao.selectGKeyByTableName("NEWWORKID")); //业务流水号
        SysUser sysUser = sysUserDao.selectByPrimaryKey(this.getOperatorUserId());
        wtGdxx.setLlr(sysUser.getName());
        wtGdxx.setLlrq(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        int cnt = this.save(wtGdxx);
        resultObject.put("saveInt", cnt);
        return resultObject;
    }
    //更新工单
    public JSONObject updateGdxx(WtGdxx wtGdxx) {
        JSONObject resultObject = new JSONObject();
        int cnt = this.updateSelective(wtGdxx);
        resultObject.put("updateInt", cnt);
        if (cnt > 0) {
            WtGdxx gdxx = this.findById(wtGdxx.getYwlsh());
            //工单相关来源为留言或投诉，处理完毕后更新留言状态为归档  01为投诉
            if (StringUtils.isEmpty(gdxx.getXgywlsh())) { //没有关联信息
                return resultObject;
            }
            if (!("33".equals(gdxx.getGdly()) || "8".equals(gdxx.getGdly()))) { //工单不为 投诉/建议 在线咨询
                return resultObject;
            }
            if (!"2".equals(gdxx.getDqzt())) { //工单状态不为 处理完毕
                return resultObject;
            }
            if ("1".equals(gdxx.getHfzt())){//回访状态为 已回访 已回访的话 不做其他操作
                return resultObject;
            }
            WtZhfwptGuestbook guestbook = guestbookDao.selectByPrimaryKey(gdxx.getXgywlsh());
            if (guestbook != null) {
                WtZhfwptGuestbook guestbook1 = new WtZhfwptGuestbook();
                guestbook1.setGuestbookid(guestbook.getGuestbookid());
                //更新留言状态为归档，并将回复内容设置为“您的相关留言已进入工单系统，并由对应工作人员XXX跟进处理完毕”；
                guestbook1.setRecontent(guestbook.getRecontent() + "\r\n您的相关留言已进入工单系统，并由对应工作人员" + gdxx.getClr() + "跟进处理完毕。\r\n"+gdxx.getCljg());
                guestbook1.setHfzt("01"); //01已回复 02未回复
                guestbook1.setShzt("02"); //01已审核 02未审核
                int result = guestbookDao.updateByPrimaryKeySelective(guestbook1);
                if (result != 1) {
                    resultObject.put("msg", "工单处理完毕，关联信息更新失败，请告知中心。\r\n工单编号："+gdxx.getGdbh());
                    logger.error("投诉留言工单：业务流水号<" + gdxx.getYwlsh() + ">的工单xgywlsh在【Wt_ZhfwptGuestbook】表中更新了【" + result + "】条记录。");
                }
            } else {
                resultObject.put("msg", "工单处理完毕，关联信息更新失败，请告知中心。\r\n工单编号："+gdxx.getGdbh());
                logger.error("投诉留言工单：业务流水号<" + gdxx.getYwlsh() + ">的工单xgywlsh在【Wt_ZhfwptGuestbook】表中没有查询到相关记录。");
            }
        }
        return resultObject;
    }

    //创建工单编号
    private String bulidGdbh(){ //获取当天最大值+1
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String rq = String.valueOf(year) + String.valueOf((month > 9 ? month : "0" + month)) + String.valueOf(day > 9 ? day : "0" + day);
        String gdbh = d.getMaxGdbh(rq);
        String gdT = "GD-" + rq;
        int gdbhI = Integer.valueOf(gdbh.replace(gdT, "")) + 1;
        String gdbhE = String.valueOf(gdbhI);
        int gdbhEL = gdbhE.length();
        for (int i = 0; i < 20 - gdT.length() - gdbhEL; i++) {
            gdbhE = "0" + gdbhE;
        }
        return  gdT + gdbhE;
    }
}
