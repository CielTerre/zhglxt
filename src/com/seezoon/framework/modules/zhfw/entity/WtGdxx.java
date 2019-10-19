package com.seezoon.framework.modules.zhfw.entity;

import com.seezoon.framework.common.entity.BaseEntity;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
/**
 * 工单处理
 *  2018-8-12 17:38:16
 */
public class WtGdxx extends BaseEntity<String> {

    private static final long serialVersionUID = 1L;
    /**
     * 业务流水号
     */
    @NotNull
    @Length(max = 20)
    private String ywlsh;
    /**
     * 工单编号
     */
    @Length(max = 20)
    private String gdbh;
    /**
     * 工单来源(01投诉02建议03在线咨询04 12329)
     */
    @Length(max = 2)
    private String gdly;
    /**
     * 紧急程度(0一般1紧急)
     */
    @Length(max = 1)
    private String jjcd;
    /**
     * 要求完成时间
     */
    private Date yqwcsj;
    /**
     * 工单标题
     */
    @Length(max = 50)
    private String gdbt;
    /**
     * 工单内容
     */
    @Length(max = 500)
    private String gdnr;
    /**
     * 预留，支持查看相关的交流记录
     */
    @Length(max = 20)
    private String xgywlsh;
    /**
     * 当前状态(0未处理1处理中2处理完毕)
     */
    @Length(max = 2)
    private String dqzt;
    /**
     * 录入人
     */
    @Length(max = 20)
    private String llr;
    /**
     * 录入日期
     */
    @Length(max = 20)
    private String llrq;
    /**
     * 处理人
     */
    @Length(max = 32)
    private String clr;
    /**
     * 处理时间
     */
    private Date clsj;
    /**
     * 处理结果
     */
    @Length(max = 200)
    private String cljg;

    /**
     * 来源渠道id
     */
    @Length(max = 20)
    private String lyqdid;

    /**
     * 回访状态
     */
    @Length(max = 2)
    private String hfzt;
    /**
     * 回访内容
     */
    @Length(max = 500)
    private String hfnr;

    /**
     * 回访时间
     */
    private Date hfsj;

    /**
     * 用户满意度
     */
    @Length(max = 2)
    private String yhmyd;

    public String getYwlsh(){
        return ywlsh;
    }
    public void setYwlsh(String ywlsh){
        this.ywlsh = ywlsh;
    }
    public String getGdbh(){
        return gdbh;
    }
    public void setGdbh(String gdbh){
        this.gdbh = gdbh;
    }
    public String getGdly(){
        return gdly;
    }
    public void setGdly(String gdly){
        this.gdly = gdly;
    }
    public String getJjcd(){
        return jjcd;
    }
    public void setJjcd(String jjcd){
        this.jjcd = jjcd;
    }
    public Date getYqwcsj(){
        return yqwcsj;
    }
    public void setYqwcsj(Date yqwcsj){
        this.yqwcsj = yqwcsj;
    }
    public String getGdbt(){
        return gdbt;
    }
    public void setGdbt(String gdbt){
        this.gdbt = gdbt;
    }
    public String getGdnr(){
        return gdnr;
    }
    public void setGdnr(String gdnr){
        this.gdnr = gdnr;
    }
    public String getXgywlsh(){
        return xgywlsh;
    }
    public void setXgywlsh(String xgywlsh){
        this.xgywlsh = xgywlsh;
    }
    public String getDqzt(){
        return dqzt;
    }
    public void setDqzt(String dqzt){
        this.dqzt = dqzt;
    }
    public String getLlr(){
        return llr;
    }
    public void setLlr(String llr){
        this.llr = llr;
    }
    public String getLlrq(){
        return llrq;
    }
    public void setLlrq(String llrq){
        this.llrq = llrq;
    }
    public String getClr(){
        return clr;
    }
    public void setClr(String clr){
        this.clr = clr;
    }
    public Date getClsj(){
        return clsj;
    }
    public void setClsj(Date clsj){
        this.clsj = clsj;
    }
    public String getCljg(){
        return cljg;
    }
    public void setCljg(String cljg){
        this.cljg = cljg;
    }

    public String getLyqdid() {
        return lyqdid;
    }

    public void setLyqdid(String lyqdid) {
        this.lyqdid = lyqdid;
    }

    public String getHfzt() {
        return hfzt;
    }

    public void setHfzt(String hfzt) {
        this.hfzt = hfzt;
    }

    public Date getHfsj() {
        return hfsj;
    }

    public void setHfsj(Date hfsj) {
        this.hfsj = hfsj;
    }

    public String getYhmyd() {
        return yhmyd;
    }

    public void setYhmyd(String yhmyd) {
        this.yhmyd = yhmyd;
    }

    public String getHfnr() {
        return hfnr;
    }

    public void setHfnr(String hfnr) {
        this.hfnr = hfnr;
    }
}
