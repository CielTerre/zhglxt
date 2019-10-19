package com.seezoon.framework.modules.zhfw.entity;

import com.seezoon.framework.common.entity.BaseEntity;
import javax.validation.constraints.NotNull;

import com.seezoon.framework.common.entity.TreeEntity;
import org.hibernate.validator.constraints.Length;
/**
 * 知识库分类
 *  2018-8-12 17:30:38
 */
public class WtZskfl extends TreeEntity<String> {

   private static final long serialVersionUID = 1L;
    /**
     * 父节点id
     */
    @Length(max = 10)
    private String parentId;
    /**
     * 父节点id多级
     */
    @Length(max = 200)
    private String parentIds;
    /**
     * 分类名称
     */
    @Length(max = 50)
    private String flmc;
    /**
     * 分类次序号
     */
    private Integer flcx;
    /**
     * 知识类型(01办事指南02常见问题)
     */
    @Length(max = 2)
    private String zslx;
    public String getParentId(){
        return parentId;
    }
    public void setParentId(String parentId){
        this.parentId = parentId;
    }
    public String getParentIds(){
        return parentIds;
    }
    public void setParentIds(String parentIds){
        this.parentIds = parentIds;
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
    public String getZslx(){
        return zslx;
    }
    public void setZslx(String zslx){
        this.zslx = zslx;
    }
}
