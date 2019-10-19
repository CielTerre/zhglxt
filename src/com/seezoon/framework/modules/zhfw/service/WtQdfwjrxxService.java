package com.seezoon.framework.modules.zhfw.service;

import com.seezoon.framework.modules.zhfw.dao.OracleToolDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.seezoon.framework.common.service.CrudService;
import com.seezoon.framework.modules.zhfw.dao.WtQdfwjrxxDao;
import com.seezoon.framework.modules.zhfw.entity.WtQdfwjrxx;

/**
 * 接入管理Service
 */
@Service
public class WtQdfwjrxxService extends CrudService<WtQdfwjrxxDao, WtQdfwjrxx>{

    @Autowired
    private OracleToolDao oracleToolDao;

    public String getYwlsh(){
      return   oracleToolDao.selectGKeyByTableName("NEWWORKID");
    }
}
