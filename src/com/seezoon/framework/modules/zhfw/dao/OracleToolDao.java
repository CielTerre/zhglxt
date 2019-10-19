package com.seezoon.framework.modules.zhfw.dao;

import com.seezoon.framework.common.dao.BaseDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by zengqy on 2018/7/15.
 */
public interface OracleToolDao extends BaseDao {
    /**
     *
     * @param tableName 表名
     * @return 返回对应序列值
     */
    public String selectGKeyByTableName(String tableName);

    public List<Map<String,Object>> selectInfo(@Param("sqlStr") String sqlStr);
}
