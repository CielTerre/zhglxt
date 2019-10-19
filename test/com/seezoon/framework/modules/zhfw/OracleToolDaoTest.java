package com.seezoon.framework.modules.zhfw;

import com.seezoon.framework.common.context.test.BaseJunitTest;
import com.seezoon.framework.modules.zhfw.dao.OracleToolDao;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * Created by zengqy on 2018/7/16.
 */
public class OracleToolDaoTest extends BaseJunitTest {

    @Autowired
    private OracleToolDao oracleToolDao;

    @Test
    public void testSelectGKeyByTableName() {
        String seqValue = oracleToolDao.selectGKeyByTableName("NEWWORKID");
        System.out.println(seqValue);
    }

    @Test
    public void  testSelectInfo(){
        String sql  = "select 1 from dual";
        List<Map<String,Object>> list = oracleToolDao.selectInfo(sql);
        for(Map<String,Object> tmp:list){
            for(Map.Entry entry:tmp.entrySet()){
                System.out.println(entry.getKey()+","+entry.getValue());
            }
        }


    }

}
