package org.tjut.xsl.mvp.model.entity;

import com.google.gson.annotations.SerializedName;

public class HallTaskCard {

    private Long taskHallId;

    private Long hallTaskId;

    private Task task;

    @SerializedName("txUrl")
    private String masterTxurl;

    @SerializedName("name")
    private String masterName;

    @SerializedName("masterlevel")
    private String masterLevel;
}
