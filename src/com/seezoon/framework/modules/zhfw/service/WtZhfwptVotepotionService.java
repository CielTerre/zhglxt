package com.seezoon.framework.modules.zhfw.service;

import org.springframework.stereotype.Service;
import com.seezoon.framework.common.service.CrudService;
import com.seezoon.framework.modules.zhfw.dao.WtZhfwptVotepotionDao;
import com.seezoon.framework.modules.zhfw.entity.WtZhfwptVotepotion;

import java.io.Serializable;


/**
 * 综合服务平台选票内容
 **/
@Service
public class WtZhfwptVotepotionService extends CrudService<WtZhfwptVotepotionDao, WtZhfwptVotepotion> {
    public int deleteByThemeid(Serializable themeid) {
        return d.deleteByThemeid(themeid);
    }
}