package com.seezoon.framework.modules.zhfw.entity;

import com.seezoon.framework.common.entity.BaseEntity;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.sql.Timestamp;
import java.util.Date;
/**
 * 互动交流历史表
 *  2018-8-12 18:14:05
 */
public class WtHdjlxxls extends BaseEntity<String> {

   private static final long serialVersionUID = 1L;
    /**
     * 业务流水号
     */
    @NotNull
    @Length(min = 1, max = 20)
    private String ywlsh;
    /**
     * 相关业务流水号（关联互动交流信息表主键）
     */
    @Length(max = 20)
    private String xgywlsh;
    /**
     * 发言人类型（0用户/1中心人员）
     */
    @Length(max = 1)
    private String fyrlx;
    /**
     * 发言人姓名
     */
    @Length(max = 50)
    private String fyrxm;
    /**
     * 发言人内容
     */
    @Length(max = 500)
    private String fyrnr;
    /**
     * 发言时间
     */
    private Timestamp fysj;
    public String getYwlsh(){
        return ywlsh;
    }
    public void setYwlsh(String ywlsh){
        this.ywlsh = ywlsh;
    }
    public String getXgywlsh(){
        return xgywlsh;
    }
    public void setXgywlsh(String xgywlsh){
        this.xgywlsh = xgywlsh;
    }
    public String getFyrlx(){
        return fyrlx;
    }
    public void setFyrlx(String fyrlx){
        this.fyrlx = fyrlx;
    }
    public String getFyrxm(){
        return fyrxm;
    }
    public void setFyrxm(String fyrxm){
        this.fyrxm = fyrxm;
    }
    public String getFyrnr(){
        return fyrnr;
    }
    public void setFyrnr(String fyrnr){
        this.fyrnr = fyrnr;
    }
    public Date getFysj(){
        return fysj;
    }

    public void setFysj(Timestamp fysj) {
        this.fysj = fysj;
    }
}
