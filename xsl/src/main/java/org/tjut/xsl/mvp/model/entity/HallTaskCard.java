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
public class HallTaskCard implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id(autoincrement = true)
    private Long HallTaskId;

    @ToMany(referencedJoinProperty = "HallTaskId")
    private List<Task> taskInfoVos;

    private Integer upFlag;

    private Integer downFlag;

    /**
     * Used to resolve relations
     */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /**
     * Used for active entity operations.
     */
    @Generated(hash = 1686856137)
    private transient HallTaskCardDao myDao;

    @Generated(hash = 1156372)
    public HallTaskCard(Long HallTaskId, Integer upFlag, Integer downFlag) {
        this.HallTaskId = HallTaskId;
        this.upFlag = upFlag;
        this.downFlag = downFlag;
    }

    @Generated(hash = 655903845)
    public HallTaskCard() {
    }

    public Long getHallTaskId() {
        return this.HallTaskId;
    }

    public void setHallTaskId(Long HallTaskId) {
        this.HallTaskId = HallTaskId;
    }

    public Integer getUpFlag() {
        return this.upFlag;
    }

    public void setUpFlag(Integer upFlag) {
        this.upFlag = upFlag;
    }

    public Integer getDownFlag() {
        return this.downFlag;
    }

    public void setDownFlag(Integer downFlag) {
        this.downFlag = downFlag;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 3827369)
    public List<Task> getTaskInfoVos() {
        if (taskInfoVos == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            TaskDao targetDao = daoSession.getTaskDao();
            List<Task> taskInfoVosNew = targetDao
                    ._queryHallTaskCard_TaskInfoVos(HallTaskId);
            synchronized (this) {
                if (taskInfoVos == null) {
                    taskInfoVos = taskInfoVosNew;
                }
            }
        }
        return taskInfoVos;
    }

    /**
     * Resets a to-many relationship, making the next get call to query for a fresh result.
     */
    @Generated(hash = 336203039)
    public synchronized void resetTaskInfoVos() {
        taskInfoVos = null;
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
    @Generated(hash = 1403234872)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getHallTaskCardDao() : null;
    }

}
