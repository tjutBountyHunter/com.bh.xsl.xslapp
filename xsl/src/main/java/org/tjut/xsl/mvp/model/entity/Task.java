package org.tjut.xsl.mvp.model.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.JoinEntity;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.Transient;

import java.util.Date;
import java.util.List;

import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

@Entity
public class Task {

    @Id
    private String taskId;

    private Long HallTaskId;

    private String taskTitle;

    private String createDate;

    private String deadLineDate;

    private String updatedate;

    private String money;

    private String content;

    private String masterId;

    private boolean isRecommend;

    private String sourceType = "APP";

    private boolean isCurrentTask;

    private Integer state;

    private String name;

    private String sex;

    private String txUrl;

    private Integer masterlevel;

    @ToMany
    @JoinEntity(entity = TaskAndTag.class, sourceProperty = "taskId", targetProperty = "tagid")
    private List<Tag> tags;

    /**
     * Used to resolve relations
     */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /**
     * Used for active entity operations.
     */
    @Generated(hash = 1469429066)
    private transient TaskDao myDao;

    @Generated(hash = 421729428)
    public Task(String taskId, Long HallTaskId, String taskTitle, String createDate, String deadLineDate,
            String updatedate, String money, String content, String masterId, boolean isRecommend,
            String sourceType, boolean isCurrentTask, Integer state, String name, String sex, String txUrl,
            Integer masterlevel) {
        this.taskId = taskId;
        this.HallTaskId = HallTaskId;
        this.taskTitle = taskTitle;
        this.createDate = createDate;
        this.deadLineDate = deadLineDate;
        this.updatedate = updatedate;
        this.money = money;
        this.content = content;
        this.masterId = masterId;
        this.isRecommend = isRecommend;
        this.sourceType = sourceType;
        this.isCurrentTask = isCurrentTask;
        this.state = state;
        this.name = name;
        this.sex = sex;
        this.txUrl = txUrl;
        this.masterlevel = masterlevel;
    }

    @Generated(hash = 733837707)
    public Task() {
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Long getHallTaskId() {
        return this.HallTaskId;
    }

    public void setHallTaskId(Long HallTaskId) {
        this.HallTaskId = HallTaskId;
    }

    public String getTaskTitle() {
        return this.taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getDeadLineDate() {
        return this.deadLineDate;
    }

    public void setDeadLineDate(String deadLineDate) {
        this.deadLineDate = deadLineDate;
    }

    public String getUpdatedate() {
        return this.updatedate;
    }

    public void setUpdatedate(String updatedate) {
        this.updatedate = updatedate;
    }

    public String getMoney() {
        return this.money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMasterId() {
        return this.masterId;
    }

    public void setMasterId(String masterId) {
        this.masterId = masterId;
    }

    public boolean getIsRecommend() {
        return this.isRecommend;
    }

    public void setIsRecommend(boolean isRecommend) {
        this.isRecommend = isRecommend;
    }

    public String getSourceType() {
        return this.sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public boolean getIsCurrentTask() {
        return this.isCurrentTask;
    }

    public void setIsCurrentTask(boolean isCurrentTask) {
        this.isCurrentTask = isCurrentTask;
    }

    public Integer getState() {
        return this.state;
    }

    public void setState(Integer state) {
        this.state = state;
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
    @Generated(hash = 725075064)
    public List<Tag> getTags() {
        if (tags == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            TagDao targetDao = daoSession.getTagDao();
            List<Tag> tagsNew = targetDao._queryTask_Tags(taskId);
            synchronized (this) {
                if (tags == null) {
                    tags = tagsNew;
                }
            }
        }
        return tags;
    }

    /**
     * Resets a to-many relationship, making the next get call to query for a fresh result.
     */
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

    public Integer getMasterlevel() {
        return this.masterlevel;
    }

    public void setMasterlevel(Integer masterlevel) {
        this.masterlevel = masterlevel;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1442741304)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getTaskDao() : null;
    }


}
