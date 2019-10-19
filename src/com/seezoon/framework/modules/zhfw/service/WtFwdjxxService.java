package com.seezoon.framework.modules.zhfw.service;

import com.seezoon.framework.modules.zhfw.dao.OracleToolDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.seezoon.framework.common.service.CrudService;
import com.seezoon.framework.modules.zhfw.dao.WtFwdjxxDao;
import com.seezoon.framework.modules.zhfw.entity.WtFwdjxx;

import java.util.List;
import java.util.Map;

/**
 * 网厅相关（核心服务登记信息）Service
 */
@Service
public class WtFwdjxxService extends CrudService<WtFwdjxxDao, WtFwdjxx>{

    @Autowired
    private OracleToolDao oracleToolDao;

    public String genFwid(){
        return oracleToolDao.selectGKeyByTableName("ALL");
    }

    public List<Map<String,Object>> selectBindService(String qdid, String fwfl, String fwsm){
        return d.selectBindService(qdid,fwfl,fwsm);
    }


}
