package com.seezoon.framework.modules.zhfw.entity;

import com.seezoon.framework.common.entity.BaseEntity;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.sql.Timestamp;
import java.util.Date;

/**
 * 互动交流信息表
 *  2018-8-12 18:12:45
 */
public class WtHdjlxx extends BaseEntity<String> {

   private static final long serialVersionUID = 1L;
    /**
     * 业务流水号
     */
    @NotNull
    @Length(min = 1, max = 20)
    private String ywlsh;
    /**
     * 渠道ID
     */
    @Length(max = 20)
    private String qdid;
    /**
     * 交流主题
     */
    @Length(max = 50)
    private String jlzt;
    /**
     * 咨询内容
     */
    @Length(max = 200)
    private String zxnr;
    /**
     * 关键字
     */
    @Length(max = 50)
    private String gjz;
    /**
     * 相关用户账号
     */
    @Length(max = 50)
    private String xgyhzh;

    private Timestamp fqsj;
    private String  yhxm;
    private String kfxm;

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
    public String getJlzt(){
        return jlzt;
    }
    public void setJlzt(String jlzt){
        this.jlzt = jlzt;
    }
    public String getZxnr(){
        return zxnr;
    }
    public void setZxnr(String zxnr){
        this.zxnr = zxnr;
    }
    public String getGjz(){
        return gjz;
    }
    public void setGjz(String gjz){
        this.gjz = gjz;
    }
    public String getXgyhzh(){
        return xgyhzh;
    }
    public void setXgyhzh(String xgyhzh){
        this.xgyhzh = xgyhzh;
    }

    public Timestamp getFqsj() {
        return fqsj;
    }

    public void setFqsj(Timestamp fqsj) {
        this.fqsj = fqsj;
    }

    public String getYhxm() {
        return yhxm;
    }

    public void setYhxm(String yhxm) {
        this.yhxm = yhxm;
    }

    public String getKfxm() {
        return kfxm;
    }

    public void setKfxm(String kfxm) {
        this.kfxm = kfxm;
    }
}
