package com.seezoon.framework.modules.zhfw.dao;

import com.seezoon.framework.common.dao.CrudDao;
import com.seezoon.framework.modules.zhfw.entity.WtZhfwptThem;


/**
 * 综合服务平台投票主题管理
 **/
public interface WtZhfwptThemDao extends CrudDao<WtZhfwptThem> {
    int deleteByThemeid(String themeid);
}