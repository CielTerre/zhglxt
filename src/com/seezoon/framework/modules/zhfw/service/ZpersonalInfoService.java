package com.seezoon.framework.modules.zhfw.service;

import com.github.pagehelper.PageHelper;
import com.seezoon.framework.modules.zhfw.dao.OracleToolDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ZpersonalInfoService {
    @Autowired
    private OracleToolDao oracleToolDao;

    /**
     * 搜索个人基本信息列表
     *
     * @param xingming 名称
     * @param zjhm     证件号码
     * @param pageNum  页号
     * @param pageSize 页值
     * @return
     */
    public List<Map<String, Object>> queryInfoData(String xingming, String zjhm, int pageNum, int pageSize) {
        String sql = "select a.GRZH, a.XINGMING, a.ZJHM, a.SJHM, a.DWZH, b.DWMC, (select mc from VW_GRZHZTBMB where BM=a.GRZHZT) GRZHZT,c.GRCKZHHM,c.GRJZNY,c.HJYJCE,c.GRZHYE " +
                " FROM ZPERSONALINFO a LEFT JOIN ZUNITINFO b on a.DWZH = b.DWZH left JOIN ZGRZHXX c on a.GRZH = c.GRZH where 1=1 ";
        if (StringUtils.isNotEmpty(xingming)) {
            sql += " and a.xingming like '%" + xingming + "%' ";
        }
        if (StringUtils.isNotEmpty(zjhm)) {
            sql += " and a.zjhm like '%" + zjhm + "%' ";
        }
        PageHelper.startPage(pageNum, pageSize, Boolean.TRUE);
        List<Map<String, Object>> list = oracleToolDao.selectInfo(sql);
        return list;
    }

    /**
     * 查询个人相关信息
     *
     * @param grzh 公积金账号
     * @return
     */
    public Map<String, Object> queryPersonalInfomation(String grzh) {
        String sql = "SELECT a.GRZH,a.XINGMING,a.XINGBIE,a.DWZH,a.ZJHM,a.CSNY,a.GDDHHM,a.SJHM,a.JTZZ, " +
                "b.DWMC,c.GRCKZHHM,to_char(c.KHRQ,'yyyy.mm.dd') KHRQ,c.CJNY,c.GRJZNY,c.GRJCJS,c.DWJCBL,c.GRJCBL,c.HJYJCE,c.GRZHYE," +
                "(CASE WHEN c.GRZHZT<>'06' THEN '否' ELSE '是' END ) sfdj " +
                "from ZPERSONALINFO a LEFT JOIN ZUNITINFO b on a.DWZH = b.DWZH " +
                "  left JOIN ZGRZHXX c on a.GRZH = c.GRZH where a.grzh='" + grzh + "'";
        List<Map<String, Object>> list = oracleToolDao.selectInfo(sql);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 查询贷款情况
     *
     * @param grzh     公积金账号
     * @param page     页号
     * @param pageSize 页值
     * @return
     */
    public List<Map<String, Object>> queryDkData(String grzh, int page, int pageSize) {
        String sqlDK = "select a.jkhtbh,a.dkzh,a.htdkje,（a.sqqs/12）||'年' sqqs,b.whbj,to_char(b.zzhkr,'YYYYMM') hkny,to_char(a.jkhtqdrq,'YYYY.MM.dd') jkhtqdrq from zloanbaseinfo a,ddkzhxx b where a.jkrgjjzh='" + grzh + "' and a.dqzt='E' and a.dkzh=b.dkzh ";
        PageHelper.startPage(page, pageSize, Boolean.TRUE);
        List<Map<String, Object>> list = oracleToolDao.selectInfo(sqlDK);
        return list;
    }

    /**
     * 查询担保情况
     *
     * @param grzh     公积金账号
     * @param page     页号
     * @param pageSize 页值
     * @return
     */
    public List<Map<String, Object>> queryDbData(String grzh, int page, int pageSize) {
        String sqlDY = "select b.jkhtbh,b.jkrxm,b.jkrgjjzh,b.htdkje,(select xsz from char_list where type='CDGX' and id = a.YDKRGX) YDKRGX,a.DQZT,a.DBFS,a.ZYWJZ from djkrzyqk a,zloanbaseinfo b where a.dbrgrzh='" + grzh + "' and a.dqzt='Y' and a.dkzh=b.dkzh ";
        PageHelper.startPage(page, pageSize, Boolean.TRUE);
        List<Map<String, Object>> list = oracleToolDao.selectInfo(sqlDY);
        return list;
    }

    /**
     * 查询业务流水
     *
     * @param grzh     公积金账号
     * @param page     页号
     * @param pageSize 页值
     * @return
     */
    public List<Map<String, Object>> queryYwlsData(String grzh, int page, int pageSize) {
        String sql = "select KJNY,to_char(JZRQ,'yyyy.mm.dd') JZRQ,YSYWLB,(CASE WHEN JDFX = -1 THEN FSE ELSE 0 END ) jsje,(CASE WHEN JDFX = 1 THEN FSE ELSE 0 END ) zjje, FSE,ZY,   " +
                "  (select XSZ from char_list where type='GRTQXHYYBMB' and ID=TQYY) tqyy   " +
                "from ZYWLSMX where GRZH='"+grzh+"' order by jzrq desc";
        PageHelper.startPage(page, pageSize, Boolean.TRUE);
        List<Map<String, Object>> list = oracleToolDao.selectInfo(sql);
        return list;
    }
}
