package org.tjut.xsl.mvp.model.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;

@Entity

public class Tag  implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    private String tagid;

    private String tagName;

    @Generated(hash = 745576747)
    public Tag(String tagid, String tagName) {
        this.tagid = tagid;
        this.tagName = tagName;
    }

    @Generated(hash = 1605720318)
    public Tag() {
    }

    public String getTagid() {
        return this.tagid;
    }

    public void setTagid(String tagid) {
        this.tagid = tagid;
    }

    public String getTagName() {
        return this.tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
