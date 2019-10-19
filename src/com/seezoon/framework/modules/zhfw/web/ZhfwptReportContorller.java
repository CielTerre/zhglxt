package com.seezoon.framework.modules.zhfw.web;

import com.seezoon.framework.common.context.beans.ResponeModel;
import com.seezoon.framework.common.utils.CodecUtils;
import com.seezoon.framework.common.web.BaseController;
import com.seezoon.framework.modules.zhfw.service.ZhfwptReportService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("${admin.path}/zhfw/zhfwptReport")
public class ZhfwptReportContorller extends BaseController {
    @Autowired
    private ZhfwptReportService zhfwptReportService;

//    @RequiresPermissions("zhfw:zhfwptbb:qry")
    @PostMapping("/qryPage.do")
    public ResponeModel qryPage(@RequestParam(required=false) String qdid, String inputDatec, @RequestParam(required=false) String inputDated) {
        JSONArray jsonArray = zhfwptReportService.findAll(qdid,inputDatec,inputDated);
        JSONObject resultObject = new JSONObject();
        resultObject.put("list",jsonArray);
        resultObject.put("total",jsonArray.size());
        return ResponeModel.ok(resultObject);
    }

//    @RequiresPermissions("zhfw:zhfwptbb:report")
    @RequestMapping("/report.do")
    public void report(@RequestParam(required=false) String qdid,String inputDatec,@RequestParam(required=false) String inputDated,HttpServletResponse response) throws IOException {
        byte[] code = zhfwptReportService.report(qdid,inputDatec,inputDated);
        response.setContentType("application/msexcel");
        response.setHeader("Content-Disposition", "attachment;filename="+ CodecUtils.urlEncode("综合管理平台报表.xlsx"));
        response.setContentLength(code.length);
        ServletOutputStream output = response.getOutputStream();
        IOUtils.write(code, output);
        IOUtils.closeQuietly(output);
    }
}
