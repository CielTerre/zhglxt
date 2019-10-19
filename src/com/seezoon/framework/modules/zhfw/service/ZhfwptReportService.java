package com.seezoon.framework.modules.zhfw.service;

import com.seezoon.framework.modules.zhfw.dao.OracleToolDao;
import com.seezoon.framework.modules.zhfw.dao.WtQdxxbDao;
import com.seezoon.framework.modules.zhfw.entity.WtQdxxb;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 综合服务平台报表Service
 */
@Service
public class ZhfwptReportService {
    @Autowired
    private OracleToolDao oracleToolDao;
    @Autowired
    private WtQdxxbDao wtQdxxbDao;

    public JSONArray findAll(String qdid, String inputDateC, String inputDateD) {
        List<Map<String, Object>> list = getReportData(qdid, inputDateC, inputDateD);

        JSONArray jsonArray = new JSONArray();
        if (list.size() == 1) {
            Map<String, Object> map = list.get(0);
            String[] strings = new String[]{
                    "信息查询#公开信息查询#GKXXCX",
                    "信息查询#个人信息查询#GRXXCX",
                    "信息查询#单位信息查询#DWXXCX",
                    "信息查询#凭证、单据打印#DY",
                    "信息查询#业务办理进度#YWBLJD",
                    "信息查询#发布文件下载#XZ",
                    "信息查询#其他#XXCXQT",

                    "信息发布#公开信息发布#GKXXFB",
                    "信息发布#业务消息推送#XXTS",
                    "信息发布#其他#XXFBQT",

                    "互动交流#政策业务咨询#ZXZS",
                    "互动交流#线上调查#WSDCZS",
                    "互动交流#投诉、建议#TSJYZS",
                    "互动交流#其他#HDJLQT",

                    "业务办理#缴存预约#JCYY",
                    "业务办理#缴存申请#JCSQ",
                    "业务办理#缴存办结#JCBJ",

                    "业务办理#提取预约#TQYY",
                    "业务办理#提取申请#TQSQ",
                    "业务办理#提取办结#TQBJ",

                    "业务办理#贷款预约#DKYY",
                    "业务办理#贷款申请#DKSQ",
                    "业务办理#贷款办结#DKBJ",
            };

            for (String str : strings) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("gnfl", str.split("#")[0]);
                jsonObject.put("flmx", str.split("#")[1]);
                jsonObject.put("fwl", map.get(str.split("#")[2]));
                jsonArray.add(jsonObject);
            }
        }
        return jsonArray;
    }

    public byte[] report(String qdid, String inputDatec, String inputDated) throws IOException {
        List<Map<String, Object>> list = getReportData(qdid, inputDatec, inputDated);

        JSONArray jsonArray = new JSONArray();
        if (list.size() == 1) {
            Map<String, Object> map = list.get(0);
            String[] strings = new String[]{
                    "信息查询#公开信息查询#GKXXCX",
                    "信息查询#个人信息查询#GRXXCX",
                    "信息查询#单位信息查询#DWXXCX",
                    "信息查询#凭证、单据打印#DY",
                    "信息查询#业务办理进度#YWBLJD",
                    "信息查询#发布文件下载#XZ",
                    "信息查询#其他#XXCXQT",
                    "信息查询#小计#XXCXXJ",

                    "信息发布#公开信息发布#GKXXFB",
                    "信息发布#业务消息推送#XXTS",
                    "信息发布#其他#XXFBQT",
                    "信息发布#小计#XXFBXJ",

                    "互动交流#政策业务咨询#ZXZS",
                    "互动交流#线上调查#WSDCZS",
                    "互动交流#投诉、建议#TSJYZS",
                    "互动交流#其他#HDJLQT",
                    "互动交流#小计#HDJLXJ",

                    "业务办理#缴存预约#JCYY",
                    "业务办理#缴存申请#JCSQ",
                    "业务办理#缴存办结#JCBJ",
                    "业务办理#缴存业务（小计）#JCXJ",

                    "业务办理#提取预约#TQYY",
                    "业务办理#提取申请#TQSQ",
                    "业务办理#提取办结#TQBJ",
                    "业务办理#提取业务（小计）#TQXJ",

                    "业务办理#贷款预约#DKYY",
                    "业务办理#贷款申请#DKSQ",
                    "业务办理#贷款办结#DKBJ",
                    "业务办理#贷款业务（小计）#DKXJ",
                    "业务办理#其他#YWBLQT",
                    "业务办理#小计#YWBLXJ",
            };

            for (String str : strings) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("gnfl", str.split("#")[0]);
                jsonObject.put("flmx", str.split("#")[1]);
                jsonObject.put("fwl", map.get(str.split("#")[2]));
                jsonArray.add(jsonObject);
            }
        }

        String qdName = "";
        if (StringUtils.isNotEmpty(qdid)) {
            WtQdxxb wtQdxxb = wtQdxxbDao.selectByPrimaryKey(qdid);
            qdName = wtQdxxb.getQdmc();
        }
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        sheet.setColumnWidth(0, 4000);
        sheet.setColumnWidth(1, 4000);
        sheet.setColumnWidth(2, 4000);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 2)); //合并

        Row row = null;
        Cell cell = null;

        CellStyle titleStyle = workbook.createCellStyle();
        titleStyle.setAlignment(HorizontalAlignment.CENTER); //水平居中
        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
        Font titleFont = workbook.createFont();
        titleFont.setFontHeightInPoints((short) 22); //字体
        titleFont.setFontName("宋体");
        titleStyle.setFont(titleFont);

        row = sheet.createRow(0);
        row.setHeightInPoints(25);

        cell = row.createCell(0);
        cell.setCellValue(qdName + "服务量表");
        cell.setCellStyle(titleStyle);
        for (int i = 0; i < 2; i++) {
            cell = row.createCell(i + 1);
            cell.setCellStyle(titleStyle);
        }

        Font subFont = workbook.createFont();
        subFont.setFontHeightInPoints((short) 8);
        subFont.setFontName("宋体");
        CellStyle subStyle = workbook.createCellStyle();
        subStyle.setFont(subFont);
        row = sheet.createRow(1);
        cell = row.createCell(0);
        cell.setCellValue("编制单位：随州公积金");
        cell.setCellStyle(subStyle);
        cell = row.createCell(1);
        cell.setCellValue(inputDatec + "至" + inputDated);
        cell.setCellStyle(subStyle);

        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontName("宋体");

        CellStyle contentStyle = workbook.createCellStyle();
        contentStyle.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());//背景色
        contentStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);//填充方式
        contentStyle.setBorderRight(BorderStyle.THIN); //右边框
        contentStyle.setBorderBottom(BorderStyle.THIN);//下边框
        contentStyle.setAlignment(HorizontalAlignment.CENTER); //水平居中
        contentStyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
        contentStyle.setFont(font);

        CellStyle greenStyle = workbook.createCellStyle();
        greenStyle.setFillForegroundColor(IndexedColors.BRIGHT_GREEN.getIndex());//背景色
        greenStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);//填充方式
        greenStyle.setBorderRight(BorderStyle.THIN); //右边框
        greenStyle.setBorderBottom(BorderStyle.THIN);//下边框
        greenStyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中

        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());//背景色
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);//填充方式
        style.setBorderRight(BorderStyle.THIN); //右边框
        style.setBorderBottom(BorderStyle.THIN);//下边框
        style.setAlignment(HorizontalAlignment.LEFT); //水平居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中

        CellStyle numberStyle = workbook.createCellStyle();
        numberStyle.setBorderRight(BorderStyle.THIN); //右边框
        numberStyle.setBorderBottom(BorderStyle.THIN);//下边框
        numberStyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中

        row = sheet.createRow(2);
        row.setHeightInPoints((short) 15);
        cell = row.createCell(0);
        cell.setCellStyle(contentStyle);
        cell.setCellValue("功能分类");
        cell = row.createCell(1);
        cell.setCellStyle(contentStyle);
        cell = row.createCell(2);
        cell.setCellStyle(contentStyle);
        cell.setCellValue("服务量（笔）");

        int rowOffset = 3; //行偏移量
        String gnfl = "";
        int changeRowNum = 3;
        int num = 1;
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            if (StringUtils.isEmpty(gnfl)) {
                gnfl = jsonObject.getString("gnfl");
            } else {
                if (!gnfl.equals(jsonObject.getString("gnfl"))) {
                    gnfl = jsonObject.getString("gnfl");
                    sheet.addMergedRegion(new CellRangeAddress(changeRowNum, i + rowOffset - 1, 0, 0));
                    changeRowNum = i + rowOffset;
                }
            }
            row = sheet.createRow(i + rowOffset);
            row.setHeightInPoints((short) 15);
            cell = row.createCell(0);
            cell.setCellValue(jsonObject.getString("gnfl"));
            cell.setCellStyle(contentStyle);
            cell = row.createCell(1);
            if (jsonObject.getString("flmx").contains("小计")) {
                cell.setCellValue(jsonObject.getString("flmx"));
                num = 1;
            } else {
                cell.setCellValue(num+"、"+jsonObject.getString("flmx"));
                num ++ ;
            }
            cell.setCellStyle(style);
            cell = row.createCell(2);
            if (jsonObject.getString("flmx").contains("小计")) {
                cell.setCellStyle(greenStyle);
            } else {
                cell.setCellStyle(numberStyle);
            }
            if (jsonObject.get("fwl") != null) {
                cell.setCellValue(jsonObject.getInt("fwl"));
            } else {
                cell.setCellValue(0);
            }

            if (i == jsonArray.size() - 1) {
                sheet.addMergedRegion(new CellRangeAddress(changeRowNum, i + rowOffset - 1, 0, 0));
            }
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();
        return out.toByteArray();
    }

    private List<Map<String, Object>> getReportData(String qdid, String inputDateC, String inputDateD) {
        if (StringUtils.isEmpty(inputDateD)) { //如果没传结束日期, 默认为截止日期
            inputDateD = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        }
        String sqlStrA = "select\n" +
                "'1' id," +
                "  nvl(sum(CASE WHEN FWID in (select FWID from WT_FWDJXX where FWID like '000006%' and FWFL='005') THEN 1 ELSE 0 END ),0) gkxxcx, \n" +//--000006公开信息 005查询
                "  nvl(sum(CASE WHEN FWID in (select FWID from WT_FWDJXX where FWID like '000002%' and FWFL='005') THEN 1 ELSE 0 END ),0) grxxcx,\n" +// --000002个人信息 005查询
                "  nvl(sum(CASE WHEN FWID in (select FWID from WT_FWDJXX where FWID like '000003%' and FWFL='005') THEN 1 ELSE 0 END ),0) dwxxcx, \n" +//--000003单位信息 005查询
                "  nvl(sum(CASE WHEN FWID in (select FWID from WT_FWDJXX where FWSM LIKE '%打印%') THEN 1 ELSE 0 END ),0) dy,\n" + //打印
                "  nvl(sum(CASE WHEN FWID in (select FWID from WT_FWDJXX where FWFL in ('001','002','003')) THEN 1 ELSE 0 END ),0) ywbljd, \n" + //业务办理进度
                "  nvl(sum(CASE WHEN (BLOB_TO_VARCHAR(QQBTNR) LIKE '%\"wzid\":\"%'\n" +
                "                 and substr(BLOB_TO_VARCHAR(QQBTNR),instr(BLOB_TO_VARCHAR(QQBTNR),'\"wzid\":\"')+8,10) in(select wzid from WT_ZHFWPT_QT_ARTICLE WHERE CLASSID='25'))\n" +
                "    THEN 1 ELSE 0 END),0) xz, \n" + //发布文件下载
                "  nvl(sum(CASE WHEN FWID in (select FWID from WT_FWDJXX where FWFL in ('005')) THEN 1 ELSE 0 END ),0) cxzs, \n" + //查询总数
                "  nvl(sum(CASE WHEN FWID in (select FWID from WT_FWDJXX where FWID like '000006%' and FWFL='004') THEN 1 ELSE 0 END ),0) gkxxfb, \n" + //000006公开信息 004信息发布
                "  nvl(sum(CASE WHEN FWID in (select FWID from WT_FWDJXX where FWID='0000010007') THEN 1 ELSE 0 END ),0) xxts,\n" + // -- 0000010007 业务消息推送
                "  nvl(sum(CASE WHEN FWID in (select FWID from WT_FWDJXX where FWFL='004') THEN 1 ELSE 0 END ),0) xxfbzs,\n" + // -- 004信息发布 总数
                "  nvl(sum(CASE WHEN (BLOB_TO_VARCHAR(QQBTNR) LIKE '%\"wzid\":\"%'\n" +
                "                     and substr(BLOB_TO_VARCHAR(QQBTNR),instr(BLOB_TO_VARCHAR(QQBTNR),'\"wzid\":\"')+8,10) in(select wzid from WT_ZHFWPT_QT_ARTICLE WHERE CLASSID='8'))\n" +
                "    THEN 1 ELSE 0 END),0) zxzs,\n" + // --咨询
                "  nvl(sum(CASE WHEN FWID in (select FWID from WT_FWDJXX where FWID='0000060067') THEN 1 ELSE 0 END ),0) wsdczs,\n" + // -- 0000060067 网上调查投票
                "  nvl(sum(CASE WHEN (BLOB_TO_VARCHAR(QQBTNR) LIKE '%\"wzid\":\"%'\n" +
                "                     and substr(BLOB_TO_VARCHAR(QQBTNR),instr(BLOB_TO_VARCHAR(QQBTNR),'\"wzid\":\"')+8,10) in(select wzid from WT_ZHFWPT_QT_ARTICLE WHERE CLASSID in('32','33')))\n" +
                "    THEN 1 ELSE 0 END),0) tsjyzs,\n" + // --投诉建议
                "  nvl(sum(CASE WHEN FWID in (select FWID from WT_FWDJXX where FWFL='006') THEN 1 ELSE 0 END ),0) hdjlzs \n" + // -- 006互动交流 总数
                "\n" +
                "from WT_YHFWRZB where 1=1 ";
        String sqlStrB = "select '1' id,count(YWLSH) dx from DX_FSHJB WHERE 1=1 "; //短信数量
        String sqlStrC = "SELECT '1' id,count(HDYWPCH) jcsqc from (select * from ZDWJCXX UNION select * from ZDWJCXXLS ) where DWZH <> '1003901334' "; //单位缴存申请数量
        String sqlStrC2 = "SELECT '1' id,count(HDYWPCH) jcbjc from (select * from ZDWJCXX UNION select * from ZDWJCXXLS ) where DWZH <> '1003901334' AND HDBZ='5' "; //单位缴存办结数量
        String sqlStrD = "select '1' id,count(YWLSH) jcsqd from ZJJPLSKMXB WHERE DQZT NOT IN ('N','D') ";//个人缴存申请数量
        String sqlStrD2 = "select '1' id,count(YWLSH) jcbjd from ZJJPLSKMXB WHERE DQZT ='E' ";//个人缴存办结数量
        String sqlStrE = "select '1' id,count(YWLSH) tqsq from ZTQSQHZXX where YWBLZT <> 'D' "; //提取申请数量
        String sqlStrE2 = "select '1' id,count(YWLSH) tqbj from ZTQSQHZXX where YWBLZT = 'E' "; //提取办结数量
        String sqlStrF = "select '1' id ,count(DKZH) dksq from DJKRSQXX WHERE DQZT <> 'D' ";//贷款申请数量
        String sqlStrF2 = "select '1' id ,count(DKZH) dkbj from DJKRSQXX WHERE DQZT = 'E' ";//贷款办结数量
        if (StringUtils.isNotEmpty(qdid)) {
            sqlStrA += " and qdid = '" + qdid + "' ";
            sqlStrB += " and FSQDID = '"+qdid+"' ";
            sqlStrC += " and HDQDID = '"+qdid+"' ";
            sqlStrC2 += " and HDQDID = '"+qdid+"' ";
            sqlStrD += " and LRQDID = '"+qdid+"' ";
            sqlStrD2 += " and LRQDID = '"+qdid+"' ";
            sqlStrE += " and LRQDID = '"+qdid+"' ";
            sqlStrE2 += " and LRQDID = '"+qdid+"' ";
            sqlStrF += " and SQQDID = '"+qdid+"' ";
            sqlStrF2 += " and SQQDID = '"+qdid+"' ";
        }
        if (StringUtils.isNotEmpty(inputDateC)) {
            sqlStrA += " and rq >= to_date('" + inputDateC + "','yyyy-mm-dd') ";
            sqlStrB += " and FSRQ >= to_date('" + inputDateC + "','yyyy-mm-dd') ";
            sqlStrC += " and HDRQ >= to_date('" + inputDateC + "','yyyy-mm-dd') ";
            sqlStrC2 += " and HDRQ >= to_date('" + inputDateC + "','yyyy-mm-dd') ";
            sqlStrD += " and LRRQ >= to_date('" + inputDateC + "','yyyy-mm-dd') ";
            sqlStrD2 += " and LRRQ >= to_date('" + inputDateC + "','yyyy-mm-dd') ";
            sqlStrE += " and LRRQ >= to_date('" + inputDateC + "','yyyy-mm-dd') ";
            sqlStrE2 += " and LRRQ >= to_date('" + inputDateC + "','yyyy-mm-dd') ";
            sqlStrF += " and SQRQ >= to_date('" + inputDateC + "','yyyy-mm-dd') ";
            sqlStrF2 += " and SQRQ >= to_date('" + inputDateC + "','yyyy-mm-dd') ";
        }
        if (StringUtils.isNotEmpty(inputDateD)) {
            sqlStrA += " and rq <= to_date('" + inputDateD + " 23:59:59','yyyy-mm-dd hh24:mi:ss') ";
            sqlStrB += " and FSRQ <= to_date('" + inputDateD + " 23:59:59','yyyy-mm-dd hh24:mi:ss') ";
            sqlStrC += " and HDRQ <= to_date('" + inputDateD + " 23:59:59','yyyy-mm-dd hh24:mi:ss') ";
            sqlStrC2 += " and HDRQ <= to_date('" + inputDateD + " 23:59:59','yyyy-mm-dd hh24:mi:ss') ";
            sqlStrD += " and LRRQ <= to_date('" + inputDateD + " 23:59:59','yyyy-mm-dd hh24:mi:ss') ";
            sqlStrD2 += " and LRRQ <= to_date('" + inputDateD + " 23:59:59','yyyy-mm-dd hh24:mi:ss') ";
            sqlStrE += " and LRRQ <= to_date('" + inputDateD + " 23:59:59','yyyy-mm-dd hh24:mi:ss') ";
            sqlStrE2 += " and LRRQ <= to_date('" + inputDateD + " 23:59:59','yyyy-mm-dd hh24:mi:ss') ";
            sqlStrF += " and SQRQ <= to_date('" + inputDateD + " 23:59:59','yyyy-mm-dd hh24:mi:ss') ";
            sqlStrF2 += " and SQRQ <= to_date('" + inputDateD + " 23:59:59','yyyy-mm-dd hh24:mi:ss') ";
        }
        String sqlStr = "select " +
                "a.gkxxcx," + //--公开信息
                "  a.grxxcx, " +//--个人信息
                "  a.dwxxcx, " +//--单位信息
                "  a.dy, " +//--打印
                "  a.ywbljd, " +//--业务办理进度
                "  a.xz, " +//--下载
                "  a.cxzs - a.gkxxcx - a.grxxcx - a.dwxxcx  xxcxqt," +// --其他
                "  a.cxzs + a.dy + a.ywbljd + a.xz xxcxxj, " +//--小计

                "  a.gkxxfb," +//--公开信息发布
                "  b.dx xxts, " +//--消息推送
                "  a.xxfbzs - a.gkxxfb xxfbqt, " +//--信息发布其他
                "  a.xxfbzs + b.dx  xxfbxj," +// --小计

                "  a.zxzs," + //咨询
                "  a.wsdczs," + //线上调查
                "  a.tsjyzs," +//投书建议
                "  a.hdjlzs hdjlqt," + //互动交流其他
                "  a.zxzs + a.wsdczs + a.tsjyzs + a.hdjlzs hdjlxj," + //互动交流小计

                " 0 jcyy," +
                " c.jcsqc + d.jcsqd jcsq," +
                " c2.jcbjc + d2.jcbjd jcbj," +
                " c.jcsqc + d.jcsqd + c2.jcbjc + d2.jcbjd jcxj, "+
                " 0 tqyy," +
                " e.tqsq," +
                " e2.tqbj, " +
                " e.tqsq tqxj," +
                " 0 dkyy," +
                " f.dksq," +
                " f2.dkbj," +
                " f.dksq dkxj," +
                " c.jcsqc + d.jcsqd + c2.jcbjc + d2.jcbjd + e.tqsq + f.dksq ywblxj "+
                "from " +
                "("+sqlStrA+") a " +
                "left join ("+sqlStrB+") b on a.id = b.id "+
                "left join ("+sqlStrC+") c on a.id = c.id "+
                "left join ("+sqlStrC2+") c2 on a.id = c2.id "+
                "left join ("+sqlStrD+") d on a.id = d.id "+
                "left join ("+sqlStrD2+") d2 on a.id = d2.id "+
                "left join ("+sqlStrE+") e on a.id = e.id "+
                "left join ("+sqlStrE2+") e2 on a.id = e2.id "+
                "left join ("+sqlStrF+") f on a.id = f.id "+
                "left join ("+sqlStrF2+") f2 on a.id = f2.id "
                ;
        return oracleToolDao.selectInfo(sqlStr);
    }
}
