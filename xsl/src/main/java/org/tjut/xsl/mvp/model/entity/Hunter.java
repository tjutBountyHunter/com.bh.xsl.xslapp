package org.tjut.xsl.mvp.model.entity;

import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToMany;

import java.io.Serializable;
import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

@Entity
public class Hunter implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SerializedName(value = "Hunterid",alternate = {"hunterid"})
    private String Hunterid;

    private String userid;

    private Integer level;

    private Integer empirical;

    private Integer taskaccnum;

    private Integer taskfailnum;

    private Integer credit;

    private String name;

    private String sex;

    private String phone;

    private String email;

    private String txUrl;

    @ToMany(referencedJoinProperty = "tagid")
    private List<Tag> tagVos;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 879596490)
    private transient HunterDao myDao;

    @Generated(hash = 346505479)
    public Hunter(String Hunterid, String userid, Integer level, Integer empirical,
            Integer taskaccnum, Integer taskfailnum, Integer credit, String name,
            String sex, String phone, String email, String txUrl) {
        this.Hunterid = Hunterid;
        this.userid = userid;
        this.level = level;
        this.empirical = empirical;
        this.taskaccnum = taskaccnum;
        this.taskfailnum = taskfailnum;
        this.credit = credit;
        this.name = name;
        this.sex = sex;
        this.phone = phone;
        this.email = email;
        this.txUrl = txUrl;
    }

    @Generated(hash = 584118558)
    public Hunter() {
    }

    public String getHunterid() {
        return this.Hunterid;
    }

    public void setHunterid(String Hunterid) {
        this.Hunterid = Hunterid;
    }

    public String getUserid() {
        return this.userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Integer getLevel() {
        return this.level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getEmpirical() {
        return this.empirical;
    }

    public void setEmpirical(Integer empirical) {
        this.empirical = empirical;
    }

    public Integer getTaskaccnum() {
        return this.taskaccnum;
    }

    public void setTaskaccnum(Integer taskaccnum) {
        this.taskaccnum = taskaccnum;
    }

    public Integer getTaskfailnum() {
        return this.taskfailnum;
    }

    public void setTaskfailnum(Integer taskfailnum) {
        this.taskfailnum = taskfailnum;
    }

    public Integer getCredit() {
        return this.credit;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getTxUrl() {
        return this.txUrl;
    }

    public void setTxUrl(String txUrl) {
        this.txUrl = txUrl;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1486514423)
    public List<Tag> getTagVos() {
        if (tagVos == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            TagDao targetDao = daoSession.getTagDao();
            List<Tag> tagVosNew = targetDao._queryHunter_TagVos(Hunterid);
            synchronized (this) {
                if (tagVos == null) {
                    tagVos = tagVosNew;
                }
            }
        }
        return tagVos;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 545731318)
    public synchronized void resetTagVos() {
        tagVos = null;
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
    @Generated(hash = 1980035656)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getHunterDao() : null;
    }

}
