package com.seezoon.framework.modules.zhfw.entity;

import com.seezoon.framework.common.entity.BaseEntity;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
/**
 * 接入管理
 *  2018-7-16 20:25:29
 */
public class WtQdfwjrxx extends BaseEntity<String> {

   private static final long serialVersionUID = 1L;
    /**
     * 业务流水号
     */
    @NotNull
    @Length(min = 1, max = 20)
    private String ywlsh;
    /**
     * 渠道ID
     */
    @Length(max = 20)
    private String qdid;
    /**
     * 服务ID
     */
    @Length(max = 20)
    private String fwid;
    /**
     * 服务状态（01启用、02停用、03注销）
     */
    @Length(max = 2)
    private String fwzt;
    /**
     * 启用日期
     */
    private Date qyrq;
    /**
     * 停用日期
     */
    private Date tyrq;
    /**
     * 注销日期
     */
    private Date zxrq;
    /**
     * 最后操作人账号
     */
    @Length(max = 20)
    private String zhczrzh;
    /**
     * 最后操作人姓名
     */
    @Length(max = 20)
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
     * 单笔上限
     */
    private BigDecimal dbsx;
    /**
     * 单笔下限
     */
    private BigDecimal dbxx;
    /**
     * 单日上限
     */
    private BigDecimal drsx;
    /**
     * 预留，变更状态（01启用、02停用、03注销）
     */
    @Length(max = 2)
    private String bgzt;
    /**
     * 申请状态（S申请中E已审批）
     */
    @Length(max = 2)
    private String sqzt;

    /**
     * 单日业务量上限
     */
    private String drywsx;
    
    /**
     * 脱敏方式
     */
    @Length(max = 50)
    private String tmfs;
    
    private List<String> tmfsids;

    public String getDrywsx() {
        return drywsx;
    }

    public void setDrywsx(String drywsx) {
        this.drywsx = drywsx;
    }

    public String getYwlsh(){
        return ywlsh;
    }
    public void setYwlsh(String ywlsh){
        this.ywlsh = ywlsh;
    }
    public String getQdid(){
        return qdid;
    }
    public void setQdid(String qdid){
        this.qdid = qdid;
    }
    public String getFwid(){
        return fwid;
    }
    public void setFwid(String fwid){
        this.fwid = fwid;
    }
    public String getFwzt(){
        return fwzt;
    }
    public void setFwzt(String fwzt){
        this.fwzt = fwzt;
    }
    public Date getQyrq(){
        return qyrq;
    }
    public void setQyrq(Date qyrq){
        this.qyrq = qyrq;
    }
    public Date getTyrq(){
        return tyrq;
    }
    public void setTyrq(Date tyrq){
        this.tyrq = tyrq;
    }
    public Date getZxrq(){
        return zxrq;
    }
    public void setZxrq(Date zxrq){
        this.zxrq = zxrq;
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
    public BigDecimal getDbsx(){
        return dbsx;
    }
    public void setDbsx(BigDecimal dbsx){
        this.dbsx = dbsx;
    }
    public BigDecimal getDbxx(){
        return dbxx;
    }
    public void setDbxx(BigDecimal dbxx){
        this.dbxx = dbxx;
    }
    public BigDecimal getDrsx(){
        return drsx;
    }
    public void setDrsx(BigDecimal drsx){
        this.drsx = drsx;
    }
    public String getBgzt(){
        return bgzt;
    }
    public void setBgzt(String bgzt){
        this.bgzt = bgzt;
    }
    public String getSqzt(){
        return sqzt;
    }
    public void setSqzt(String sqzt){
        this.sqzt = sqzt;
    }

    /**
     * 非数据库字段
     */
    //服务说明(名称)
    private String fwsm;
    //分类名称
    private String flmc;

    //服务性质
    private String fwxz;

    //渠道名称
    private String qdmc;


    //服务分类
    private String fwfl;


    public String getFwsm() {
        return fwsm;
    }

    public void setFwsm(String fwsm) {
        this.fwsm = fwsm;
    }

    public String getFlmc() {
        return flmc;
    }

    public void setFlmc(String flmc) {
        this.flmc = flmc;
    }

    public String getFwxz() {
        return fwxz;
    }

    public void setFwxz(String fwxz) {
        this.fwxz = fwxz;
    }

    public String getQdmc() {
        return qdmc;
    }

    public void setQdmc(String qdmc) {
        this.qdmc = qdmc;
    }

    public String getFwfl() {
        return fwfl;
    }

    public void setFwfl(String fwfl) {
        this.fwfl = fwfl;
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
