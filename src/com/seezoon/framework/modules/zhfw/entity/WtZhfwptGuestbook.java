package com.seezoon.framework.modules.zhfw.entity;

import com.seezoon.framework.common.entity.BaseEntity;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
/**
 * 综合服务平台留言管理
 *  2018-8-12 17:34:25
 */
public class WtZhfwptGuestbook extends BaseEntity<String> {

   private static final long serialVersionUID = 1L;
    /**
     * 留言id
     */
    @NotNull
    @Length(min = 1, max = 10)
    private String guestbookid;
    /**
     * 留言审核状态
     */
    @Length(max = 2)
    private String shzt;
    /**
     * 留言用户账户
     */
    @Length(max = 10)
    private String userid;
    /**
     * 留言用户姓名
     */
    @Length(max = 30)
    private String username;
    /**
     * 留言用户ip
     */
    @Length(max = 30)
    private String addip;
    /**
     * 留言标题
     */
    @Length(max = 255)
    private String title;
    /**
     * 留言内容
     */
    @Length(max = 65535)
    private String content;
    /**
     * 留言时间
     */
    private Date addtime;
    /**
     * 留言回复内容
     */
    @Length(max = 65535)
    private String recontent;
    /**
     * 留言回复时间
     */
    private Date retime;
    /**
     * 留言相关渠道
     */
    @Length(max = 255)
    private String qdgxid;
    /**
     * 回复状态
     */
    @Length(max = 2)
    private String hfzt;
    /**
     * 回复录入账号
     */
    @Length(max = 10)
    private String hflrrzh;
    /**
     * 栏目所属id
     */
    @Length(max = 10)
    private String classid;
    public String getGuestbookid(){
        return guestbookid;
    }
    public void setGuestbookid(String guestbookid){
        this.guestbookid = guestbookid;
    }
    public String getShzt(){
        return shzt;
    }
    public void setShzt(String shzt){
        this.shzt = shzt;
    }
    public String getUserid(){
        return userid;
    }
    public void setUserid(String userid){
        this.userid = userid;
    }
    public String getUsername(){
        return username;
    }
    public void setUsername(String username){
        this.username = username;
    }
    public String getAddip(){
        return addip;
    }
    public void setAddip(String addip){
        this.addip = addip;
    }
    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getContent(){
        return content;
    }
    public void setContent(String content){
        this.content = content;
    }
    public Date getAddtime(){
        return addtime;
    }
    public void setAddtime(Date addtime){
        this.addtime = addtime;
    }
    public String getRecontent(){
        return recontent;
    }
    public void setRecontent(String recontent){
        this.recontent = recontent;
    }
    public Date getRetime(){
        return retime;
    }
    public void setRetime(Date retime){
        this.retime = retime;
    }
    public String getQdgxid(){
        return qdgxid;
    }
    public void setQdgxid(String qdgxid){
        this.qdgxid = qdgxid;
    }
    public String getHfzt(){
        return hfzt;
    }
    public void setHfzt(String hfzt){
        this.hfzt = hfzt;
    }
    public String getHflrrzh(){
        return hflrrzh;
    }
    public void setHflrrzh(String hflrrzh){
        this.hflrrzh = hflrrzh;
    }
    public String getClassid(){
        return classid;
    }
    public void setClassid(String classid){
        this.classid = classid;
    }
}
