package com.seezoon.framework.modules.zhfw.entity;

import com.seezoon.framework.common.entity.BaseEntity;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
/**
 * 渠道设置
 *  2018-7-16 20:24:53
 */
public class WtQdxxb extends BaseEntity<String> {

   private static final long serialVersionUID = 1L;
    /**
     * 渠道ID
     */
    @NotNull
    @Length(min = 1, max = 20)
    private String qdid;
    /**
     * 渠道名称
     */
    @Length(max = 255)
    private String qdmc;
    /**
     * 渠道说明信息
     */
    @Length(max = 255)
    private String qdsm;
    /**
     * 渠道接入IP
     */
    @Length(max = 60)
    private String qdjrip;
    /**
     * 渠道接入端口号
     */
    @Length(max = 20)
    private String qdjrdk;
    /**
     * 渠道接入账号
     */
    @Length(max = 60)
    private String qdjrzh;
    /**
     * 渠道接入密码
     */
    @Length(max = 60)
    private String qdjrmm;
    /**
     * 渠道联系人
     */
    @Length(max = 120)
    private String qdlxr;
    /**
     * 证件类型  CHAR_LIST GRZJLX
     */
    @Length(max = 2)
    private String zjlx;
    /**
     * 证件号码
     */
    @Length(max = 30)
    private String zjhm;
    /**
     * 联系电话
     */
    @Length(max = 20)
    private String lxdh;
    /**
     * 手机号码
     */
    @Length(max = 11)
    private String sjhm;
    /**
     * 邮箱地址
     */
    @Length(max = 120)
    private String yxdz;
    /**
     * 邮箱号码
     */
    @Length(max = 20)
    private String qqhm;
    /**
     * 微信号码
     */
    @Length(max = 60)
    private String wxhm;
    /**
     * 联系地址
     */
    @Length(max = 120)
    private String lxdz;
    /**
     * 邮政编码
     */
    @Length(max = 6)
    private String yzbm;
    /**
     * 最大并发量
     */
    private BigDecimal zdbfl;
    /**
     * 超过最大并发量时提示信息
     */
    @Length(max = 200)
    private String cgbflts;
    /**
     * 渠道开通日期
     */
    private Date qdktrq;
    /**
     * 渠道停用日期
     */
    private Date qdtyrq;
    /**
     * 渠道销户日期
     */
    private Date qdxhrq;
    /**
     * 渠道状态 01 启用  02 停用  03 已销户
     */
    @Length(max = 2)
    private String qdzt;
    /**
     * 备注
     */
    @Length(max = 255)
    private String beizhu;
    /**
     * 最后操作人账号
     */
    @Length(max = 20)
    private String zhczrzh;
    /**
     * 最后操作人姓名
     */
    @Length(max = 120)
    private String zhczrxm;
    /**
     * 最后操作日期
     */
    private Date zhczrq;
    /**
     * 最后操作渠道ID（FROPERQDXXB表主键）
     */
    @Length(max = 5)
    private String zhczqdid;
    /**
     * 渠道接入令牌
     */
    @Length(max = 100)
    private String token;
    /**
     * 单日访问上限
     */
    private BigDecimal drfwsx;
    /**
     * 单日业务量上限
     */
    private BigDecimal drywlsx;
    /**
     * 停止接入条件
     */
    @Length(max = 100)
    private String tzjrtj;
    /**
     * 脱敏方式
     */
    @Length(max = 50)
    private String tmfs;
    
    private List<String> tmfsids;
    
    public String getQdid(){
        return qdid;
    }
    public void setQdid(String qdid){
        this.qdid = qdid;
    }
    public String getQdmc(){
        return qdmc;
    }
    public void setQdmc(String qdmc){
        this.qdmc = qdmc;
    }
    public String getQdsm(){
        return qdsm;
    }
    public void setQdsm(String qdsm){
        this.qdsm = qdsm;
    }
    public String getQdjrip(){
        return qdjrip;
    }
    public void setQdjrip(String qdjrip){
        this.qdjrip = qdjrip;
    }
    public String getQdjrdk(){
        return qdjrdk;
    }
    public void setQdjrdk(String qdjrdk){
        this.qdjrdk = qdjrdk;
    }
    public String getQdjrzh(){
        return qdjrzh;
    }
    public void setQdjrzh(String qdjrzh){
        this.qdjrzh = qdjrzh;
    }
    public String getQdjrmm(){
        return qdjrmm;
    }
    public void setQdjrmm(String qdjrmm){
        this.qdjrmm = qdjrmm;
    }
    public String getQdlxr(){
        return qdlxr;
    }
    public void setQdlxr(String qdlxr){
        this.qdlxr = qdlxr;
    }
    public String getZjlx(){
        return zjlx;
    }
    public void setZjlx(String zjlx){
        this.zjlx = zjlx;
    }
    public String getZjhm(){
        return zjhm;
    }
    public void setZjhm(String zjhm){
        this.zjhm = zjhm;
    }
    public String getLxdh(){
        return lxdh;
    }
    public void setLxdh(String lxdh){
        this.lxdh = lxdh;
    }
    public String getSjhm(){
        return sjhm;
    }
    public void setSjhm(String sjhm){
        this.sjhm = sjhm;
    }
    public String getYxdz(){
        return yxdz;
    }
    public void setYxdz(String yxdz){
        this.yxdz = yxdz;
    }
    public String getQqhm(){
        return qqhm;
    }
    public void setQqhm(String qqhm){
        this.qqhm = qqhm;
    }
    public String getWxhm(){
        return wxhm;
    }
    public void setWxhm(String wxhm){
        this.wxhm = wxhm;
    }
    public String getLxdz(){
        return lxdz;
    }
    public void setLxdz(String lxdz){
        this.lxdz = lxdz;
    }
    public String getYzbm(){
        return yzbm;
    }
    public void setYzbm(String yzbm){
        this.yzbm = yzbm;
    }
    public BigDecimal getZdbfl(){
        return zdbfl;
    }
    public void setZdbfl(BigDecimal zdbfl){
        this.zdbfl = zdbfl;
    }
    public String getCgbflts(){
        return cgbflts;
    }
    public void setCgbflts(String cgbflts){
        this.cgbflts = cgbflts;
    }
    public Date getQdktrq(){
        return qdktrq;
    }
    public void setQdktrq(Date qdktrq){
        this.qdktrq = qdktrq;
    }
    public Date getQdtyrq(){
        return qdtyrq;
    }
    public void setQdtyrq(Date qdtyrq){
        this.qdtyrq = qdtyrq;
    }
    public Date getQdxhrq(){
        return qdxhrq;
    }
    public void setQdxhrq(Date qdxhrq){
        this.qdxhrq = qdxhrq;
    }
    public String getQdzt(){
        return qdzt;
    }
    public void setQdzt(String qdzt){
        this.qdzt = qdzt;
    }
    public String getBeizhu(){
        return beizhu;
    }
    public void setBeizhu(String beizhu){
        this.beizhu = beizhu;
    }
    public String getZhczrzh(){
        return zhczrzh;
    }
    public void setZhczrzh(String zhczrzh){
        this.zhczrzh = zhczrzh;
    }
    public String getZhczrxm(){
        return zhczrxm;
    }
    public void setZhczrxm(String zhczrxm){
        this.zhczrxm = zhczrxm;
    }
    public Date getZhczrq(){
        return zhczrq;
    }
    public void setZhczrq(Date zhczrq){
        this.zhczrq = zhczrq;
    }
    public String getZhczqdid(){
        return zhczqdid;
    }
    public void setZhczqdid(String zhczqdid){
        this.zhczqdid = zhczqdid;
    }
    public String getToken(){
        return token;
    }
    public void setToken(String token){
        this.token = token;
    }
    public BigDecimal getDrfwsx(){
        return drfwsx;
    }
    public void setDrfwsx(BigDecimal drfwsx){
        this.drfwsx = drfwsx;
    }
    public BigDecimal getDrywlsx(){
        return drywlsx;
    }
    public void setDrywlsx(BigDecimal drywlsx){
        this.drywlsx = drywlsx;
    }
    public String getTzjrtj(){
        return tzjrtj;
    }
    public void setTzjrtj(String tzjrtj){
        this.tzjrtj = tzjrtj;
    }
	public String getTmfs() {
		return tmfs;
	}
	public void setTmfs(String tmfs) {
		this.tmfs = tmfs;
	}
	public List<String> getTmfsids() {
		return tmfsids;
	}
	public void setTmfsids(List<String> tmfsids) {
		this.tmfsids = tmfsids;
	}
    
    
}
