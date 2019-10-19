package com.seezoon.framework.modules.zhfw.entity;

import com.seezoon.framework.common.entity.BaseEntity;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.sql.Timestamp;
import java.util.Date;
/**
 * ������أ��ֻ�ע������Ϣ��
 * Copyright &copy; 2018 powered by huangdf, All rights reserved.
 * @author hdf 2018-9-12 16:35:17
 */
public class WtSjyzmxx extends BaseEntity<String> {

   private static final long serialVersionUID = 1L;
    /**
     * ҵ����ˮ��
     */
    @NotNull
    @Length(min = 1, max = 20)
    private String ywlsh;
    /**
     * ����ID������������Ϣ��ID��
     */
    @Length(max = 20)
    private String qdid;
    /**
     * �û�ID
     */
    @Length(max = 20)
    private String yhid;
    /**
     * �û������루 01���ˣ�02��λ��03�����̣�04�������֣�05���У�06���Ĺ�����Ա��
     */
    @Length(max = 2)
    private String yhlbdm;
    /**
     * ����ģ�����   ����ע�ᡰWT00001���������޸�/�������롰WT00002�������ӿ�ͣ�ã��޸İ��ֻ����롰WT00003�������˵�¼��WT00004����������ǰ���WT00005���������Զ����۽ɴ����롰WT00006������Ϣ�������"WT0007"�����˹����𻹴������Զ�����"WT0008"�������޸�\���˹ؼ���Ϣ�޸�"WT0009"�����˽ɴ�"WT00010"������黹"WT00011"
     */
    @Length(max = 20)
    private String dtmyz;
    /**
     * �ֻ�����
     */
    @Length(max = 11)
    private String sjhm;
    /**
     * ��֤��
     */
    @Length(max = 6)
    private String yzm;
    /**
     * ��֤�뷢��ʱ��
     */
    private Timestamp yzmfssj;
    /**
     * ��֤����֤ʱ��
     */
    private Timestamp yzmyzsj;
    /**
     * ���ҵ����ˮ��
     */
    @Length(max = 20)
    private String xgyslsh;
    /**
     * ��֤��״̬��Y��Ч��N����֤��F�ѹ��ڣ�
     */
    @Length(max = 2)
    private String yzmzt;
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
    public String getYhid(){
        return yhid;
    }
    public void setYhid(String yhid){
        this.yhid = yhid;
    }
    public String getYhlbdm(){
        return yhlbdm;
    }
    public void setYhlbdm(String yhlbdm){
        this.yhlbdm = yhlbdm;
    }
    public String getDtmyz(){
        return dtmyz;
    }
    public void setDtmyz(String dtmyz){
        this.dtmyz = dtmyz;
    }
    public String getSjhm(){
        return sjhm;
    }
    public void setSjhm(String sjhm){
        this.sjhm = sjhm;
    }
    public String getYzm(){
        return yzm;
    }
    public void setYzm(String yzm){
        this.yzm = yzm;
    }

    public Timestamp getYzmfssj() {
        return yzmfssj;
    }

    public void setYzmfssj(Timestamp yzmfssj) {
        this.yzmfssj = yzmfssj;
    }

    public Timestamp getYzmyzsj() {
        return yzmyzsj;
    }

    public void setYzmyzsj(Timestamp yzmyzsj) {
        this.yzmyzsj = yzmyzsj;
    }

    public String getXgyslsh(){
        return xgyslsh;
    }
    public void setXgyslsh(String xgyslsh){
        this.xgyslsh = xgyslsh;
    }
    public String getYzmzt(){
        return yzmzt;
    }
    public void setYzmzt(String yzmzt){
        this.yzmzt = yzmzt;
    }
}