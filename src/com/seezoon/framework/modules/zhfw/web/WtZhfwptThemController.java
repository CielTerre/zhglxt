package com.seezoon.framework.modules.zhfw.web;

import com.seezoon.framework.modules.zhfw.entity.WtZhfwptVotepotion;
import com.seezoon.framework.modules.zhfw.service.OracleToolService;
import com.seezoon.framework.modules.zhfw.service.WtZhfwptVotepotionService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.github.pagehelper.PageInfo;
import com.seezoon.framework.common.context.beans.ResponeModel;
import com.seezoon.framework.common.web.BaseController;
import com.seezoon.framework.modules.zhfw.entity.WtZhfwptThem;
import com.seezoon.framework.modules.zhfw.service.WtZhfwptThemService;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 综合服务平台投票主题管理
 */
@RestController
@RequestMapping("${admin.path}/zhfw/wtzhfwptthem")
public class WtZhfwptThemController extends BaseController {

    @Autowired
    private WtZhfwptThemService wtzhfwptthemService;
    @Autowired
    private WtZhfwptVotepotionService wtZhfwptVotepotionService;
    @Autowired
    private OracleToolService oracleToolService;

    //	@RequiresPermissions("zhfw:wtzhfwptthem:qry")
    @PostMapping("/qryPage.do")
    public ResponeModel qryPage(WtZhfwptThem wtzhfwptthem) {
        wtzhfwptthem.setSortField("themeid");
        PageInfo<WtZhfwptThem> page = wtzhfwptthemService.findByPage(wtzhfwptthem, wtzhfwptthem.getPage(), wtzhfwptthem.getPageSize());
        return ResponeModel.ok(page);
    }

    //	@RequiresPermissions("zhfw:wtzhfwptthem:qry")
    @RequestMapping("/get.do")
    public ResponeModel get(@RequestParam Serializable id) {
        WtZhfwptThem wtzhfwptthem = wtzhfwptthemService.findById(id);
        WtZhfwptVotepotion wtZhfwptVotepotion = new WtZhfwptVotepotion();
        wtZhfwptVotepotion.setThemeid(wtzhfwptthem.getThemeid());
        wtZhfwptVotepotion.setSortField("voteoptionid");
        List<WtZhfwptVotepotion> list = wtZhfwptVotepotionService.findList(wtZhfwptVotepotion);
        Map<String,Object> map = new HashMap<>();
        map.put("data",wtzhfwptthem);
        map.put("options",list);
        return ResponeModel.ok(map);
    }

    //	@RequiresPermissions("zhfw:wtzhfwptthem:save")
    @PostMapping("/save.do")
    public ResponeModel save(@Validated WtZhfwptThem wtzhfwptthem, BindingResult bindingResult, HttpServletRequest request) {
        wtzhfwptthem.setThemeid(oracleToolService.getCommonKey());
        Timestamp timestamp = new Timestamp(new Date().getTime());
        wtzhfwptthem.setLrrq(timestamp);
        wtzhfwptthem.setLrrzh(wtzhfwptthemService.getOperatorUserId());
        int cnt = wtzhfwptthemService.save(wtzhfwptthem);
        if (cnt > 0) {
            Map<String, String[]> map = request.getParameterMap();
            for (Map.Entry<String, String[]> entry : map.entrySet()) {
                String key = entry.getKey();
                if (key.contains("options") && StringUtils.isNotEmpty(entry.getValue()[0])) {
                    WtZhfwptVotepotion wtZhfwptVotepotion = new WtZhfwptVotepotion();
                    wtZhfwptVotepotion.setThemeid(wtzhfwptthem.getThemeid());
                    wtZhfwptVotepotion.setLrrq(timestamp);
                    wtZhfwptVotepotion.setLrrzh(wtzhfwptthemService.getOperatorUserId());
                    wtZhfwptVotepotion.setVoteoptionid(oracleToolService.getCommonKey());
                    wtZhfwptVotepotion.setXpjg(new BigDecimal(0));
                    wtZhfwptVotepotion.setDqzt("01");
                    if (entry.getValue().length > 0) {
                        wtZhfwptVotepotion.setXpnr(entry.getValue()[0]);
                    }
                    wtZhfwptVotepotionService.save(wtZhfwptVotepotion);
                }
            }
        }
        return ResponeModel.ok(cnt);
    }

    //	@RequiresPermissions("zhfw:wtzhfwptthem:update")
    @PostMapping("/update.do")
    public ResponeModel update(@Validated WtZhfwptThem wtzhfwptthem, BindingResult bindingResult, HttpServletRequest request) {
        int cnt = wtzhfwptthemService.updateSelective(wtzhfwptthem);
        if(cnt>0){
            Timestamp timestamp = new Timestamp(new Date().getTime());
            Map<String, String[]> map = request.getParameterMap();
            Map<String,String> kvMap = new HashMap<>();
            for (Map.Entry<String, String[]> entry : map.entrySet()) {
                String key = entry.getKey();
                kvMap.put(key,entry.getValue()[0]);
            }
            for (Map.Entry<String,String> entry : kvMap.entrySet()){
                String key = entry.getKey();
                if (key.startsWith("options")){
                    String xpnr = entry.getValue();
                    String voteoptionid = null;
                    String id = key.replace("options","");
                    voteoptionid = kvMap.get("voteoptionid"+id);

                    if (StringUtils.isEmpty(xpnr) && StringUtils.isEmpty(voteoptionid)){
                        continue;
                    }
                    if (StringUtils.isEmpty(xpnr) && StringUtils.isNotEmpty(voteoptionid)){
                        wtZhfwptVotepotionService.deleteById(voteoptionid);
                    }
                    WtZhfwptVotepotion wtZhfwptVotepotion = new WtZhfwptVotepotion();
                    wtZhfwptVotepotion.setVoteoptionid(voteoptionid);
                    wtZhfwptVotepotion.setThemeid(wtzhfwptthem.getThemeid());
                    wtZhfwptVotepotion.setXpnr(xpnr);
                    if (StringUtils.isEmpty(voteoptionid)){
                        wtZhfwptVotepotion.setVoteoptionid(oracleToolService.getCommonKey());
                        wtZhfwptVotepotion.setLrrq(timestamp);
                        wtZhfwptVotepotion.setLrrzh(wtzhfwptthemService.getOperatorUserId());
                        wtZhfwptVotepotion.setDqzt("01");
                        wtZhfwptVotepotion.setXpjg(new BigDecimal(0));
                        wtZhfwptVotepotionService.save(wtZhfwptVotepotion);
                    }else {
                        wtZhfwptVotepotionService.updateSelective(wtZhfwptVotepotion);
                    }
                }
            }
        }
        return ResponeModel.ok(cnt);
    }

    //	@RequiresPermissions("zhfw:wtzhfwptthem:delete")
    @PostMapping("/delete.do")
    public ResponeModel delete(@RequestParam Serializable id) {
        int cnt = wtzhfwptthemService.deleteById(id);
        if(cnt>0){
            cnt = wtZhfwptVotepotionService.deleteByThemeid(id);
        }
        return ResponeModel.ok(cnt);
    }
}