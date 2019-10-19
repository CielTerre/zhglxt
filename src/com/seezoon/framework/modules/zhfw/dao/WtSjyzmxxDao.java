package com.seezoon.framework.modules.zhfw.dao;

import com.seezoon.framework.common.dao.CrudDao;
import com.seezoon.framework.modules.zhfw.entity.WtSjyzmxx;

/**
 * ������أ��ֻ�ע������Ϣ��Dao
 * Copyright &copy; 2018 powered by huangdf, All rights reserved.
 * @author hdf 2018-9-12 16:35:17
 */
public interface WtSjyzmxxDao extends CrudDao<WtSjyzmxx> {
    WtSjyzmxx selectSjyzm(String qdid, String sjhm, String dtmyz);
}
