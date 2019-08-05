package org.tjut.xsl.mvp.model.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;

@Entity
public class Major implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private Long id;

    private String majorname;

    @Generated(hash = 561710562)
    public Major(Long id, String majorname) {
        this.id = id;
        this.majorname = majorname;
    }

    @Generated(hash = 818744244)
    public Major() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMajorname() {
        return this.majorname;
    }

    public void setMajorname(String majorname) {
        this.majorname = majorname;
    }

}
