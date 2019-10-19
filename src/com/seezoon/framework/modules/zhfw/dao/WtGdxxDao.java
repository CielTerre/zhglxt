package com.seezoon.framework.modules.zhfw.dao;

import com.seezoon.framework.common.dao.CrudDao;
import com.seezoon.framework.modules.zhfw.entity.WtGdxx;

import java.io.Serializable;

/**
 * 工单处理Dao
 * 2018-8-12 17:38:16
 */
public interface WtGdxxDao extends CrudDao<WtGdxx> {
    String getMaxGdbh(String time);
}
