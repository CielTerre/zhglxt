package com.seezoon.framework.modules.zhfw.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seezoon.framework.common.service.CrudService;
import com.seezoon.framework.modules.zhfw.dao.FroperaccntDao;
import com.seezoon.framework.modules.zhfw.entity.Froperaccnt;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by zengqy on 2018/8/14.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class FroperaccntService  extends CrudService<FroperaccntDao, Froperaccnt> {

    /**
     * 根据用户姓名和用户类别和用户姓名查询用户信息
     * @param yhxm 用户姓名
     * @param yhlbdm  用户类别
     * @return
     */
    public List<Map<String,Object>> findUser(String yhxm,String yhlbdm){
        return d.findUser(yhxm,yhlbdm);
    }

    /**
     * 根据用户账号流水号 查询用户开通渠道列表
     * @param froaid 账号流水号
     * @param pageNum
     * @param pageSize
     */
    public PageInfo<Map<String,Object>> getYhqdgl(String froaid,int pageNum,int pageSize){
        PageHelper.startPage(pageNum, pageSize, Boolean.TRUE);
        List<Map<String,Object>> list = d.getYhqdgl(froaid);
        PageInfo<Map<String,Object>> pageInfo = new PageInfo<Map<String,Object>>(list);
        return pageInfo;
    }

    /**
     * 根据用户账号流水号,渠道id 改变开通状态
     * @param froaid 账号流水号
     * @param qdid 渠道ID
     * @param newDqzt 新状态
     */
    public int changeZt(String froaid, String qdid, String newDqzt) {
        int cnt = 0;
        if ("Y".equals(newDqzt)){ //新开
            cnt = d.insertQdgx(froaid,qdid,newDqzt);
        }else if ("N".equals(newDqzt)){//停用
            cnt = d.changeZt(froaid,qdid,newDqzt);
            if (qdid.equals("QD004")){ //微信需要清空微信字段
                cnt = d.resetWxhm(froaid);
            }
        }
        return cnt;
    }
}
