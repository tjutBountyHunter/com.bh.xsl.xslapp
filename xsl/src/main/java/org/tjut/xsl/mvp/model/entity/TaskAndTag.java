package org.tjut.xsl.mvp.model.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;

@Entity
public class TaskAndTag implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id(autoincrement = true)
    private Long taskAndTagId;

    private String taskId;

    private String tagid;

    @Generated(hash = 1880313271)
    public TaskAndTag(Long taskAndTagId, String taskId, String tagid) {
        this.taskAndTagId = taskAndTagId;
        this.taskId = taskId;
        this.tagid = tagid;
    }

    @Generated(hash = 591759228)
    public TaskAndTag() {
    }

    public Long getTaskAndTagId() {
        return this.taskAndTagId;
    }

    public void setTaskAndTagId(Long taskAndTagId) {
        this.taskAndTagId = taskAndTagId;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTagid() {
        return this.tagid;
    }

    public void setTagid(String tagid) {
        this.tagid = tagid;
    }
}
