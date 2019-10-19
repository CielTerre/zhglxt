package com.seezoon.framework.modules.zhfw.entity;

import com.seezoon.framework.common.entity.BaseEntity;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
/**
 * 知识库详细内容
 *  2018-8-12 17:32:14
 */
public class WtZskmx extends BaseEntity<String> {

   private static final long serialVersionUID = 1L;
    /**
     * 分类id,管理WT_ZSKFL
     */
    @Length(max = 10)
    private String flid;
    /**
     * 知识库类型(01办事指南02常见问题)
     */
    @Length(max = 2)
    private String zslx;
    /**
     * 分项名称
     */
    @Length(max = 255)
    private String fxmc;
    /**
     * 关键字
     */
    @Length(max = 100)
    private String gjz;
    /**
     * 知识内容
     */
    @Length(max = 65535)
    private String zsnr;
    /**
     * 录入人姓名
     */
    @Length(max = 50)
    private String llrxm;
    /**
     * 录入人账号
     */
    @Length(max = 20)
    private String llr;

    private String flmc;
    /**
     * 录入日期
     */
    private Date llrq;
    public String getFlid(){
        return flid;
    }
    public void setFlid(String flid){
        this.flid = flid;
    }
    public String getZslx(){
        return zslx;
    }
    public void setZslx(String zslx){
        this.zslx = zslx;
    }
    public String getFxmc(){
        return fxmc;
    }
    public void setFxmc(String fxmc){
        this.fxmc = fxmc;
    }
    public String getGjz(){
        return gjz;
    }
    public void setGjz(String gjz){
        this.gjz = gjz;
    }
    public String getZsnr(){
        return zsnr;
    }
    public void setZsnr(String zsnr){
        this.zsnr = zsnr;
    }
    public String getLlrxm(){
        return llrxm;
    }
    public void setLlrxm(String llrxm){
        this.llrxm = llrxm;
    }
    public String getLlr(){
        return llr;
    }
    public void setLlr(String llr){
        this.llr = llr;
    }
    public Date getLlrq(){
        return llrq;
    }
    public void setLlrq(Date llrq){
        this.llrq = llrq;
    }


    public String getFlmc() {
        return flmc;
    }

    public void setFlmc(String flmc) {
        this.flmc = flmc;
    }
}
