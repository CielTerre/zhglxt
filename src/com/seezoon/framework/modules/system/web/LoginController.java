package com.seezoon.framework.modules.system.web;

import javax.servlet.http.HttpServletRequest;

import com.seezoon.framework.modules.zhfw.entity.DxCzhjb;
import com.seezoon.framework.modules.zhfw.entity.DxFshjb;
import com.seezoon.framework.modules.zhfw.entity.Froperaccnt;
import com.seezoon.framework.modules.zhfw.entity.WtSjyzmxx;
import com.seezoon.framework.modules.zhfw.service.*;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seezoon.framework.common.Constants;
import com.seezoon.framework.common.context.beans.ResponeModel;
import com.seezoon.framework.common.utils.IpUtils;
import com.seezoon.framework.common.web.BaseController;
import com.seezoon.framework.modules.system.entity.SysLoginLog;
import com.seezoon.framework.modules.system.entity.SysUser;
import com.seezoon.framework.modules.system.service.LoginSecurityService;
import com.seezoon.framework.modules.system.service.SysLoginLogService;
import com.seezoon.framework.modules.system.service.SysUserService;
import com.seezoon.framework.modules.system.shiro.ShiroUtils;
import com.seezoon.framework.modules.system.web.form.LoginForm;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("${admin.path}")
public class LoginController extends BaseController{
	private static final String qdid="QD000";

	@Autowired
	private SysLoginLogService sysLoginLogService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private LoginSecurityService loginSecurityService;
	@Autowired
	private FroperaccntService froperaccntService;
	@Autowired
	private WtSjyzmxxService wtSjyzmxxService;
	@Autowired
	private OracleToolService oracleToolService;
	@Autowired
	private DxFshjbService dxFshjbService;
	@Autowired
	private DxCzhjbService dxCzhjbService;
	
	@PostMapping("/login.do")
	public ResponeModel login(@Validated LoginForm userForm,BindingResult bindingResult,HttpServletRequest request) {
		Subject subject = ShiroUtils.getSubject();
		String loginName = userForm.getLoginName();
		String yzm = userForm.getYzm();
		String ip = IpUtils.getIpAddr(request);
		String userAgent = request.getHeader("User-Agent");
		try {
			
			Long lockCnt = loginSecurityService.getLoginFailCount(loginName);
			if (null != lockCnt && lockCnt >= 5) {
				//超级管理员不锁定
				SysUser findByLoginName = sysUserService.findByLoginName(loginName);
				if (null == findByLoginName || !ShiroUtils.isSuperAdmin(findByLoginName.getId())) {
					//登录成功 记录日志
					sysLoginLogService.loginLogByLoginName(SysLoginLog.LOCK_24, loginName, ip, userAgent);
					return ResponeModel.error("账户锁定24小时");
				}
			}
			//根据loginName查询手机号码
			SysUser sysUser = sysUserService.findByLoginName(loginName);
			if (sysUser ==null){
				return ResponeModel.error("用户不存在");
			}
			String mobile = sysUser.getMobile(); //手机号码
			if(StringUtils.isNotEmpty(sysUser.getFroaid())){
				Froperaccnt froperaccnt = froperaccntService.findById(sysUser.getFroaid());
				if (froperaccnt == null){
					return ResponeModel.error("用户关联中心账号不存在");
				}
				if(StringUtils.isEmpty(mobile) || mobile.length() != 11){
					mobile = froperaccnt.getYhsjhm();
				}
			}
			if (StringUtils.isEmpty(mobile) || mobile.length() != 11){
				return ResponeModel.error("手机号码不存在或者不为11位");
			}

			JSONObject jsonObject = wtSjyzmxxService.checkVerificationCode(qdid,mobile,"WT00004",yzm);
			if (jsonObject.get("success")==null || jsonObject.getString("success").equals("false")){
				return ResponeModel.error(String.valueOf(jsonObject.get("msg")));
			}

			UsernamePasswordToken token = new UsernamePasswordToken(loginName, userForm.getPassword(),Constants.YES.equals(userForm.getRememberMe()));
			subject.login(token);
			loginSecurityService.clearLoginFailTimes(loginName);
			//登录成功 记录日志
			sysLoginLogService.loginLogByUserId(SysLoginLog.SUCCESS,ShiroUtils.getUserId(), ip, userAgent);
		} catch (UnknownAccountException|IncorrectCredentialsException|LockedAccountException e) {
			if (e instanceof UnknownAccountException ) {
				logger.warn("loging account not exist loginName:{},IP:{},User-Agent:{}",userForm.getLoginName(),ip,userAgent);
				return ResponeModel.error("账户密码错误,连续错误5次将锁定24小时");
			} else {
				loginSecurityService.incrementLoginFailTimes(loginName);
				if (e instanceof IncorrectCredentialsException) {
					//账户密码错误
					sysLoginLogService.loginLogByLoginName(SysLoginLog.PASSWORD_WRONG,loginName, ip, userAgent);
					return ResponeModel.error("账户密码错误,连续错误5次将锁定24小时");
				} else if (e instanceof LockedAccountException) {
					//账号已被锁定
					sysLoginLogService.loginLogByLoginName(SysLoginLog.USER_STAUTS_STOP,loginName, ip, userAgent);
					return ResponeModel.error("账号已被禁用");
				}
			}
		}
		return ResponeModel.ok();
	}

	@PostMapping("/getVerificationCode")
	public ResponeModel getVerificationCode(String loginName,String type){
		//根据loginName查询手机号码
		SysUser sysUser = sysUserService.findByLoginName(loginName);
		if (sysUser ==null){
			return ResponeModel.error("用户不存在");
		}
		String mobile = sysUser.getMobile(); //手机号码
		String sjssqy = "1"; //默认省内
		String yhid = sysUser.getLoginName();
		if(StringUtils.isNotEmpty(sysUser.getFroaid())){
			Froperaccnt froperaccnt = froperaccntService.findById(sysUser.getFroaid());
			if (froperaccnt == null){
				return ResponeModel.error("用户关联中心账号不存在");
			}
			sjssqy = froperaccnt.getYhsjssqy(); //手机所属区域
			yhid = froperaccnt.getFroaid();
			if(StringUtils.isEmpty(mobile) || mobile.length() != 11){
				mobile = froperaccnt.getYhsjhm();
			}
		}
		if (StringUtils.isEmpty(mobile) || mobile.length() != 11){
			return ResponeModel.error("手机号码不存在或者不为11位");
		}

		//生成短息验证码
		String selSqlStr = "select trunc(dbms_random.value(100000,999999)) verifcode from dual ";

		List<Map<String,Object>> list = oracleToolService.selectInfo(selSqlStr);
		if(list==null){
			return ResponeModel.error("生成验证码失败");
		}
		String verifcode = String.valueOf(list.get(0).get("VERIFCODE"));  //验证码

		//获取该手机号码最近一次的此短息模板的验证信息
		WtSjyzmxx wtSjyzmxx = new WtSjyzmxx();
		wtSjyzmxx.setQdid(qdid);
		wtSjyzmxx.setDtmyz(type);
		wtSjyzmxx.setSjhm(mobile);
		wtSjyzmxx.setSortField("yzmfssj");
		wtSjyzmxx.setDirection("desc");
		List<WtSjyzmxx> yzmxxList = wtSjyzmxxService.findList(wtSjyzmxx);
		if (yzmxxList.size() > 0){
			WtSjyzmxx wtSjyzmxxOld = yzmxxList.get(0);
			if(wtSjyzmxxOld!=null){
				//插入新的短息验证信息前作废原有的验证
				WtSjyzmxx wtSjyzmxxNew = new WtSjyzmxx();
				wtSjyzmxxNew.setYwlsh(wtSjyzmxxOld.getYwlsh());
				wtSjyzmxxNew.setYzmzt("F");
				wtSjyzmxxService.updateSelective(wtSjyzmxxNew);
			}
		}

		//插入新手机验证码
		WtSjyzmxx wtsjyzmxx = new WtSjyzmxx();
		String ywlsh = oracleToolService.getYwlsh();			//短信验证码发送流水号
		wtsjyzmxx.setYwlsh(ywlsh);
		wtsjyzmxx.setQdid(qdid);
		wtsjyzmxx.setYhid(yhid);
		wtsjyzmxx.setYhlbdm("01");
		wtsjyzmxx.setDtmyz(type);
		wtsjyzmxx.setSjhm(mobile);
		wtsjyzmxx.setYzm(verifcode);
		wtsjyzmxx.setYzmfssj(new Timestamp(new Date().getTime()));
		wtsjyzmxx.setYzmzt("Y");			//默认 Y 有效  , 根据短信发送情况更新是否作废
		wtSjyzmxxService.save(wtsjyzmxx);

		String dxnr = "您正在登陆随州住房公积金综合服务管理系统，登陆验证码："+verifcode+"，10分钟内有效，请勿泄露！";
		String ywlb = "01";
		String beizhu = yhid+"登录网厅发送验证码短信<1>条";

		//插入操作痕迹
		DxCzhjb dxCzhjbNew = new DxCzhjb();
		String czywlsh = oracleToolService.getYwlsh();		//获取短信操作业务流水号
		dxCzhjbNew.setYwlsh(czywlsh);
		dxCzhjbNew.setXgywpch("");
		dxCzhjbNew.setBldbd("10");
		dxCzhjbNew.setBlywwd("1010");
		dxCzhjbNew.setYwlb(ywlb);
		dxCzhjbNew.setCzrzh(qdid);
		dxCzhjbNew.setCzrxm("综合服务管理系统");
		dxCzhjbNew.setBeizhu(beizhu);
		dxCzhjbNew.setYwrs(new BigDecimal(1));
		dxCzhjbNew.setWktdxrs(new BigDecimal(0));
		dxCzhjbNew.setSjhmwxrs(new BigDecimal(0));
		dxCzhjbNew.setDxfsrs(new BigDecimal(1));
		dxCzhjbService.save(dxCzhjbNew);

        //插入短信发送痕迹
        DxFshjb dxFshjb = new DxFshjb();
        dxFshjb.setYwlsh(ywlsh);		//短信发送业务流水号誉与验证码流水号相同
        dxFshjb.setBldbd("10");
        dxFshjb.setBlywwd("1010");
        dxFshjb.setXgywpch("");
        dxFshjb.setCzywlsh(czywlsh);
        dxFshjb.setDwzh("");
        dxFshjb.setGrzh("");
        dxFshjb.setXgywlsh("");
        dxFshjb.setYwlb(ywlb);
        dxFshjb.setSjhm(mobile);
        dxFshjb.setFszt("F");	//默认为发送失败，  确认发送成功后更新为成功状态
        dxFshjb.setFsrzh(qdid);
        dxFshjb.setFsrxm("综合服务管理系统");
        dxFshjb.setDxnr(dxnr);
        dxFshjb.setSjssqy(sjssqy);
        //插入短信发送痕迹表
		dxFshjbService.save(dxFshjb);

        String fszt = "C";			//默认发送状态为成功状态
        String dfywlsh = "";
        String sbyy = "";

		//发送短息验证码（新渠道）
		JSONObject JObjectSendMsg = wtSjyzmxxService.sendDxMsgNewCd(dxFshjb);
		//如果短信发送成功,则修改发送状态为成功,否则为失败
		if (JObjectSendMsg.getString("success").equals("true")) {
			fszt = "C";
		} else {
			fszt = "F";
			sbyy = JObjectSendMsg.getString("msg");
		}
		dfywlsh = JObjectSendMsg.getString("dfywlsh");
        //更新短信发送痕迹状态
        DxFshjb dxFshjbNew = new DxFshjb();
        dxFshjbNew.setYwlsh(ywlsh);
        dxFshjbNew.setFszt(fszt);
        dxFshjbNew.setSbyy(sbyy);
        dxFshjbNew.setDfywlsh(dfywlsh);
        dxFshjbService.updateSelective(dxFshjbNew);
        if(fszt.equals("F")){
            //如果失败则，更新验证码状态为过期状态
            WtSjyzmxx wtsjyzmxxNew = new WtSjyzmxx();
            wtsjyzmxxNew.setYwlsh(ywlsh);
            wtsjyzmxxNew.setYzmzt("F");
            wtSjyzmxxService.updateSelective(wtsjyzmxxNew);
        }
		return ResponeModel.ok();
	}
}
