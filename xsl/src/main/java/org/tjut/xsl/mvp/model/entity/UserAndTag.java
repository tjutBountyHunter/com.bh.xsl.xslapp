package org.tjut.xsl.mvp.model.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;

@Entity
public class UserAndTag implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id(autoincrement = true)
    private Long id;

    private String userid;

    private String tagid;

    @Generated(hash = 2140776763)
    public UserAndTag(Long id, String userid, String tagid) {
        this.id = id;
        this.userid = userid;
        this.tagid = tagid;
    }

    @Generated(hash = 549656635)
    public UserAndTag() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserid() {
        return this.userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getTagid() {
        return this.tagid;
    }

    public void setTagid(String tagid) {
        this.tagid = tagid;
    }

}
