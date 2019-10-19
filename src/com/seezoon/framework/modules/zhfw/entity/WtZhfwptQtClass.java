package com.seezoon.framework.modules.zhfw.entity;

import com.seezoon.framework.common.entity.BaseEntity;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
/**
 * 综合服务平台前台栏目
 *  2018-8-12 17:36:20
 */
public class WtZhfwptQtClass extends BaseEntity<String> {

   private static final long serialVersionUID = 1L;
    /**
     * 前台栏目id
     */
    @NotNull
    @Length(min = 1, max = 10)
    private String classid;
    /**
     * 一级栏目名
     */
    @Length(max = 200)
    private String classname;
    /**
     * 二级栏目名
     */
    @Length(max = 200)
    private String classname2;
    /**
     * 上级栏目id
     */
    @Length(max = 10)
    private String topid;
    /**
     * 栏目排序
     */
    @Length(max = 10)
    private String num;
    /**
     * 是否为导航栏显示
     */
    @Length(max = 10)
    private String ismenu;
    /**
     * 是否为首页显示
     */
    @Length(max = 10)
    private String isindex;
    /**
     * 栏目展示渠道
     */
    @Length(max = 255)
    private String qdgxid;
    /**
     * 栏目说明
     */
    @Length(max = 200)
    private String lmsm;
    public String getClassid(){
        return classid;
    }
    public void setClassid(String classid){
        this.classid = classid;
    }
    public String getClassname(){
        return classname;
    }
    public void setClassname(String classname){
        this.classname = classname;
    }
    public String getClassname2(){
        return classname2;
    }
    public void setClassname2(String classname2){
        this.classname2 = classname2;
    }
    public String getTopid(){
        return topid;
    }
    public void setTopid(String topid){
        this.topid = topid;
    }
    public String getNum(){
        return num;
    }
    public void setNum(String num){
        this.num = num;
    }
    public String getIsmenu(){
        return ismenu;
    }
    public void setIsmenu(String ismenu){
        this.ismenu = ismenu;
    }
    public String getIsindex(){
        return isindex;
    }
    public void setIsindex(String isindex){
        this.isindex = isindex;
    }
    public String getQdgxid(){
        return qdgxid;
    }
    public void setQdgxid(String qdgxid){
        this.qdgxid = qdgxid;
    }
    public String getLmsm(){
        return lmsm;
    }
    public void setLmsm(String lmsm){
        this.lmsm = lmsm;
    }
}
