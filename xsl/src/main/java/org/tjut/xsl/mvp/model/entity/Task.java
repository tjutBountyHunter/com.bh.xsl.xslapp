package org.tjut.xsl.mvp.model.entity;

import org.greenrobot.greendao.annotation.Transient;

import java.util.Date;

public class Task {

    Long id;

    String taskId;

    String taskTitle;

    Date createDate;

    Date deadLineDate;

    String money;

    String content;

    String masterId;

    boolean isRecommend;

    @Transient
    String sourceType = "APP";

    boolean isCurrentTask;

    private Integer state;

}
