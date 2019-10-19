package com.seezoon.framework.modules.zhfw.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
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
import com.seezoon.framework.modules.zhfw.dao.OracleToolDao;
import com.seezoon.framework.modules.zhfw.entity.WtSRtjxx;
import com.seezoon.framework.modules.zhfw.service.WtSRtjxxService;
import com.seezoon.framework.modules.zhfw.util.SystemUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 综合服务平台日统计信息controller
 */
@RestController
@RequestMapping("${admin.path}/zhfw/srtjxx")
public class WtSRtjxxController extends BaseController {

	@Autowired
	private WtSRtjxxService wtSRtjxxService;
	
	@Autowired
    private OracleToolDao oracleToolDao;

	@RequiresPermissions("zhfw:srtjxx:qry")
	@PostMapping("/qryPage.do")
	public ResponeModel qryPage(WtSRtjxx wtSRtjxx) {
		PageInfo<WtSRtjxx> page = wtSRtjxxService.findByPage(wtSRtjxx, wtSRtjxx.getPage(), wtSRtjxx.getPageSize());
		return ResponeModel.ok(page);
	}
	@RequiresPermissions("zhfw:srtjxx:qry")
	@RequestMapping("/get.do")
	public ResponeModel get(@RequestParam Serializable id) {
		WtSRtjxx wtSRtjxx = wtSRtjxxService.findById(id);
		//富文本处理
		return ResponeModel.ok(wtSRtjxx);
	}
	@RequiresPermissions("zhfw:srtjxx:save")
	@PostMapping("/save.do")
	public ResponeModel save(@Validated WtSRtjxx wtSRtjxx, BindingResult bindingResult) {
		int cnt = wtSRtjxxService.save(wtSRtjxx);
		return ResponeModel.ok(cnt);
	}
	@RequiresPermissions("zhfw:srtjxx:update")
	@PostMapping("/update.do")
	public ResponeModel update(@Validated WtSRtjxx wtSRtjxx, BindingResult bindingResult) {
		int cnt = wtSRtjxxService.updateSelective(wtSRtjxx);
		return ResponeModel.ok(cnt);
	}
	@PostMapping("/delete.do")
	public ResponeModel delete(@RequestParam Serializable id) {
		int cnt = wtSRtjxxService.deleteById(id);
		return ResponeModel.ok(cnt);
	}
	
	/** 渠道业务占比接统计 */ 
	@PostMapping("/selectQdywzbtj.do")
	public ResponeModel selectQdywzbtj(@RequestParam(required=false) String ywlb,@RequestParam(required=false) String tjrqc,@RequestParam(required=false) String tjrqd) {
		String sqlStr = "select s.qdid,s.qdtjz,s.sumtjz,decode(s.sumtjz,0,0,round(s.qdtjz/s.sumtjz,2)) qdtjzb from " +
				" (select t.qdid,sum(t.tjz) qdtjz,(select sum(m.tjz) from wt_s_rtjxx m where " +
				" m.tjzb = 'fwfwl' and m.tjzbmx = '"+ywlb+"' and to_char(m.tjrq,'yyyy-mm-dd') >= '"+tjrqc+"' " +
				" and to_char(m.tjrq,'yyyy-mm-dd') <= '"+tjrqd+"' ) sumtjz from wt_s_rtjxx t " +
				" where t.tjzb = 'fwfwl' and t.tjzbmx = '"+ywlb+"' and to_char(t.tjrq,'yyyy-mm-dd') >= '"+tjrqc+"' " +
				" and to_char(t.tjrq,'yyyy-mm-dd') <= '"+tjrqd+"' group by t.qdid) s";
		List<Map<String,Object>> list = oracleToolDao.selectInfo(sqlStr);
//		JSONArray json = JSONArray.fromObject(list);  
//		System.out.println(json.toString());
		return ResponeModel.ok(list);
	}
	
	/** 服务访问量统计 */
	@PostMapping("/selectfwfwltj.do")
	public ResponeModel selectfwfwltj(@RequestParam(required=false) String tjrqc,@RequestParam(required=false) String tjrqd) {
		
		JSONObject JObject = new JSONObject();
		//统计办理时间段
		String sqlStr = "select t.qdid,sum(t.tjz) qdtjz from wt_s_rtjxx t where t.qdid is not null and t.tjzb = 'fwfwl' "+
				" and to_char(t.tjrq, 'yyyy-mm-dd') >= '"+tjrqc+"' and to_char(t.tjrq, 'yyyy-mm-dd') <= '"+tjrqd+"' "+ 
				" group by t.qdid order by t.qdid ";
		List<Map<String,Object>> listYwblsjd = oracleToolDao.selectInfo(sqlStr);
		JObject.put("ywblsjdResult", JSONArray.fromObject(listYwblsjd));
		return ResponeModel.ok(JObject);
	}
	
	/** 渠道访问交易情况统计 */
	@PostMapping("/selectQdjyqktj.do")
	public ResponeModel selectQdjyqktj(@RequestParam(required=false) String tjrqc,@RequestParam(required=false) String tjrqd,@RequestParam(required=false) String qdid) {
		
		String paramQdid = "";
		if(!SystemUtil.isEmpty(qdid)){
			paramQdid = " and t.qdid = '"+qdid+"' ";
		}
		JSONObject JObject = new JSONObject();
		//统计交易情况
		String sqlStr = "select s.ycjyyws,s.ywqqs,s.ywqqcgs,decode(s.ywqqs,0,0,s.ywqqcgs/s.ywqqs) ywqqcgbl,decode(s.ywqqs,0,0,s.ycjyyws/s.ywqqs) ywblycbl from " +
				" (select sum(decode(t.tjzb,'ywqqcgs',t.tjz,0)) ywqqcgs,sum(decode(t.tjzb,'ycjyyws',t.tjz,0)) ycjyyws," +
				" sum(decode(t.tjzb,'ywqqs',t.tjz,0)) ywqqs from wt_s_rtjxx t where t.tjzb in ('ywblcgs','ywqqcgs','ywqqs','ycjyyws') " +
				" and to_char(t.tjrq,'yyyy-mm-dd') >= '"+tjrqc+"' and to_char(t.tjrq,'yyyy-mm-dd') <= '"+tjrqd+"' "+paramQdid+" ) s";
		List<Map<String,Object>> listJyqk = oracleToolDao.selectInfo(sqlStr);
		
		System.out.println(sqlStr);
		JObject.put("jyqktjResult", JSONArray.fromObject(listJyqk));
		return ResponeModel.ok(JObject);
	}
	/** 渠道信息推送情况统计 */
	@PostMapping("/selectQdtsqktj.do")
	public ResponeModel selectQdtsqktj(@RequestParam(required=false) String tjrqc,@RequestParam(required=false) String tjrqd) {
		String sqlStr = "select nvl(t.qdid,f.qdid) qdid,nvl(t.xxtsqdtjz,0) xxtsqdtjz,nvl(f.lmgxqdtjz,0) lmgxqdtjz\n" +
				"from \n" +
				"(select t.qdid,\n" +
				"       sum(t.tjz) xxtsqdtjz\n" +
				"  from wt_s_rtjxx t\n" +
				"  where t.tjzb = 'xxtsl'\n" +
				"        and to_char(t.tjrq, 'yyyy-mm-dd') >= '"+tjrqc+"'\n" +
				"        and to_char(t.tjrq, 'yyyy-mm-dd') <= '"+tjrqd+"'\n" +
				"        and t.qdid is not null\n" +
				"        group by t.qdid ) t full outer join\n" +
				"   (select t.qdid,\n" +
				"       sum(t.tjz) lmgxqdtjz\n" +
				"  from wt_s_rtjxx t\n" +
				"  where t.tjzb = 'lmgxl'\n" +
				"        and to_char(t.tjrq, 'yyyy-mm-dd') >= '"+tjrqc+"'\n" +
				"        and to_char(t.tjrq, 'yyyy-mm-dd') <= '"+tjrqd+"'\n" +
				"        and t.qdid is not null\n" +
				"        group by t.qdid ) f\n" +
				"        on t.qdid= f.qdid ";

		List<Map<String,Object>> list = oracleToolDao.selectInfo(sqlStr);
		return ResponeModel.ok(list);
	}
	/** 渠道栏目更新详情 */
	@PostMapping("/selectQdlmgxxq.do")
	public ResponeModel selectQdlmgxxq(@RequestParam(required=false) String qdid,@RequestParam(required=false) String tjrqc,@RequestParam(required=false) String tjrqd) {
		String sqlStr = "select t.qdid,t.tjzbmx,sum(t.tjz) tjz,(select n.classname from wt_zhfwpt_qt_class n where n.classid = t.tjzbmx) classname "+
				" from wt_s_rtjxx t where t.tjzb = 'lmgxl' and t.qdid = '"+qdid+"' " +
				" and to_char(t.tjrq, 'yyyy-mm-dd') >= '"+tjrqc+"' and to_char(t.tjrq, 'yyyy-mm-dd') <= '"+tjrqd+"' group by t.qdid,t.tjzbmx";
		List<Map<String,Object>> list = oracleToolDao.selectInfo(sqlStr);
		return ResponeModel.ok(list);
	}
	/** 渠道注册情况统计 */
	@PostMapping("/selectQdzcqktj.do")
	public ResponeModel selectQdzcqktj(@RequestParam(required=false) String qdid) {
		//如果渠道ID不为空，则仅统计指定渠道
		String paramQdid = "";
		if(!SystemUtil.isEmpty(qdid)){
			paramQdid = " and s.qdid = '"+qdid+"' ";
		}
		String sqlStr = "select s.qdid,\n" +
				"       s.dqzcrs,\n" +
				"       s.ygbzcrs,\n" +
				"       s.zczrs,\n" +
				"       t.blyhs,\n" +
				"       decode(s.zczrs, 0, 0, round(t.blyhs / s.zczrs,4)) hdyhbl,\n" +
				"       round((case\n" +
				"               when s.qdid in ('QD001', 'QD002', 'QD004') then\n" +
				"                s.zczrs / (s.grzhs + s.dwzhs + s.kfszhs + s.lpzhs)\n" +
				"               else\n" +
				"                0\n" +
				"             end),\n" +
				"             4) yhzcbl\n" +
				"  from (select a.qdid,\n" +
				"               sum(decode(a.dqzt, 'Y', 1, 0)) dqzcrs,\n" +
				"               sum(decode(a.dqzt, 'N', 1, 0)) ygbzcrs,\n" +
				"               count(*) zczrs,\n" +
				"               (select count(*)\n" +
				"                  from zgrzhxx n\n" +
				"                 where n.xhrq is null\n" +
				"                    or n.xhrq > sysdate) grzhs,\n" +
				"               (select count(*)\n" +
				"                  from zdwzhxx n\n" +
				"                 where n.dwxhrq is null\n" +
				"                    or n.dwxhrq > sysdate) dwzhs,\n" +
				"               (select count(*) from zkfsinfo n where n.dqzt = 'Y') kfszhs,\n" +
				"               (select count(*) from zxqlpxx n where n.zt = 'Y') lpzhs\n" +
				"               \n" +
				"          from froperyhqdgxb a\n" +
				"         where a.qdid <> 'QD000'\n" +
				"         group by a.qdid) s,(select count(distinct m.yhid) blyhs,m.qdid\n" +
				"                  from wt_yhfwrzb m\n" +
				"                 where to_char(m.rq, 'yyyy-mm-dd') <=\n" +
				"                       to_char(sysdate, 'yyyy-mm-dd')\n" +
				"                   and to_char(m.rq, 'yyyy-mm-dd') >=\n"+
				"                       to_char(add_months(sysdate,-3), 'yyyy-mm') || '-01' group by m.qdid)  t \n" +
				"                       where t.qdid=s.qdid\n" +paramQdid+
				" order by s.qdid\n ";
		List<Map<String,Object>> list = oracleToolDao.selectInfo(sqlStr);
		return ResponeModel.ok(list);
	}
	/** 渠道用户体验评价统计 */
	@PostMapping("/selectQdyhtytj.do")
	public ResponeModel selectQdyhtytj(@RequestParam(required=false) String tjrqc,@RequestParam(required=false) String tjrqd,@RequestParam(required=false) String qdid) {
		JSONObject JObject = new JSONObject();
		if(StringUtils.isEmpty(tjrqc)){
			tjrqc = "1990-01-01";
		}
		if(StringUtils.isEmpty(tjrqd)){
			tjrqd = "2100-01-01";
		}
		//统计渠道用户满意度
		String sqlStr = "                 select \n" +
				"                       t.tjzbmx tjzbmx,\n" +
				"                       nvl(sum(t.tjz),0) pjrs\n" +
				"                  from wt_s_rtjxx t\n" +
				"                 where t.tjzb = 'yhmyd'\n" +
				"                   and to_char(t.tjrq, 'yyyy-mm-dd') >= '"+tjrqc+"'\n" +
				"                   and to_char(t.tjrq, 'yyyy-mm-dd') <= '"+tjrqd+"'\n" ;
				if(!StringUtils.isEmpty(qdid)){
					sqlStr += "                    and t.qdid='"+qdid+"'\n" ;
				}

				sqlStr += "                 group by t.tjzbmx\n";
		List<Map<String,Object>> listMyd = oracleToolDao.selectInfo(sqlStr);
		JObject.put("yhmydResult", JSONArray.fromObject(listMyd));
		//统计渠道用户投诉率
		sqlStr = "select ss.tsyhs,ss.djyhs,decode(ss.djyhs,0,0,round(ss.tsyhs*100/ss.djyhs,2)) yhtsl "
				+ " from (select nvl(sum(decode(t.tjzb,'tsyhs',t.tjz,0)),0) tsyhs,nvl(max(decode(t.tjzb,'djyhs',t.tjz,0)),0) djyhs "
				+ " from wt_s_rtjxx t where t.tjzb in ('tsyhs','djyhs') and to_char(t.tjrq, 'yyyy-mm-dd') >= '"+tjrqc+"' "
				+ " and to_char(t.tjrq, 'yyyy-mm-dd') <= '"+tjrqd +"' ";
		if(!StringUtils.isEmpty(qdid)){
			sqlStr += "                    and t.qdid='"+qdid+"'\n" ;
		}
		sqlStr += ") ss";
		List<Map<String,Object>> listTsl = oracleToolDao.selectInfo(sqlStr);
		JObject.put("yhtslResult", JSONArray.fromObject(listTsl));
		return ResponeModel.ok(JObject);
	}
	
	/** 渠道运行指标统计 */
	@PostMapping("/selectQdyxzbtj.do")
	public ResponeModel selectQdyxzbtj(@RequestParam(required=false) String qdid,@RequestParam(required=false) String tjrqc,@RequestParam(required=false) String tjrqd) {
		JSONObject JObject = new JSONObject();
		//如果渠道ID不为空，则仅统计指定渠道
		String paramQdid = "";
		if(!SystemUtil.isEmpty(qdid)){
			paramQdid = " and t.qdid in ("+SystemUtil.arrToSqlInStr(qdid.split(","))+") ";
		}
		//统计业务办理类型
		String sqlStr = "select f.fwfl tjzbmx,sum(t.tjz) qdtjz from wt_s_rtjxx t,wt_fwdjxx f where f.fwid=t.tjzbmx and f.fwxz='1' and t.tjzb = 'ywqqcgs' " +paramQdid+
				" and to_char(t.tjrq, 'yyyy-mm-dd') >= '"+tjrqc+"' and to_char(t.tjrq, 'yyyy-mm-dd') <= '"+tjrqd+"' " + 
				" group by f.fwfl order by f.fwfl ";
		List<Map<String,Object>> listYwbllx = oracleToolDao.selectInfo(sqlStr);
		JObject.put("ywbllxResult", JSONArray.fromObject(listYwbllx));
		//统计访问时间段
		sqlStr = "select t.tjzbmx,sum(t.tjz) qdtjz from wt_s_rtjxx t where t.tjzb = 'sdfwl' " +paramQdid+
				" and to_char(t.tjrq, 'yyyy-mm-dd') >= '"+tjrqc+"' and to_char(t.tjrq, 'yyyy-mm-dd') <= '"+tjrqd+"' "+ 
				" group by t.tjzbmx order by t.tjzbmx ";
		List<Map<String,Object>> listYwblsjd = oracleToolDao.selectInfo(sqlStr);
		JObject.put("ywblsjdResult", JSONArray.fromObject(listYwblsjd));
		return ResponeModel.ok(JObject);
	}
	/** 渠道业务办理分布统计 */
	@PostMapping("/selectQdywblfbtj.do")
	public ResponeModel selectQdywblfbtj(@RequestParam(required=false) String tjrqc,@RequestParam(required=false) String tjrqd) {
		JSONObject JObject = new JSONObject();
		String paramTjrqc = "";
		if(!SystemUtil.isEmpty(tjrqc)){
			paramTjrqc = " and to_char(t.tjrq, 'yyyy-mm-dd') >= '"+tjrqc+"' ";
		}
		String paramTjrqd = "";
		if(!SystemUtil.isEmpty(tjrqd)){
			paramTjrqd = " and to_char(t.tjrq, 'yyyy-mm-dd') <= '"+tjrqd+"' ";
		}
		//统计业务办理渠道分布情况
		String sqlStr = " select s.qdid,s.qdtjz,s.ywqqs,decode(s.ywqqs,0,0,round(s.qdtjz/s.ywqqs,2)) qdfbll "
				+ " from (select t.qdid,sum(t.tjz) qdtjz,(select sum(t.tjz) from wt_s_rtjxx t where t.tjzb = 'ywqqcgs' "+paramTjrqc+paramTjrqd+") ywqqs "
				+ " from wt_s_rtjxx t where t.tjzb = 'ywqqcgs' " +paramTjrqc+paramTjrqd+" group by t.qdid) s ";
		List<Map<String,Object>> listQdfbqk = oracleToolDao.selectInfo(sqlStr);
		JObject.put("qdfbqkResult", JSONArray.fromObject(listQdfbqk));
		//统计业务办理用户分布情况
		sqlStr = " select s.qdid,s.qdtjz,s.blyhs,decode(s.blyhs,0,0,round(s.qdtjz/s.blyhs,2)) qdfbll "
				+ " from (select t.qdid,sum(t.tjz) qdtjz, (select sum(t.tjz) from wt_s_rtjxx t where t.tjzb = 'blyhs' "+paramTjrqc+paramTjrqd+") blyhs "
				+ " from wt_s_rtjxx t where t.tjzb = 'blyhs' " +paramTjrqc+paramTjrqd+" group by t.qdid) s ";
		List<Map<String,Object>> listYhfbqk = oracleToolDao.selectInfo(sqlStr);
		JObject.put("yhfbqkResult", JSONArray.fromObject(listYhfbqk));
		return ResponeModel.ok(JObject);
	}
	
	/** 用户信息统计  tjlb 统计类别（zcyh注册用户  hyyh活跃用户） */
	@PostMapping("/selectYhxxtj.do")
	public ResponeModel selectYhxxtj(@RequestParam(required=false) String tjlb) {
		
		JSONObject JObject = new JSONObject();
		String paramTjlb = "";
		if(!SystemUtil.isEmpty(tjlb)&&tjlb.equals("hyyh")){
			paramTjlb = " where   exists (select n.yhid from froperaccnt m,wt_yhfwrzb n where m.froaid = n.yhid " +
					" and m.yhlbdm = '01' and m.xgyhzh1 = a.grzh and n.rq<sysdate and n.rq>= add_months(sysdate,-12)) ";
		}
		//统计业务办理渠道分布情况
		String sqlStr = " select (case when ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) >= 16 and " + 
				"                  ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) <= 20 then " + 
				"                  '16-20' " + 
				"             when ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) >= 21 and " + 
				"                  ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) <= 25 then " + 
				"                  '21-25' " + 
				"             when ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) >= 26 and " + 
				"                  ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) <= 30 then " + 
				"                  '26-30' " + 
				"             when ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) >= 31 and " + 
				"                  ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) <= 35 then " + 
				"                  '31-35' " + 
				"             when ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) >= 36 and " + 
				"                  ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) <= 40 then " + 
				"                  '36-40' " + 
				"             when ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) >= 41 and " + 
				"                  ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) <= 45 then " + 
				"                  '41-45' " + 
				"             when ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) >= 46 and " + 
				"                  ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) <= 50 then " + 
				"                  '46-50' " + 
				"             when ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) >= 51 and " + 
				"                  ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) <= 55 then " + 
				"                  '51-55' " + 
				"             when ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) >= 56 and" + 
				"                  ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) <= 60 then " + 
				"                  '56-60' " + 
				"             when ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) >= 61 and " + 
				"                  ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) <= 65 then " + 
				"                  '61-65' " + 
				"             when ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) >= 66 and " + 
				"                  ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) <= 70 then " + 
				"                  '66-70' " + 
				"             when ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) >= 71 and " + 
				"                  ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) <= 75 then " + 
				"                  '71-75' " + 
				"             when ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) >= 76 and " + 
				"                  ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) <= 80 then " + 
				"                  '76-80' " + 
				"             when ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) >= 81 and " + 
				"                  ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) <= 85 then " + 
				"                  '81-85' " + 
				"             when ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) >= 86 and " + 
				"                  ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) <= 90 then " + 
				"                  '86-90' " + 
				"             when ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) >= 91 and " + 
				"                  ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) <= 95 then " + 
				"                  '91-95' " + 
				"             when ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) >= 96 and " + 
				"                  ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) <= 100 then " + 
				"                  '96-100' " + 
				"             else " + 
				"              n.csrq " + 
				"       end) nld, " + 
				"       count(*) nldrs " + 
				"  from (select a.grzh, a.xingming, a.csrq " +
				"          from mv_zperson_age a " +
				"          " + paramTjlb+
				"           ) n " +
				" group by (case when ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) >= 16 and " + 
				"                  ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) <= 20 then " + 
				"                  '16-20' " + 
				"             when ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) >= 21 and " + 
				"                  ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) <= 25 then " + 
				"                  '21-25' " + 
				"             when ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) >= 26 and " + 
				"                  ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) <= 30 then " + 
				"                  '26-30' " + 
				"             when ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) >= 31 and " + 
				"                  ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) <= 35 then " + 
				"                  '31-35' " + 
				"             when ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) >= 36 and " + 
				"                  ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) <= 40 then " + 
				"                  '36-40' " + 
				"             when ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) >= 41 and " + 
				"                  ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) <= 45 then " + 
				"                  '41-45' " + 
				"             when ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) >= 46 and " + 
				"                  ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) <= 50 then " + 
				"                  '46-50' " + 
				"             when ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) >= 51 and " + 
				"                  ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) <= 55 then " + 
				"                  '51-55' " + 
				"             when ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) >= 56 and " + 
				"                  ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) <= 60 then " + 
				"                  '56-60' " + 
				"             when ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) >= 61 and " + 
				"                  ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) <= 65 then " + 
				"                  '61-65' " + 
				"             when ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) >= 66 and " + 
				"                  ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) <= 70 then " + 
				"                  '66-70' " + 
				"             when ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) >= 71 and " + 
				"                  ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) <= 75 then " + 
				"                  '71-75' " + 
				"             when ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) >= 76 and " + 
				"                  ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) <= 80 then " + 
				"                  '76-80' " + 
				"             when ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) >= 81 and " + 
				"                  ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) <= 85 then " + 
				"                  '81-85' " + 
				"             when ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) >= 86 and " + 
				"                  ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) <= 90 then " + 
				"                  '86-90' " + 
				"             when ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) >= 91 and " + 
				"                  ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) <= 95 then " + 
				"                  '91-95' " + 
				"             when ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) >= 96 and " + 
				"                  ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) <= 100 then " + 
				"                  '96-100' " + 
				"             else " + 
				"              n.csrq " + 
				"       end) " + 
				"     order by (case when ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) >= 16 and " + 
				"                  ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) <= 20 then " + 
				"                  '16-20' " + 
				"             when ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) >= 21 and " + 
				"                  ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) <= 25 then " + 
				"                  '21-25' " + 
				"             when ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) >= 26 and " + 
				"                  ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) <= 30 then " + 
				"                  '26-30' " + 
				"             when ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) >= 31 and " + 
				"                  ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) <= 35 then " + 
				"                  '31-35' " + 
				"             when ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) >= 36 and " + 
				"                  ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) <= 40 then " + 
				"                  '36-40' " + 
				"             when ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) >= 41 and " + 
				"                  ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) <= 45 then " + 
				"                  '41-45' " + 
				"             when ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) >= 46 and " + 
				"                  ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) <= 50 then " + 
				"                  '46-50' " + 
				"             when ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) >= 51 and " + 
				"                  ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) <= 55 then " + 
				"                  '51-55' " + 
				"             when ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) >= 56 and " + 
				"                  ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) <= 60 then " + 
				"                  '56-60' " + 
				"             when ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) >= 61 and " + 
				"                  ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) <= 65 then " + 
				"                  '61-65' " + 
				"             when ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) >= 66 and " + 
				"                  ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) <= 70 then " + 
				"                  '66-70' " + 
				"             when ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) >= 71 and " + 
				"                  ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) <= 75 then " + 
				"                  '71-75' " + 
				"             when ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) >= 76 and " + 
				"                  ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) <= 80 then " + 
				"                  '76-80' " + 
				"             when ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) >= 81 and " + 
				"                  ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) <= 85 then " + 
				"                  '81-85' " + 
				"             when ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) >= 86 and " + 
				"                  ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) <= 90 then " + 
				"                  '86-90' " + 
				"             when ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) >= 91 and " + 
				"                  ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) <= 95 then " + 
				"                  '91-95' " + 
				"             when ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) >= 96 and " + 
				"                  ceil(months_between(sysdate, to_date(n.csrq, 'yyyymmdd'))/12) <= 100 then " + 
				"                  '96-100' " + 
				"             else " + 
				"              n.csrq " + 
				"       end) " + 
				"";
		List<Map<String,Object>> listNlqk = oracleToolDao.selectInfo(sqlStr);
		JObject.put("nlqkResult", JSONArray.fromObject(listNlqk));
		//统计业务办理用户分布情况
		sqlStr = " select decode(a.xb,0,'女','男') xingbie,count(*) xbrs " +
				"          from mv_zperson_age a " +
				"          " + paramTjlb +
				"         group by a.xb " +
				"         order by a.xb ";
		List<Map<String,Object>> listXbqk = oracleToolDao.selectInfo(sqlStr);
		JObject.put("xbqkResult", JSONArray.fromObject(listXbqk));
		return ResponeModel.ok(JObject);
	}
}
