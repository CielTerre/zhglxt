package com.seezoon.framework.modules.zhfw.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.seezoon.framework.modules.zhfw.dao.OracleToolDao;

@Service
public class YhqyxxService {
	
	@Autowired
	private OracleToolDao oracleToolDao;
	
	public PageInfo<Map<String,Object>> getPageInfo(String qylx,String zh,
		String mc, String zjh,String qyrq,int page,int pageSize){
		
		StringBuffer sql = new StringBuffer("");
		
		if("01".equals(qylx)){
			sql.append("select b.grzh as zh,b.xingming as mc,b.zjhm as zjh,c.yhfzhsmc as qyyh,a.xingming as qyyhzhm,a.styhzhh as qyyhzh,a.sqrq as qyrq,"
					+ "decode(a.dqzt,'S','新申请','F','终止','E','生效中','其他') dqztc,'01' qylx,a.dqzt "
					+ "from zayhkjcsqb a,zpersonalinfo b,zbankinfo c "
					+ "where a.grzh = b.grzh and a.skyhzhbmid = c.yhzhbmid");
			
			if(StringUtil.isNotEmpty(zh)){
				sql.append(" and a.grzh='"+zh+"'");
			}
			if(StringUtil.isNotEmpty(mc)){
				sql.append(" and b.xingming like '%"+mc+"%'");
			}
			if(StringUtil.isNotEmpty(zjh)){
				sql.append(" and b.zjhm='"+zjh+"'");
			}
			if(StringUtil.isNotEmpty(qyrq)){
				sql.append(" and to_date(to_char(a.sqrq,'YYYY-MM-DD'),'YYYY-MM-DD')= to_date('"+qyrq+"','YYYY-MM-DD')");
			}
			
		}else if("02".equals(qylx)){
			sql.append("select b.dwzh as zh,'02' qylx,b.dwmc as mc,b.zzjgdm as zjh,c.yhfzhsmc as qyyh,a.styhzhm as qyyhzhm,a.styhzhh as qyyhzh,a.lrrq as qyrq,a.dqzt as qyzt,"
					+ "decode(a.dqzt,'S','新申请','Y','协议有效中','N','协议已失效','D','协议已删除','其他') dqztc "
					+ "from zdwqyxxb a,zunitinfo b,zbankinfo c "
					+ "where a.dwzh=b.dwzh and a.hjyhzhbmid = c.yhzhbmid");
			
			if(StringUtil.isNotEmpty(zh)){
				sql.append(" and b.dwzh='"+zh+"'");
			}
			if(StringUtil.isNotEmpty(mc)){
				sql.append(" and b.dwmc like '%"+mc+"%'");
			}
			if(StringUtil.isNotEmpty(zjh)){
				sql.append(" and b.zzjgdm='"+zjh+"'");
			}
			if(StringUtil.isNotEmpty(qyrq)){
				sql.append(" and to_date(to_char(a.lrrq,'YYYY-MM-DD'),'YYYY-MM-DD')= to_date('"+qyrq+"','YYYY-MM-DD')");
			}
			
		}else if("03".equals(qylx)){
			sql.append("select a.jkrgjjzh,'03' qylx,a.jkrxm as mc,a.jkrzjh as zjh,d.yhfzhsmc as qyyh,a.dkzh as zh,"
					+ "b.gtjkrkkzh as qyyhzh,b.gtjkrxm as qyyhzhm,b.lrrq as qyrq,'生效中' dqztc "
					+ "from zloanbaseinfo a,dgtjkrxx b,zzhxzinfo c,zbankinfo d "
					+ "where a.dkzh = b.dkzh and a.yhzhbmid = c.yhzhbmid and c.skyhzhbmid = d.yhzhbmid "
					+ "and b.sfzkkzh = 'Y' and a.dkfl= '1' and a.dqzt <> 'N'  and a.dklx <> '03'");
			
			if(StringUtil.isNotEmpty(zh)){
				sql.append(" and a.dkzh='"+zh+"'");
			}
			if(StringUtil.isNotEmpty(mc)){
				sql.append(" and a.jkrxm like '%"+mc+"%'");
			}
			if(StringUtil.isNotEmpty(zjh)){
				sql.append(" and a.jkrzjh='"+zjh+"'");
			}
			if(StringUtil.isNotEmpty(qyrq)){
				sql.append(" and to_date(to_char(b.lrrq,'YYYY-MM-DD'),'YYYY-MM-DD')= to_date('"+qyrq+"','YYYY-MM-DD')");
			}
			
		}else if("04".equals(qylx)){
			sql.append("select b.jkrgjjzh,'04' qylx,b.jkrxm as mc,b.jkrzjh as zjh,a.lrrq as qyrq,a.dqzt,a.dkzh as zh,"
					+ "decode(a.dqzt,'S','新增申请中','E','已生效','F','已终止','D','已删除','其他') dqztc "
					+ "from dk_gjj_hksq a,zloanbaseinfo b where a.dkzh = b.dkzh and b.dqzt <> 'N'  and b.dklx <> '03'");
			
			if(StringUtil.isNotEmpty(zh)){
				sql.append(" and a.dkzh='"+zh+"'");
			}
			if(StringUtil.isNotEmpty(mc)){
				sql.append(" and b.jkrxm like '%"+mc+"%'");
			}
			if(StringUtil.isNotEmpty(zjh)){
				sql.append(" and b.jkrzjh='"+zjh+"'");
			}
			if(StringUtil.isNotEmpty(qyrq)){
				sql.append(" and to_date(to_char(a.lrrq,'YYYY-MM-DD'),'YYYY-MM-DD')= to_date('"+qyrq+"','YYYY-MM-DD')");
			}
		
		}
		
		String sqlStr = sql.toString();
		PageHelper.startPage(page,pageSize, Boolean.TRUE);
		List<Map<String,Object>> list = oracleToolDao.selectInfo(sqlStr);
		PageInfo<Map<String,Object>> pageInfo = new PageInfo<Map<String,Object>>(list);
		
		return pageInfo;
	}
	
}
