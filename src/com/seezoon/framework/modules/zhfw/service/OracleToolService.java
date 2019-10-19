package com.seezoon.framework.modules.zhfw.service;

import com.seezoon.framework.modules.zhfw.dao.OracleToolDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by zengqy on 2018/8/16.
 */
@Service
public class OracleToolService {
    @Autowired
    private OracleToolDao oracleToolDao;

    public String getCommonKey(){
        return oracleToolDao.selectGKeyByTableName("ALL");
    }

    public List<Map<String,Object>> selectInfo(String sqlStr){
        return oracleToolDao.selectInfo(sqlStr);
    }

    public String getYwlsh(){
        return oracleToolDao.selectGKeyByTableName("NEWWORKID");
    }

}
