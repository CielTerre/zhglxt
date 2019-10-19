package com.seezoon.framework.modules.zhfw;

import com.seezoon.framework.common.context.beans.ResponeModel;
import com.seezoon.framework.common.context.test.BaseJunitTest;
import com.seezoon.framework.modules.zhfw.service.FroperaccntService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * Created by zengqy on 2018/8/14.
 */
public class FroperaccntServiceTest extends BaseJunitTest {

    @Autowired
    private FroperaccntService froperaccntService;

    @Test
    public void testFindUser() {
        List<Map<String,Object>> list = froperaccntService.findUser("张","06");
        System.out.println(ResponeModel.ok(list));
    }
}
