package com.seezoon.framework.modules.zhfw.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seezoon.framework.common.context.beans.ResponeModel;
import com.seezoon.framework.common.web.BaseController;
import com.seezoon.framework.modules.zhfw.entity.Froperaccnt;
import com.seezoon.framework.modules.zhfw.service.FroperaccntService;
import com.seezoon.framework.modules.zhfw.util.SystemUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("${admin.path}/zhfw/froperaccnt")
public class FroperaccntController extends BaseController {
    @Autowired
    private FroperaccntService froperaccntService;

//    @RequiresPermissions("zhfw:froperaccnt:qry")
    @PostMapping("/qryPage.do")
    public ResponeModel qryPage(Froperaccnt froperaccnt) {
        PageHelper.startPage(froperaccnt.getPage(), froperaccnt.getPageSize(), Boolean.TRUE);
        List<Froperaccnt> list = froperaccntService.findList(froperaccnt);
        for (Froperaccnt froperaccnt1 : list){
            froperaccnt1.setYhsjhm(SystemUtil.sgtm(froperaccnt1.getYhsjhm(), 0, 4));
            froperaccnt1.setZjhm(SystemUtil.sgtm(froperaccnt1.getZjhm(),0,4));
        }
        PageInfo<Froperaccnt> pageInfo = new PageInfo<Froperaccnt>(list);
        return ResponeModel.ok(pageInfo);
    }

//    @RequiresPermissions("zhfw:froperaccnt:qry")
    @RequestMapping("/get.do")
    public ResponeModel get(@RequestParam Serializable id) {
        Froperaccnt froperaccnt = froperaccntService.findById(id);
        //富文本处理
        return ResponeModel.ok(froperaccnt);
    }

//    @RequiresPermissions("zhfw:froperaccnt:save")
    @PostMapping("/save.do")
    public ResponeModel save(@Validated Froperaccnt froperaccnt, BindingResult bindingResult) {
        int cnt = froperaccntService.save(froperaccnt);
        return ResponeModel.ok(cnt);
    }

//    @RequiresPermissions("zhfw:froperaccnt:update")
    @PostMapping("/update.do")
    public ResponeModel update(@Validated Froperaccnt froperaccnt, BindingResult bindingResult) {
        int cnt = froperaccntService.updateSelective(froperaccnt);
        return ResponeModel.ok(cnt);
    }

//    @RequiresPermissions("zhfw:froperaccnt:delete")
    @PostMapping("/delete.do")
    public ResponeModel delete(@RequestParam Serializable id) {
        int cnt = froperaccntService.deleteById(id);
        return ResponeModel.ok(cnt);
    }

//    @RequiresPermissions("zhfw:froperaccnt:reset")
    @PostMapping("/reset.do")
    public ResponeModel reset(@RequestParam Serializable id) { //重置密码
        Froperaccnt newFroperaccnt = new Froperaccnt();
        newFroperaccnt.setFroaid((String) id);
        newFroperaccnt.setYhdlmm("888888");
        int cnt = froperaccntService.updateSelective(newFroperaccnt);
        return ResponeModel.ok(cnt);
    }

    @PostMapping("/getYhqdList.do")
    public ResponeModel getYhqdList(@RequestParam Serializable froaid) {
        PageInfo<Map<String,Object>> list = froperaccntService.getYhqdgl(String.valueOf(froaid),1,100);
        return ResponeModel.ok(list);
    }

    @PostMapping("/changeZt.do")
    public ResponeModel changeZt(@RequestParam Serializable froaid,@RequestParam Serializable qdid,@RequestParam Serializable newDqzt) {
        int cnt = froperaccntService.changeZt(String.valueOf(froaid),String.valueOf(qdid),String.valueOf(newDqzt));
        return ResponeModel.ok(cnt);
    }


}