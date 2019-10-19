package com.seezoon.framework.modules.zhfw.dao;

import com.seezoon.framework.common.dao.CrudDao;
import com.seezoon.framework.modules.zhfw.entity.WtFwdjxx;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 网厅相关（核心服务登记信息）Dao
 * 2018-7-16 17:40:18
 */
public interface WtFwdjxxDao extends CrudDao<WtFwdjxx> {

    public List<Map<String,Object>> selectBindService(@Param("qdid") String qdid,@Param("fwfl") String fwfl,@Param("fwsm")  String fwsm);

}
