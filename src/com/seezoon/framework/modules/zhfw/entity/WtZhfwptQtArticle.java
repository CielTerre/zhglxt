package com.seezoon.framework.modules.zhfw.entity;

import com.seezoon.framework.common.entity.BaseEntity;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
/**
 * 综合服务平台前台文章信息
 *  2018-8-12 17:27:44
 */
public class WtZhfwptQtArticle extends BaseEntity<String> {

   private static final long serialVersionUID = 1L;
    /**
     * 文章id
     */
    @NotNull
    private String wzid;
    /**
     * 文章标题
     */
    @Length(max = 250)
    private String title;
    /**
     * 标题颜色
     */
    @Length(max = 50)
    private String titlefontcolor;
    /**
     * 文章作者
     */
    @Length(max = 50)
    private String author;
    /**
     * 文章来源
     */
    @Length(max = 50)
    private String copyfrom;
    /**
     * 所属栏目id
     */
    @Length(max = 10)
    private String classid;
    /**
     * 文章摘要
     */
    @Length(max = 65535)
    private String artdescription;
    /**
     * 文章内容
     */
    @Length(max = 65535)
    private String content;
    /**
     * 文章最后操作时间
     */
    private Date dateandtime;
    /**
     * 文章点击次数
     */
    private Integer hits;
    /**
     * 文章审核状态（01 是  02 否 00草稿）
     */
    @Length(max = 10)
    private String dqzt;
    /**
     * 是否置顶
     */
    @Length(max = 10)
    private String istop;
    /**
     * 是否推荐
     */
    @Length(max = 10)
    private String ishot;
    /**
     * 是否为图片
     */
    @Length(max = 10)
    private String isflash;
    /**
     * 图片链接
     */
    @Length(max = 200)
    private String images;
    /**
     * 添加文章用户id
     */
    @Length(max = 10)
    private String userid;
    /**
     * 添加文章用户名
     */
    @Length(max = 50)
    private String username;
    /**
     * 文章发布渠道
     */
    @Length(max = 255)
    private String qdgxid;
    /**
     * 文章发布时间
     */
    @Length(max = 20)
    private String wzfbsj;
    /**
     * 最后操作日期
     */
    private Date zhczrq;
    /**
     * 知识库id
     */
    @Length(max = 10)
    private String zskid;
    /**
     * 审核意见
     */
    @Length(max = 65535)
    private String shcontent;
    
    public String getWzid(){
        return wzid;
    }
    public void setWzid(String wzid){
        this.wzid = wzid;
    }
    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getTitlefontcolor(){
        return titlefontcolor;
    }
    public void setTitlefontcolor(String titlefontcolor){
        this.titlefontcolor = titlefontcolor;
    }
    public String getAuthor(){
        return author;
    }
    public void setAuthor(String author){
        this.author = author;
    }
    public String getCopyfrom(){
        return copyfrom;
    }
    public void setCopyfrom(String copyfrom){
        this.copyfrom = copyfrom;
    }
    public String getClassid(){
        return classid;
    }
    public void setClassid(String classid){
        this.classid = classid;
    }
    public String getArtdescription(){
        return artdescription;
    }
    public void setArtdescription(String artdescription){
        this.artdescription = artdescription;
    }
    public String getContent(){
        return content;
    }
    public void setContent(String content){
        this.content = content;
    }
    public Date getDateandtime(){
        return dateandtime;
    }
    public void setDateandtime(Date dateandtime){
        this.dateandtime = dateandtime;
    }
    public Integer getHits(){
        return hits;
    }
    public void setHits(Integer hits){
        this.hits = hits;
    }
    public String getDqzt(){
        return dqzt;
    }
    public void setDqzt(String dqzt){
        this.dqzt = dqzt;
    }
    public String getIstop(){
        return istop;
    }
    public void setIstop(String istop){
        this.istop = istop;
    }
    public String getIshot(){
        return ishot;
    }
    public void setIshot(String ishot){
        this.ishot = ishot;
    }
    public String getIsflash(){
        return isflash;
    }
    public void setIsflash(String isflash){
        this.isflash = isflash;
    }
    public String getImages(){
        return images;
    }
    public void setImages(String images){
        this.images = images;
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
    public String getQdgxid(){
        return qdgxid;
    }
    public void setQdgxid(String qdgxid){
        this.qdgxid = qdgxid;
    }
    public String getWzfbsj(){
        return wzfbsj;
    }
    public void setWzfbsj(String wzfbsj){
        this.wzfbsj = wzfbsj;
    }
    public Date getZhczrq(){
        return zhczrq;
    }
    public void setZhczrq(Date zhczrq){
        this.zhczrq = zhczrq;
    }
    public String getZskid(){
        return zskid;
    }
    public void setZskid(String zskid){
        this.zskid = zskid;
    }
	public String getShcontent() {
		return shcontent;
	}
	public void setShcontent(String shcontent) {
		this.shcontent = shcontent;
	}
    
}
