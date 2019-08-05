package org.tjut.xsl.mvp.model.entity;

import java.io.Serializable;
import java.util.Date;

public class Master implements Serializable {

    private static final long serialVersionUID = 1L;


    private String userid;

    private Integer level;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private Integer empirical;

    private Integer taskaccnum;

    private Integer taskrevokenum;

    private String credit;

    private String descr;

//    private Date lastaccdate;


    private String txUrl;

    private String phone;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getEmpirical() {
        return empirical;
    }

    public void setEmpirical(Integer empirical) {
        this.empirical = empirical;
    }

    public Integer getTaskaccnum() {
        return taskaccnum;
    }

    public void setTaskaccnum(Integer taskaccnum) {
        this.taskaccnum = taskaccnum;
    }

    public Integer getTaskrevokenum() {
        return taskrevokenum;
    }

    public void setTaskrevokenum(Integer taskrevokenum) {
        this.taskrevokenum = taskrevokenum;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }



    public String getTxUrl() {
        return txUrl;
    }

    public void setTxUrl(String txUrl) {
        this.txUrl = txUrl;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
