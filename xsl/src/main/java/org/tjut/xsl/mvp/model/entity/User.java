package org.tjut.xsl.mvp.model.entity;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;


import org.greenrobot.greendao.annotation.Generated;

@Entity
public class User {

    @Id
    private String userid;

    private String hunterid;

    private String masterid;

    private String name;

    private String password;

    private String sex;

    private String phone;

    private String email;

    private String school;

    private Integer state;

    private Integer hunterlevel;

    private Integer masterlevel;

    private String college;

    private String major;

    private String txUrl;

    private String sno;

    @Generated(hash = 456033714)
    public User(String userid, String hunterid, String masterid, String name, String password,
            String sex, String phone, String email, String school, Integer state,
            Integer hunterlevel, Integer masterlevel, String college, String major,
            String txUrl, String sno) {
        this.userid = userid;
        this.hunterid = hunterid;
        this.masterid = masterid;
        this.name = name;
        this.password = password;
        this.sex = sex;
        this.phone = phone;
        this.email = email;
        this.school = school;
        this.state = state;
        this.hunterlevel = hunterlevel;
        this.masterlevel = masterlevel;
        this.college = college;
        this.major = major;
        this.txUrl = txUrl;
        this.sno = sno;
    }

    @Generated(hash = 586692638)
    public User() {
    }

    public String getUserid() {
        return this.userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getHunterid() {
        return this.hunterid;
    }

    public void setHunterid(String hunterid) {
        this.hunterid = hunterid;
    }

    public String getMasterid() {
        return this.masterid;
    }

    public void setMasterid(String masterid) {
        this.masterid = masterid;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSex() {
        return this.sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSchool() {
        return this.school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public Integer getState() {
        return this.state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getHunterlevel() {
        return this.hunterlevel;
    }

    public void setHunterlevel(Integer hunterlevel) {
        this.hunterlevel = hunterlevel;
    }

    public Integer getMasterlevel() {
        return this.masterlevel;
    }

    public void setMasterlevel(Integer masterlevel) {
        this.masterlevel = masterlevel;
    }

    public String getCollege() {
        return this.college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getMajor() {
        return this.major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getTxUrl() {
        return this.txUrl;
    }

    public void setTxUrl(String txUrl) {
        this.txUrl = txUrl;
    }

    public String getSno() {
        return this.sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }
}
