package com.seezoon.framework.modules.zhfw.entity;

import com.seezoon.framework.common.entity.BaseEntity;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.sql.Timestamp;
import java.util.Date;
/**
 * 短信发送痕迹表
 * Copyright &copy; 2018 powered by huangdf, All rights reserved.
 * @author hdf 2018-9-12 16:52:57
 */
public class DxFshjb extends BaseEntity<String> {

   private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    @NotNull
    @Length(min = 1, max = 20)
    private String ywlsh;
    /**
     * 代办点
     */
    @Length(max = 2)
    private String bldbd;
    /**
     * 承办网点
     */
    @Length(max = 4)
    private String blywwd;
    /**
     * 操作workid（dx_czhjb的主键）
     */
    @Length(max = 20)
    private String czywlsh;
    /**
     * 对应的业务批次号（对应业务系统的workno）
     */
    @Length(max = 20)
    private String xgywpch;
    /**
     * 单位账号（zunitinfo表主键）
     */
    @Length(max = 20)
    private String dwzh;
    /**
     * 个人账号（zpersonalinfo表主键）
     */
    @Length(max = 20)
    private String grzh;
    /**
     * 业务类别（char_list表type为DXYWFL）
     */
    @Length(max = 20)
    private String ywlb;
    /**
     * 对应业务的流水号（对应业务系统的workid）
     */
    @Length(max = 20)
    private String xgywlsh;
    /**
     * 手机号码
     */
    @Length(max = 20)
    private String sjhm;
    /**
     * 短信内容
     */
    @Length(max = 400)
    private String dxnr;
    /**
     * 发送状态(0未发送，1短信发送中，C发送成功，F发送失败)
     */
    @Length(max = 2)
    private String fszt;
    /**
     * 失败原因
     */
    @Length(max = 200)
    private String sbyy;
    /**
     * 发送人账号
     */
    @Length(max = 20)
    private String fsrzh;
    /**
     * 发送人姓名
     */
    @Length(max = 20)
    private String fsrxm;
    /**
     * 发送日期
     */
    private Timestamp fsrq;
    /**
     * 对方唯一标识
     */
    @Length(max = 36)
    private String dfywlsh;
    /**
     * 手机所属区域
     */
    @Length(max = 1)
    private String sjssqy;
    /**
     * 发送渠道ID（FROPERQDXXB表主键）
     */
    @Length(max = 5)
    private String fsqdid;
    public String getYwlsh(){
        return ywlsh;
    }
    public void setYwlsh(String ywlsh){
        this.ywlsh = ywlsh;
    }
    public String getBldbd(){
        return bldbd;
    }
    public void setBldbd(String bldbd){
        this.bldbd = bldbd;
    }
    public String getBlywwd(){
        return blywwd;
    }
    public void setBlywwd(String blywwd){
        this.blywwd = blywwd;
    }
    public String getCzywlsh(){
        return czywlsh;
    }
    public void setCzywlsh(String czywlsh){
        this.czywlsh = czywlsh;
    }
    public String getXgywpch(){
        return xgywpch;
    }
    public void setXgywpch(String xgywpch){
        this.xgywpch = xgywpch;
    }
    public String getDwzh(){
        return dwzh;
    }
    public void setDwzh(String dwzh){
        this.dwzh = dwzh;
    }
    public String getGrzh(){
        return grzh;
    }
    public void setGrzh(String grzh){
        this.grzh = grzh;
    }
    public String getYwlb(){
        return ywlb;
    }
    public void setYwlb(String ywlb){
        this.ywlb = ywlb;
    }
    public String getXgywlsh(){
        return xgywlsh;
    }
    public void setXgywlsh(String xgywlsh){
        this.xgywlsh = xgywlsh;
    }
    public String getSjhm(){
        return sjhm;
    }
    public void setSjhm(String sjhm){
        this.sjhm = sjhm;
    }
    public String getDxnr(){
        return dxnr;
    }
    public void setDxnr(String dxnr){
        this.dxnr = dxnr;
    }
    public String getFszt(){
        return fszt;
    }
    public void setFszt(String fszt){
        this.fszt = fszt;
    }
    public String getSbyy(){
        return sbyy;
    }
    public void setSbyy(String sbyy){
        this.sbyy = sbyy;
    }
    public String getFsrzh(){
        return fsrzh;
    }
    public void setFsrzh(String fsrzh){
        this.fsrzh = fsrzh;
    }
    public String getFsrxm(){
        return fsrxm;
    }
    public void setFsrxm(String fsrxm){
        this.fsrxm = fsrxm;
    }

    public Timestamp getFsrq() {
        return fsrq;
    }

    public void setFsrq(Timestamp fsrq) {
        this.fsrq = fsrq;
    }

    public String getDfywlsh(){
        return dfywlsh;
    }
    public void setDfywlsh(String dfywlsh){
        this.dfywlsh = dfywlsh;
    }
    public String getSjssqy(){
        return sjssqy;
    }
    public void setSjssqy(String sjssqy){
        this.sjssqy = sjssqy;
    }
    public String getFsqdid(){
        return fsqdid;
    }
    public void setFsqdid(String fsqdid){
        this.fsqdid = fsqdid;
    }
}