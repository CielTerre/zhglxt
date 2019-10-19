package com.seezoon.framework.modules.zhfw.dao;

 import com.seezoon.framework.common.dao.CrudDao;
import com.seezoon.framework.modules.zhfw.entity.WtZhfwptVotepotion;

 import java.io.Serializable;


/**
*综合服务平台选票内容
**/
public interface WtZhfwptVotepotionDao extends CrudDao<WtZhfwptVotepotion>{

    int deleteByThemeid(Serializable id);
}