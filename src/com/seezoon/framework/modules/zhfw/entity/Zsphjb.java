package com.seezoon.framework.modules.zhfw.entity;

import com.seezoon.framework.common.entity.BaseEntity;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import java.math.BigDecimal;
import java.util.Date;
/**
 * 审批痕迹表
 *  2018-8-12 18:21:44
 */
public class Zsphjb extends BaseEntity<String> {

   private static final long serialVersionUID = 1L;
    /**
     * WORKID 业务流水号
     */
    @NotNull
    @Length(min = 1, max = 20)
    private String ywlsh;
    /**
     * WORKNO 业务批次（同一批次的业务则都一致）
     */
    @Length(max = 20)
    private String sppch;
    /**
     * SQBH 相关申请号（与实际业务的申请编号一致）
     */
    @Length(max = 20)
    private String ywbh;
    /**
     * OFAGENT 业务办理代办点
     */
    @Length(max = 2)
    private String bldbd;
    /**
     * CBAGENT 业务办理承办网点
     */
    @Length(max = 4)
    private String blywwd;
    /**
     * 业务所属代办点(与单位所属代办点一致)
     */
    @Length(max = 2)
    private String ywssdbd;
    /**
     * 相关业务所属网点
     */
    @Length(max = 4)
    private String ywsswd;
    /**
     * 会计年月（审批时的会计年月）
     */
    @Length(max = 6)
    private String kjny;
    /**
     * PNAME 申请人姓名(改为业务摘要) (后UNITNAME 删除)
     */
    @Length(max = 255)
    private String ywzy;
    /**
     * 业务类别代码
     */
    @Length(max = 20)
    private String ywlbdm;
    /**
     * 起始业务节点代码
     */
    @Length(max = 2)
    private String qsywjddm;
    /**
     * 起始业务节点名称
     */
    @Length(max = 30)
    private String qsywjdmc;
    /**
     * 下一业务节点代码
     */
    @Length(max = 2)
    private String xyywjddm;
    /**
     * 下一业务节点名称
     */
    @Length(max = 30)
    private String xyywjdmc;
    /**
     * 正、反（退单时以审批时的环节退回<比如顺序是135，退单则以531的退，而不是54321>，审批通过时顺序走）
     */
    @Length(max = 2)
    private String spfx;
    /**
     * 审批金额
     */
    private BigDecimal spje;
    /**
     * 审批期限
     */
    private Integer spqx;
    /**
     * 审批同意否(Y审批同意 N 审批不同意)
     */
    @Length(max = 1)
    private String sptyf;
    /**
     * 审批意见
     */
    @Length(max = 200)
    private String spyj;
    /**
     * 审批人账号
     */
    @Length(max = 20)
    private String sprzh;
    /**
     * 审批人姓名
     */
    @Length(max = 20)
    private String sprxm;
    /**
     * 审批日期
     */
    private Date sprq;
    /**
     * 审批渠道ID（FROPERQDXXB表主键）
     */
    @Length(max = 5)
    private String spqdid;
    public String getYwlsh(){
        return ywlsh;
    }
    public void setYwlsh(String ywlsh){
        this.ywlsh = ywlsh;
    }
    public String getSppch(){
        return sppch;
    }
    public void setSppch(String sppch){
        this.sppch = sppch;
    }
    public String getYwbh(){
        return ywbh;
    }
    public void setYwbh(String ywbh){
        this.ywbh = ywbh;
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
    public String getYwssdbd(){
        return ywssdbd;
    }
    public void setYwssdbd(String ywssdbd){
        this.ywssdbd = ywssdbd;
    }
    public String getYwsswd(){
        return ywsswd;
    }
    public void setYwsswd(String ywsswd){
        this.ywsswd = ywsswd;
    }
    public String getKjny(){
        return kjny;
    }
    public void setKjny(String kjny){
        this.kjny = kjny;
    }
    public String getYwzy(){
        return ywzy;
    }
    public void setYwzy(String ywzy){
        this.ywzy = ywzy;
    }
    public String getYwlbdm(){
        return ywlbdm;
    }
    public void setYwlbdm(String ywlbdm){
        this.ywlbdm = ywlbdm;
    }
    public String getQsywjddm(){
        return qsywjddm;
    }
    public void setQsywjddm(String qsywjddm){
        this.qsywjddm = qsywjddm;
    }
    public String getQsywjdmc(){
        return qsywjdmc;
    }
    public void setQsywjdmc(String qsywjdmc){
        this.qsywjdmc = qsywjdmc;
    }
    public String getXyywjddm(){
        return xyywjddm;
    }
    public void setXyywjddm(String xyywjddm){
        this.xyywjddm = xyywjddm;
    }
    public String getXyywjdmc(){
        return xyywjdmc;
    }
    public void setXyywjdmc(String xyywjdmc){
        this.xyywjdmc = xyywjdmc;
    }
    public String getSpfx(){
        return spfx;
    }
    public void setSpfx(String spfx){
        this.spfx = spfx;
    }
    public BigDecimal getSpje(){
        return spje;
    }
    public void setSpje(BigDecimal spje){
        this.spje = spje;
    }
    public Integer getSpqx(){
        return spqx;
    }
    public void setSpqx(Integer spqx){
        this.spqx = spqx;
    }
    public String getSptyf(){
        return sptyf;
    }
    public void setSptyf(String sptyf){
        this.sptyf = sptyf;
    }
    public String getSpyj(){
        return spyj;
    }
    public void setSpyj(String spyj){
        this.spyj = spyj;
    }
    public String getSprzh(){
        return sprzh;
    }
    public void setSprzh(String sprzh){
        this.sprzh = sprzh;
    }
    public String getSprxm(){
        return sprxm;
    }
    public void setSprxm(String sprxm){
        this.sprxm = sprxm;
    }
    public Date getSprq(){
        return sprq;
    }
    public void setSprq(Date sprq){
        this.sprq = sprq;
    }
    public String getSpqdid(){
        return spqdid;
    }
    public void setSpqdid(String spqdid){
        this.spqdid = spqdid;
    }
}
