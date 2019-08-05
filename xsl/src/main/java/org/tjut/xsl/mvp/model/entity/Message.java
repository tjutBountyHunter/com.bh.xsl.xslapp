package org.tjut.xsl.mvp.model.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;

@Entity
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id(autoincrement = true)
    private Long id;
    private String title;

    private String msg_content;

    private String date;


    private String taskId;


    @Generated(hash = 408455641)
    public Message(Long id, String title, String msg_content, String date,
                   String taskId) {
        this.id = id;
        this.title = title;
        this.msg_content = msg_content;
        this.date = date;
        this.taskId = taskId;
    }

    @Generated(hash = 637306882)
    public Message() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getId() {
        return this.id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getTitle() {
        return this.title;
    }


    public void setTitle(String title) {
        this.title = title;
    }


    public String getMsg_content() {
        return this.msg_content;
    }


    public void setMsg_content(String msg_content) {
        this.msg_content = msg_content;
    }


    public String getTaskId() {
        return this.taskId;
    }


    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", msg_content='" + msg_content + '\'' +
                ", date='" + date + '\'' +
                ", taskId='" + taskId + '\'' +
                '}';
    }
}
