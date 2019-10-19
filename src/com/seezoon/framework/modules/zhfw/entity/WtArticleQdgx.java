package com.seezoon.framework.modules.zhfw.entity;

import com.seezoon.framework.common.entity.BaseEntity;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
/**
 * 文章渠道关系表
 *  2018-8-12 17:28:15
 */
public class WtArticleQdgx extends BaseEntity<String> {

   private static final long serialVersionUID = 1L;
    /**
     * 流水号，主键
     */
    @NotNull
    @Length(min = 1, max = 10)
    private String ywlsh;
    /**
     * 渠道id
     */
    @Length(max = 6)
    private String qdid;
    /**
     * 相关id
     */
    @Length(max = 10)
    private String xgid;
    /**
     * 点击数
     */
    private Integer hits;
    /**
     * 相关类型(1文章2留言3知识库)
     */
    @Length(max = 2)
    private String refType;
    /**
     * 最后访问时间
     */
    private Date zhfwrq;
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
    public String getXgid(){
        return xgid;
    }
    public void setXgid(String xgid){
        this.xgid = xgid;
    }
    public Integer getHits(){
        return hits;
    }
    public void setHits(Integer hits){
        this.hits = hits;
    }
    public String getRefType(){
        return refType;
    }
    public void setRefType(String refType){
        this.refType = refType;
    }
    public Date getZhfwrq(){
        return zhfwrq;
    }
    public void setZhfwrq(Date zhfwrq){
        this.zhfwrq = zhfwrq;
    }
}
