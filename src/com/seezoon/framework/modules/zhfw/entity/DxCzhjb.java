package com.seezoon.framework.modules.zhfw.entity;

import com.seezoon.framework.common.entity.BaseEntity;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
/**
 * 短信操作痕迹表
 * Copyright &copy; 2018 powered by huangdf, All rights reserved.
 * @author hdf 2018-9-12 18:31:46
 */
public class DxCzhjb extends BaseEntity<String> {

   private static final long serialVersionUID = 1L;
    /**
     * 发生流水号
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
     * 业务类别（char_list表type为DXYWFL）
     */
    @Length(max = 20)
    private String ywlb;
    /**
     * 对应业务批次号（对应业务系统的workno）
     */
    @Length(max = 20)
    private String xgywpch;
    /**
     * 备注
     */
    @Length(max = 200)
    private String beizhu;
    /**
     * 操作人账号
     */
    @Length(max = 20)
    private String czrzh;
    /**
     * 操作人姓名
     */
    @Length(max = 20)
    private String czrxm;
    /**
     * 操作日期
     */
    private Timestamp czrq;
    /**
     * 业务人数（未开通人数+无效人数+发送人数）
     */
    private BigDecimal ywrs;
    /**
     * 未开通短信人数
     */
    private BigDecimal wktdxrs;
    /**
     * 手机号码无效人数
     */
    private BigDecimal sjhmwxrs;
    /**
     * 短信发送人数
     */
    private BigDecimal dxfsrs;
    /**
     * 操作渠道ID（FROPERQDXXB表主键）
     */
    @Length(max = 5)
    private String czqdid;
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
    public String getYwlb(){
        return ywlb;
    }
    public void setYwlb(String ywlb){
        this.ywlb = ywlb;
    }
    public String getXgywpch(){
        return xgywpch;
    }
    public void setXgywpch(String xgywpch){
        this.xgywpch = xgywpch;
    }
    public String getBeizhu(){
        return beizhu;
    }
    public void setBeizhu(String beizhu){
        this.beizhu = beizhu;
    }
    public String getCzrzh(){
        return czrzh;
    }
    public void setCzrzh(String czrzh){
        this.czrzh = czrzh;
    }
    public String getCzrxm(){
        return czrxm;
    }
    public void setCzrxm(String czrxm){
        this.czrxm = czrxm;
    }

    public Timestamp getCzrq() {
        return czrq;
    }

    public void setCzrq(Timestamp czrq) {
        this.czrq = czrq;
    }

    public BigDecimal getYwrs(){
        return ywrs;
    }
    public void setYwrs(BigDecimal ywrs){
        this.ywrs = ywrs;
    }
    public BigDecimal getWktdxrs(){
        return wktdxrs;
    }
    public void setWktdxrs(BigDecimal wktdxrs){
        this.wktdxrs = wktdxrs;
    }
    public BigDecimal getSjhmwxrs(){
        return sjhmwxrs;
    }
    public void setSjhmwxrs(BigDecimal sjhmwxrs){
        this.sjhmwxrs = sjhmwxrs;
    }
    public BigDecimal getDxfsrs(){
        return dxfsrs;
    }
    public void setDxfsrs(BigDecimal dxfsrs){
        this.dxfsrs = dxfsrs;
    }
    public String getCzqdid(){
        return czqdid;
    }
    public void setCzqdid(String czqdid){
        this.czqdid = czqdid;
    }
}