package com.seezoon.framework.modules.zhfw.web;

import com.github.pagehelper.PageInfo;
import com.seezoon.framework.common.context.beans.ResponeModel;
import com.seezoon.framework.common.web.BaseController;
import com.seezoon.framework.modules.zhfw.service.ZpersonalInfoService;
import com.seezoon.framework.modules.zhfw.util.SystemUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 用户基本信息
 */
@RestController
@RequestMapping("${admin.path}/zhfw/zpersonalinfo")
public class ZpersonalInfoContorller extends BaseController {
    @Autowired
    private ZpersonalInfoService zpersonalInfoService;

//    @RequiresPermissions("zhfw:zpersonalinfo:qry")
    @PostMapping("/qryPage.do")
    public ResponeModel qryPage( String xingming, String zjhm,int page,int pageSize) {
        List<Map<String, Object>> list = zpersonalInfoService.queryInfoData(xingming,zjhm,page,pageSize);
        for (Map<String,Object> map : list){
            if (map.get("GRZH")!=null){
                map.put("id",map.get("GRZH"));
                map.put("GRZH", SystemUtil.sgtm(String.valueOf(map.get("GRZH")),0,4));
            }
            if (map.get("ZJHM")!=null){
                map.put("ZJHM", SystemUtil.sgtm(String.valueOf(map.get("ZJHM")),0,4));
            }
            if (map.get("SJHM")!=null){
                map.put("SJHM", SystemUtil.sgtm(String.valueOf(map.get("SJHM")),3,4));
            }
            if (map.get("GRCKZHHM")!=null){
                map.put("GRCKZHHM", SystemUtil.sgtm(String.valueOf(map.get("GRCKZHHM")),0,4));
            }
        }
        PageInfo pageInfo = new PageInfo(list);
        return ResponeModel.ok(pageInfo);
    }
    @PostMapping("/qryDkPage.do")
    public ResponeModel qryDkPage(String grzh,int page,int pageSize) {
        if (StringUtils.isEmpty(grzh)){
            return ResponeModel.ok();
        }
        List<Map<String,Object>> list = zpersonalInfoService.queryDkData(grzh,page,pageSize);
        PageInfo pageInfo = new PageInfo(list);
        return ResponeModel.ok(pageInfo);
    }
    @PostMapping("/qryDbPage.do")
    public ResponeModel qryDbPage(String grzh,int page,int pageSize) {
        if (StringUtils.isEmpty(grzh)){
            return ResponeModel.ok();
        }
        List<Map<String,Object>> list = zpersonalInfoService.queryDbData(grzh,page,pageSize);
        PageInfo pageInfo = new PageInfo(list);
        return ResponeModel.ok(pageInfo);
    }
    @PostMapping("/qryYwlsPage.do")
    public ResponeModel qryYwlsPage(String grzh,int page,int pageSize) {
        if (StringUtils.isEmpty(grzh)){
            return ResponeModel.ok();
        }
        List<Map<String,Object>> list = zpersonalInfoService.queryYwlsData(grzh,page,pageSize);
        PageInfo pageInfo = new PageInfo(list);
        return ResponeModel.ok(pageInfo);
    }

    @RequestMapping("/get.do")
    public ResponeModel get(@RequestParam String grzh) {
        Map<String,Object> map = zpersonalInfoService.queryPersonalInfomation(grzh);
        if (map.get("GRZH")!=null){
            map.put("GRZH",SystemUtil.sgtm(String.valueOf(map.get("GRZH")),0,4));
        }
        if (map.get("ZJHM")!=null){
            map.put("ZJHM",SystemUtil.sgtm(String.valueOf(map.get("ZJHM")),0,4));
        }
        if (map.get("GDDHHM")!=null){
            map.put("GDDHHM",SystemUtil.sgtm(String.valueOf(map.get("GDDHHM")),0,4));
        }
        if (map.get("SJHM")!=null){
            map.put("SJHM",SystemUtil.sgtm(String.valueOf(map.get("SJHM")),3,4));
        }
        if (map.get("GRCKZHHM")!=null){
            map.put("GRCKZHHM",SystemUtil.sgtm(String.valueOf(map.get("GRCKZHHM")),0,4));
        }
        return ResponeModel.ok(map);
    }
}
