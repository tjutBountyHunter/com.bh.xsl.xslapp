package org.tjut.xsl.mvp.model.entity;


import com.alibaba.fastjson.annotation.JSONField;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;


import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.JoinEntity;
import org.greenrobot.greendao.annotation.ToMany;

import java.io.Serializable;
import java.util.List;
import org.greenrobot.greendao.DaoException;

@Entity
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

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

    private Integer hunterEmpirical;

    private Integer masterEmpirical;

    @ToMany
    @JoinEntity(entity = UserAndTag.class, sourceProperty = "userid", targetProperty = "tagid")
    @JSONField(name = "tagVos")
    private List<Tag> tags;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1507654846)
    private transient UserDao myDao;

    public Integer getHunterEmpirical() {
        return hunterEmpirical;
    }

    public void setHunterEmpirical(Integer hunterEmpirical) {
        this.hunterEmpirical = hunterEmpirical;
    }

    public Integer getMasterEmpirical() {
        return masterEmpirical;
    }

    public void setMasterEmpirical(Integer masterEmpirical) {
        this.masterEmpirical = masterEmpirical;
    }

    @Generated(hash = 1956828015)
    public User(String userid, String hunterid, String masterid, String name, String password,
            String sex, String phone, String email, String school, Integer state, Integer hunterlevel,
            Integer masterlevel, String college, String major, String txUrl, String sno,
            Integer hunterEmpirical, Integer masterEmpirical) {
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
        this.hunterEmpirical = hunterEmpirical;
        this.masterEmpirical = masterEmpirical;
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

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 818175001)
    public List<Tag> getTags() {
        if (tags == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            TagDao targetDao = daoSession.getTagDao();
            List<Tag> tagsNew = targetDao._queryUser_Tags(userid);
            synchronized (this) {
                if (tags == null) {
                    tags = tagsNew;
                }
            }
        }
        return tags;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 404234)
    public synchronized void resetTags() {
        tags = null;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 2059241980)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getUserDao() : null;
    }

}
