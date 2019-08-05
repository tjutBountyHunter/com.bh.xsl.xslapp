package org.tjut.xsl.mvp.model.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class TaskDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    private String taskId;

    private String taskTitle;

    private String content;

    private String money;

    private Integer state;

    private String createDate;

    private String updatedate;

    private String deadLineDate;

    private String sourcetype;

    private List<Tag> tags;

    private Hunter hunterInfo;

    private Master masterInfo;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(String updatedate) {
        this.updatedate = updatedate;
    }

    public String getDeadLineDate() {
        return deadLineDate;
    }

    public void setDeadLineDate(String deadLineDate) {
        this.deadLineDate = deadLineDate;
    }

    public String getSourcetype() {
        return sourcetype;
    }

    public void setSourcetype(String sourcetype) {
        this.sourcetype = sourcetype;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public Hunter getHunterInfo() {
        return hunterInfo;
    }

    public void setHunterInfo(Hunter hunterInfo) {
        this.hunterInfo = hunterInfo;
    }

    public Master getMasterInfo() {
        return masterInfo;
    }

    public void setMasterInfo(Master masterInfo) {
        this.masterInfo = masterInfo;
    }
}
