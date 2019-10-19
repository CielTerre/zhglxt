package com.seezoon.framework.modules.zhfw.entity;

import com.seezoon.framework.common.entity.BaseEntity;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import java.math.BigDecimal;
import java.util.Date;
/**
 * 综合服务平台日统计信息
 *  2018-8-7 21:30:19
 */
public class WtSRtjxx extends BaseEntity<String> {

   private static final long serialVersionUID = 1L;
    /**
     * 业务流水号,主键
     */
    @NotNull
    @Length(min = 1, max = 20)
    private String ywlsh;
    /**
     * 渠道
     */
    @Length(max = 10)
    private String qdid;
    /**
     * 统计日期
     */
    private Date tjrq;
    /**
     * 统计指标
     */
    @Length(max = 20)
    private String tjzb;
    /**
     * 统计指标明细
     */
    @Length(max = 20)
    private String tjzbmx;
    /**
     * 统计值
     */
    private BigDecimal tjz;
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
    public Date getTjrq(){
        return tjrq;
    }
    public void setTjrq(Date tjrq){
        this.tjrq = tjrq;
    }
    public String getTjzb(){
        return tjzb;
    }
    public void setTjzb(String tjzb){
        this.tjzb = tjzb;
    }
    public String getTjzbmx(){
        return tjzbmx;
    }
    public void setTjzbmx(String tjzbmx){
        this.tjzbmx = tjzbmx;
    }
    public BigDecimal getTjz(){
        return tjz;
    }
    public void setTjz(BigDecimal tjz){
        this.tjz = tjz;
    }
}
