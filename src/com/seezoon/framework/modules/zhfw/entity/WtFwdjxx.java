package com.seezoon.framework.modules.zhfw.entity;

import com.seezoon.framework.common.entity.BaseEntity;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
/**
 * 网厅相关（核心服务登记信息）
 *  2018-7-16 17:40:18
 */
public class WtFwdjxx extends BaseEntity<String> {

   private static final long serialVersionUID = 1L;
    /**
     * 服务ID
     */
    private String fwid;
    /**
     * 服务名称
     */
    @Length(max = 120)
    private String fwmc;
    /**
     * 服务说明
     */
    @Length(max = 255)
    private String fwsm;
    /**
     * 服务输入参数说明
     */
    @Length(max = 255)
    private String fwsrcssm;
    /**
     * 服务输出说明
     */
    @Length(max = 600)
    private String fwsccssm;
    /**
     * 服务状态（01启用，02停用，03销户）
     */
    @Length(max = 2)
    private String fwzt;
    /**
     * 服务登记日期
     */
    private Date fwdjrq;
    /**
     * 服务停用日期
     */
    private Date fwtyrq;
    /**
     * 服务销户日期
     */
    private Date fwxhrq;
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
     * 服务分类（从服务分类表中取WT_Fwfl）
     */
    @Length(max = 6)
    private String fwfl;
    /**
     * 服务性质(0查询类1办理类)
     */
    @Length(max = 1)
    private String fwxz;
    /**
     * 是否资金变化(0不变动1变化)
     */
    @Length(max = 1)
    private String zjbd;
    /**
     * 每月服务受理时间(如1--L-1,1-15)
     */
    @Length(max = 50)
    private String fwsj;
    /**
     * 是否仅工作日受理(0否1是)
     */
    @Length(max = 1)
    private String jgzrsl;
    /**
     * 服务受理时间段
     */
    @Length(max = 50)
    private String fwslsjd;

    private String flmc;

    public String getFwid(){
        return fwid;
    }
    public void setFwid(String fwid){
        this.fwid = fwid;
    }
    public String getFwmc(){
        return fwmc;
    }
    public void setFwmc(String fwmc){
        this.fwmc = fwmc;
    }
    public String getFwsm(){
        return fwsm;
    }
    public void setFwsm(String fwsm){
        this.fwsm = fwsm;
    }
    public String getFwsrcssm(){
        return fwsrcssm;
    }
    public void setFwsrcssm(String fwsrcssm){
        this.fwsrcssm = fwsrcssm;
    }
    public String getFwsccssm(){
        return fwsccssm;
    }
    public void setFwsccssm(String fwsccssm){
        this.fwsccssm = fwsccssm;
    }
    public String getFwzt(){
        return fwzt;
    }
    public void setFwzt(String fwzt){
        this.fwzt = fwzt;
    }
    public Date getFwdjrq(){
        return fwdjrq;
    }
    public void setFwdjrq(Date fwdjrq){
        this.fwdjrq = fwdjrq;
    }
    public Date getFwtyrq(){
        return fwtyrq;
    }
    public void setFwtyrq(Date fwtyrq){
        this.fwtyrq = fwtyrq;
    }
    public Date getFwxhrq(){
        return fwxhrq;
    }
    public void setFwxhrq(Date fwxhrq){
        this.fwxhrq = fwxhrq;
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
    public String getFwfl(){
        return fwfl;
    }
    public void setFwfl(String fwfl){
        this.fwfl = fwfl;
    }
    public String getFwxz(){
        return fwxz;
    }
    public void setFwxz(String fwxz){
        this.fwxz = fwxz;
    }
    public String getZjbd(){
        return zjbd;
    }
    public void setZjbd(String zjbd){
        this.zjbd = zjbd;
    }
    public String getFwsj(){
        return fwsj;
    }
    public void setFwsj(String fwsj){
        this.fwsj = fwsj;
    }
    public String getJgzrsl(){
        return jgzrsl;
    }
    public void setJgzrsl(String jgzrsl){
        this.jgzrsl = jgzrsl;
    }
    public String getFwslsjd(){
        return fwslsjd;
    }
    public void setFwslsjd(String fwslsjd){
        this.fwslsjd = fwslsjd;
    }

    public String getFlmc() {
        return flmc;
    }

    public void setFlmc(String flmc) {
        this.flmc = flmc;
    }
}
