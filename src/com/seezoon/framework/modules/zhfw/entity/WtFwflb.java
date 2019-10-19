package com.seezoon.framework.modules.zhfw.entity;

import com.seezoon.framework.common.entity.BaseEntity;
import javax.validation.constraints.NotNull;

import com.seezoon.framework.common.entity.TreeEntity;
import org.hibernate.validator.constraints.Length;
/**
 * 服务分类表
 *  2018-7-19 10:04:24
 */
public class WtFwflb extends TreeEntity<String> {

   private static final long serialVersionUID = 1L;
    /**
     * 分类ID
     */
    @NotNull
    @Length(min = 1, max = 6)
    private String fwid;
    /**
     * 分类名称
     */
    @Length(max = 20)
    private String flmc;
    /**
     * 分类次序
     */
    private Integer flcx;
    /**
     * 是否统计(0不统计1统计)
     */
    @Length(max = 1)
    private String sftj;
    public String getFwid(){
        return fwid;
    }
    public void setFwid(String fwid){
        this.fwid = fwid;
    }
    public String getFlmc(){
        return flmc;
    }
    public void setFlmc(String flmc){
        this.flmc = flmc;
    }
    public Integer getFlcx(){
        return flcx;
    }
    public void setFlcx(Integer flcx){
        this.flcx = flcx;
    }
    public String getSftj(){
        return sftj;
    }
    public void setSftj(String sftj){
        this.sftj = sftj;
    }
}
