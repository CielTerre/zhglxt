package com.seezoon.framework.modules.zhfw.util;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;

import net.sf.json.JSONObject;

public class SystemUtil {
	/** 前补零，获取指定长度的字符串 */
	public static String getLengthStr(Object obj,int len){
		String thisobj = obj.toString();
		String result = thisobj;		
		for(int i=thisobj.length();i<len;i++){
			result = "0"+result;
		}
		return result;
	}
	
	/** 去除字符串中的空格 */
	public static String replaceBlank(String str) {
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str.trim());
			dest = m.replaceAll("");
		}
		return dest;
	};
	/** 验证字符串中是否有某个字符 */
	public static boolean checkStrHasCode(String str,String pattern) {
		if (!isEmpty(str)) {
			Matcher match = Pattern.compile(pattern).matcher(str);
			return match.find();	
		}
		return false;
	};
	
	/** 将null转换成字符串空 */
	public static String ObjectToStr(Object obj){
		if (isEmpty(obj)) {
            return "";
        } 
        if (obj instanceof Date) {
        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format((Date)obj);
        }
		return obj.toString().trim();
	}
	/** 将null转换double 0 */
	public static double ObjectToDouble(Object obj){
		if(isEmpty(obj)){
			return 0.0;
		}
		return new BigDecimal(obj.toString()).doubleValue();
	}
	/** 将null转换成int 0 */
	public static int ObjectToInt(Object obj){
		if(isEmpty(obj)){
			return 0;
		}
		return new BigDecimal(obj.toString()).intValue();
	}
	/** 将null转换成int 0 */
	public static BigDecimal ObjectToBigDecimal(Object obj){
		if(isEmpty(obj)){
			return new BigDecimal(0);
		}
		return new BigDecimal(obj.toString());
	}
	/** 将字符串数组转成sql中的in字符串 */
	public static String arrToSqlInStr(String[] stringz){
		String returnStr = "";
		for(int i=0;i<stringz.length;i++){
			returnStr = returnStr+"'"+stringz[i]+"'";
			if(i<stringz.length-1){
				returnStr = returnStr+",";
			}
		}
		if(SystemUtil.isEmpty(returnStr)){
			returnStr = "''";
		}
		return returnStr;
	}
	/** 对数组中相同字符串去重 */
	public static String[] arrRemoveRepeat(String[] stringz){
		Set<String> set = new HashSet<String>();
        for(String sa : stringz){
        	set.add(sa);
        }
        return set.toArray(new String[]{});
	}
	/** 判断某个字符串是否是空 */
	public static boolean isEmpty(Object str){
		if(str==null || replaceBlank(str.toString()).length()==0){
			return true;
		}
		return false;
	}
	/** 对double类型格式化，避免浮点型计算错误问题 */
	public static double doubleFormat(double obj,String formatStr){
		return new BigDecimal(new DecimalFormat(formatStr).format(obj)).doubleValue();
	}
	/** 对double类型格式化并返回字符串，避免浮点型计算错误问题 */
	public static String doubleFormatToStr(Object obj,String formatStr){
		return new DecimalFormat(formatStr).format(ObjectToDouble(obj));
	}
	
	
	/** 根据身份证号获取出生年月、性别信息  jsrq 计算年龄时的日期 格式为YYYY-MM-DD*/
	public static JSONObject getValueByZjhm(Object zjhm){
		JSONObject JObject = new JSONObject();
		if(isEmpty(zjhm)){
			JObject.put("success", "false");
			JObject.put("csny", "");
			JObject.put("xingbiec", "");
			JObject.put("xingbie", "");	//住建部要求的性别
			JObject.put("wzmm", "");
			JObject.put("zjhm", "");
			JObject.put("nl", "");
			JObject.put("msg", "身份证号为空");
			return JObject;
		}
		String thisZjhm = SystemUtil.replaceBlank(SystemUtil.ObjectToStr(zjhm)).toUpperCase();
		if(thisZjhm.length()!=18){
			JObject.put("success", "false");
			JObject.put("csny", "");
			JObject.put("xingbiec", "");
			JObject.put("xingbie", "");	//住建部要求的性别
			JObject.put("wzmm", "");
			JObject.put("zjhm", "");
			JObject.put("nl", "");
			JObject.put("msg", "身份证位数必须为18位");
			return JObject;
		}
		int nowYear = Calendar.getInstance().get(Calendar.YEAR);
		int nowMonthDay = SystemUtil.ObjectToInt(new SimpleDateFormat("MMdd").format(new Date()));
		if(thisZjhm.length()==15){
			JObject.put("csny", "19"+thisZjhm.substring(6, 8)+thisZjhm.substring(8, 10));
			JObject.put("xingbiec", new BigDecimal(thisZjhm.substring(14, 15)).intValue()%2 == 0?"女":"男");
			JObject.put("xingbie", new BigDecimal(thisZjhm.substring(14, 15)).intValue()%2 == 0?"2":"1");
			
			int thisAge = nowYear-new BigDecimal("19"+thisZjhm.substring(6, 8)).intValue();
			if(nowMonthDay<SystemUtil.ObjectToInt(thisZjhm.substring(8, 12))){
				thisAge = thisAge-1;
			}
			JObject.put("nl", thisAge);
			//龚红端 2013-5-28 修改
			//JObject.put("nl", age);
			JObject.put("wzmm", thisZjhm.substring(9, 15));
		}
		if(thisZjhm.length()==18){
			JObject.put("csny", thisZjhm.substring(6, 10)+thisZjhm.substring(10, 12));
			JObject.put("xingbiec", new BigDecimal(thisZjhm.substring(16, 17)).intValue()%2 == 0?"女":"男");
			JObject.put("xingbie", new BigDecimal(thisZjhm.substring(16, 17)).intValue()%2 == 0?"2":"1");
			int thisAge = nowYear-new BigDecimal(thisZjhm.substring(6, 10)).intValue();
			if(nowMonthDay<SystemUtil.ObjectToInt(thisZjhm.substring(10, 14))){
				thisAge = thisAge-1;
			}
			JObject.put("nl", thisAge);
			
			JObject.put("wzmm", thisZjhm.substring(12, 18));
		}
		JObject.put("success", "true");
		JObject.put("msg", "");
		JObject.put("zjhm", thisZjhm);
		return JObject;
	}
	
	
	
	/** 根据15位身份证号获取18位身份证号 */
	public String getNewZjhm(String zjhm) {
		if(SystemUtil.isEmpty(zjhm)){
			return "";
		}
		String thisZjhm = zjhm.replace(" ", "").toUpperCase();
		if(thisZjhm.length()==18){
			return thisZjhm;
		}
		if(thisZjhm.length()!=15){
			return "";
		}
		final int[] W = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1};
		final String[] A = {"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};
		int i, j, s = 0;
		String newid = thisZjhm.substring(0, 6) + "19" + thisZjhm.substring(6, thisZjhm.length());
		for (i = 0; i < newid.length(); i++) {
			j = Integer.parseInt(newid.substring(i, i + 1)) * W[i];
			s = s + j;
		}
		s = s % 11;
		newid = newid + A[s];
		return newid;
	}
	/** 获取指定字符串的的指定长度（主要做验证字符串长度使用） */
	public static String getStrByMaxLength(Object obj,int length){
		String inStr = SystemUtil.replaceBlank((obj==null?"":obj).toString());
		try {
			for(int i=0;i<=inStr.length();i++){
				if(inStr.substring(0, i).getBytes("GBK").length>length){
					return inStr.substring(0, i-1);
				}
			}
			return inStr;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}
	}
	/** 计算两个日期之间的月份差(同开始结束年月结果将是1) */
	public static int betweenMonth(String ksny,String jsny){
		if(ksny.length()!=6||jsny.length()!=6){
			return -1;
		}
		int startYear = SystemUtil.ObjectToInt(ksny.substring(0, 4));
		int startMonth = SystemUtil.ObjectToInt(ksny.substring(4, 6));
		int endYear = SystemUtil.ObjectToInt(jsny.substring(0, 4));
		int endMonth = SystemUtil.ObjectToInt(jsny.substring(4, 6));
		return  (endYear-startYear)*12+(endMonth-startMonth)+1;
	}
	
	/** 获取本周的周日所在的日期 */
	public static String getCurrentSunday() {
		Calendar c = Calendar.getInstance();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		return df.format(c.getTime());
	}
	
	/** 获取本周的周一所在的日期 */
	public static String getCurrentMonday() {
		Calendar c = Calendar.getInstance();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		return df.format(c.getTime());
	}
	
	/** 自动将日期格式补全为YYYY-MM-DD的格式 */
	public static String completionDate(String sDate){
		String nowShowDate = SystemUtil.ObjectToStr(sDate);
		nowShowDate = nowShowDate.replaceAll("/", "-").replaceAll("\\.", "-").replaceAll("年", "-").replaceAll("月", "-").replaceAll("日", "-");
		String[] sDatArr = nowShowDate.split("-");
		if(sDatArr.length!=3){
			if(sDate.length()<8){
				return sDate;
			}
			sDate = sDate.substring(0, 8);
			return sDate.substring(0, 4)+"-"+sDate.substring(4, 6)+"-"+sDate.substring(6);
		}
		String thisYear = sDatArr[0];
		String thisMonth = sDatArr[1];
		String thisDay = sDatArr[2];
		if(thisMonth.length()<2){
			thisMonth = "0"+thisMonth;
		}
		//如果日期中的日长度小于2，则前补0
		if(thisDay.length()<2){
			thisDay = "0"+thisDay;
		}else{
			thisDay = replaceBlank(thisDay.substring(0, 2));
			//如果日期中的日大于某月最后一天的话，则取第一位前补0
			int lastDay = 31;
			if(thisMonth.equals("04")||thisMonth.equals("06")||thisMonth.equals("09")||thisMonth.equals("11")){
				lastDay = 30;
			}
			if(thisMonth.equals("02")&&(ObjectToInt(thisYear)%4)==0){
				lastDay = 29;
			}
			if(thisMonth.equals("02")&&(ObjectToInt(thisYear)%4)!=0){
				lastDay = 28;
			}
			if(ObjectToInt(thisDay)>lastDay){
				thisDay = "0"+thisDay.substring(0, 1);
			}
		}
		return thisYear+"-"+thisMonth+"-"+thisDay;
	}
	
	/** 判断日期格式是否为YYYY-MM-DD */ 
	public static boolean isValidDate(String sDate) {
		String thisDate = completionDate(sDate);
		String datePattern1 = "\\d{4}-\\d{2}-\\d{2}";
		String datePattern2 = "^((\\d{2}(([02468][048])|([13579][26]))"
				+ "[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|"
				+ "(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?"
				+ "((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?("
				+ "(((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?"
				+ "((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
		if (!isEmpty(thisDate)) {
			Pattern pattern = Pattern.compile(datePattern1);
			Matcher match = pattern.matcher(thisDate);
			if (match.matches()) {
				pattern = Pattern.compile(datePattern2);
				match = pattern.matcher(thisDate);
				return match.matches();
			} else {
				return false;
			}
		}
		return false;
	}
	
	/** 验证是否是一个有效的YYYYMM格式的会计年月 */
	public static boolean isValidYYYYMM(String kjny){
		String regStr = "\\d{6}";
		return kjny.matches(regStr);
	}
	
	/** 获取某个会计年月的最后一天的日期 */
	public static String getLastDate(String kjny) throws ParseException{
		if(kjny.length()!=6){
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		String month = kjny.substring(4, 6);
		month = (ObjectToInt(month)+1)+"";
		month = month.length()==2?month:("0"+month);
		return sdf1.format(sdf.parse(kjny.substring(0, 4)+month).getTime()-1);
	}

	/** 获取某个会计年月的第一天的日期 */
	public static String getStartDate(String fyearmonth){
		if(fyearmonth.length()!=6){
			return "";
		}
		return fyearmonth.substring(0, 4)+"-"+fyearmonth.substring(4, 6)+"-01";
	}
	
	/** 获取某个会计年月的上一个(up)、下一个(down)会计年月 */
	public static String getNextFyearmonth(String kjny,String type) throws ParseException{
		if(kjny.length()!=6){
			return kjny;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		String year = kjny.substring(0, 4);
		String month = kjny.substring(4, 6);
		int nextYear = ObjectToInt(year);
		int nextMonth = ObjectToInt(month);
		if(SystemUtil.isEmpty(type)||type.equals("down")){
			nextMonth = nextMonth+1;
		}else{
			nextMonth = nextMonth-1;
		}
		nextYear = nextYear+ObjectToInt(Math.floor(nextMonth/12));
		//如果下一年月除于12求余后等于0，则下一年月就为12的倍数，即为12月
		nextMonth = nextMonth%12;
		if(nextMonth==0){
			nextYear = nextYear-1;
			nextMonth = 12;
		}
		month = nextMonth>=10?(nextMonth+""):("0"+nextMonth);
		return sdf.format(sdf.parse(nextYear+""+month));
	}
	
	//验证传入的值是否是数字
	public static boolean isNumeric(String str){ 
	    Pattern pattern = Pattern.compile("[0-9]*"); 
	    return pattern.matcher(str).matches();    
	 } 
	
	//验证传入的值是否符合身份证的格式
	public static boolean isSfzh(String str){
		return str.matches("^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}[0-9,x,X]$");
	}
	
	//计算两个日期之间的天数差（每月按30天计算）
	public static int getDaysBetweenDate(String startDate, String endDate){
		String[] startDateZ = startDate.split("-");
		String[] endDateZ = endDate.split("-");
		int endDateYear = SystemUtil.ObjectToInt(endDateZ[0]);
		int endDateMonth = SystemUtil.ObjectToInt(endDateZ[1]);
		int endDateDay = SystemUtil.ObjectToInt(endDateZ[2]);
		int startDateYear = SystemUtil.ObjectToInt(startDateZ[0]);
		int startDateMonth = SystemUtil.ObjectToInt(startDateZ[1]);
		int startDateDay = SystemUtil.ObjectToInt(startDateZ[2]);
		int qixian = (endDateYear-startDateYear)*12*30+(endDateMonth-startDateMonth)*30+(endDateDay-startDateDay);
		return qixian;
	}
	//计算两个日期之间的天数差（每月按30天计算）
	public static int getDaysBetweenDate(Date startDate, Date endDate){
		SimpleDateFormat sdfY_m_d = new SimpleDateFormat("yyyy-MM-dd");
		return getDaysBetweenDate(sdfY_m_d.format(startDate), sdfY_m_d.format(endDate));
	}
	 
	 /*** 
     * 日期减一天、加一天 
     *  
     * @param option 
     *            传入类型 pro：日期减一天，next：日期加一天 
     * @param _date 
     *            2014-11-24 
     * @return 减一天：2014-11-23或(加一天：2014-11-25) 
     */  
    public static Date checkOption(String option, String _date) {  
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
        Calendar cl = Calendar.getInstance();  
        Date date = null;  
        try {  
            date = (Date) sdf.parse(_date);  
        } catch (ParseException e) {  
            e.printStackTrace();  
        }  
        cl.setTime(date);  
        if ("pro".equals(option)) {  
            // 时间减一天  
            cl.add(Calendar.DAY_OF_MONTH, -1);  
  
        } else if ("next".equals(option)) {  
            // 时间加一天  
            cl.add(Calendar.DAY_OF_YEAR, 1);  
        } else {  
            // do nothing  
        }  
        date = cl.getTime();  
        return date;  
    }
	//转换文章内容字符串中的标签 富文本编辑器结果 保存前
	public static String htmlspecialchars(String str) {
		str = str.replaceAll("&amp;", "&");
		str = str.replaceAll("&lt;", "<");
		str = str.replaceAll("&gt;", ">");
		str = str.replaceAll("&quot;", "\"");
		return str;
	}

	public static String replaceHtmlTag(String str){
        return str.replaceAll("<[.[^<]]*>", "").replace("&nbsp;","").trim();
    }

	/**
	 * 手工脱敏
	 * @param str 脱敏字符串
	 * @param startNum 前面保留位数
	 * @param endNum 后面保留位数
	 * @return 中间补 ****
	 */
	public static String sgtm(String str,int startNum,int endNum){
		if (StringUtils.isNotEmpty(str)){
			return str.substring(0, startNum) + "****" + str.substring(str.length() - endNum, str.length());
		}else {
			return str;
		}
	}
}
