package com.seezoon.framework.modules.zhfw.entity;

import java.util.Date;

import com.seezoon.framework.common.entity.BaseEntity;
/**
 * 
 * 留言处理
 *
 */
public class Lycl extends BaseEntity<String> {

	private static final long serialVersionUID = 1L;
	/**
	 *id
	 */
	private String guestbookid;	
	/**
	 * 业务流水号
	 */
	private String ywlsh;
	/**
	 * 留言标题
	 */
   private String title;
   /**
    * 留言回复内容
    */
   private String recontent;
   /**
    * 来源渠道
    */
   private String lyqd;
   /**
    * 留言人
    */
   private String username;
   /**
    * 联系方式
    */
   private String lxfs;
   /**
    * 留言时间
    */
   private Date addtime;
   /**
    * 处理状态
    */
   private String shzt;
   /**
    * 回复时间
    */
   private Date retime;
   /**
    * 工单编号
    */
   private String gdbh;
   /**
    * 
    * 留言内容
    */
   private String content;
   
   
   /**
    * 工单来源
    * 
    */
   private String gdly;
   
   /**
    * 工单标题
    * 
    */
   private String gdbt;
   /**
    * 
    *栏目id
    */
   private String classid;
   /**
    * 
    * 来源渠道id
    */
   private String lyqdid;
   
   /**
    * 
    * 知识库ID
    */
   private String zskid;

public String getContent() {
	return content;
}
public void setContent(String content) {
	this.content = content;
}
public String getTitle() {
	return title;
}
public void setTitle(String title) {
	this.title = title;
}

public String getLyqd() {
	return lyqd;
}
public void setLyqd(String lyqd) {
	this.lyqd = lyqd;
}
public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
}
public String getLxfs() {
	return lxfs;
}
public void setLxfs(String lxfs) {
	this.lxfs = lxfs;
}
public Date getAddtime() {
	return addtime;
}
public void setAddtime(Date addtime) {
	this.addtime = addtime;
}
public String getShzt() {
	return shzt;
}
public void setShzt(String shzt) {
	this.shzt = shzt;
}
public Date getRetime() {
	return retime;
}
public void setRetime(Date retime) {
	this.retime = retime;
}
public String getGdbh() {
	return gdbh;
}
public void setGdbh(String gdbh) {
	this.gdbh = gdbh;
}
public String getGuestbookid() {
	return guestbookid;
}
public void setGuestbookid(String guestbookid) {
	this.guestbookid = guestbookid;
}
public String getGdly() {
	return gdly;
}
public void setGdly(String gdly) {
	this.gdly = gdly;
}
public String getGdbt() {
	return gdbt;
}
public void setGdbt(String gdbt) {
	this.gdbt = gdbt;
}
public String getRecontent() {
	return recontent;
}
public void setRecontent(String recontent) {
	this.recontent = recontent;
}
public String getClassid() {
	return classid;
}
public void setClassid(String classid) {
	this.classid = classid;
}
public String getLyqdid() {
	return lyqdid;
}
public void setLyqdid(String lyqdid) {
	this.lyqdid = lyqdid;
}
public String getZskid() {
	return zskid;
}
public void setZskid(String zskid) {
	this.zskid = zskid;
}

public String getYwlsh() {
	return ywlsh;
}
public void setYwlsh(String ywlsh) {
	this.ywlsh = ywlsh;
}

}
