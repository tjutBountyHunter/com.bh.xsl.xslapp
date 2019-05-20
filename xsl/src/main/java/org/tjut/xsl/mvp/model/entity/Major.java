package org.tjut.xsl.mvp.model.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Major {
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
