package com.seezoon.framework.modules.zhfw.dao;

import com.seezoon.framework.common.dao.BaseDao;
import com.seezoon.framework.common.dao.CrudDao;
import com.seezoon.framework.modules.zhfw.entity.Froperaccnt;
import com.seezoon.framework.modules.zhfw.entity.IndexQdjbxx;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by zengqy on 2018/8/14.
 */
public interface FroperaccntDao extends CrudDao<Froperaccnt>{


    /**
     * 根据用户姓名和用户类别和用户姓名查询用户信息
     * @param yhxm 用户姓名
     * @param yhlbdm  用户类别
     * @return
     */
    public List<Map<String,Object>> findUser(@Param("yhxm") String yhxm,@Param("yhlbdm") String yhlbdm);

    List<Map<String,Object>> getYhqdgl(@Param("froaid") String froaid);

    int changeZt(@Param("froaid") String froaid,@Param("qdid") String qdid,@Param("newDqzt") String newDqzt);

    int insertQdgx(@Param("froaid") String froaid,@Param("qdid") String qdid,@Param("newDqzt") String newDqzt);

    int resetWxhm(String froaid);
}
