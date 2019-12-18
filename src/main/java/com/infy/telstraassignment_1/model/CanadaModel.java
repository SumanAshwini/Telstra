package com.infy.telstraassignment_1.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CanadaModel {
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("rows")
    @Expose
    private ArrayList<Canada> rows = null;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<Canada> getRows() {
        return rows;
    }

    public void setRows(ArrayList<Canada> rows) {
        this.rows = rows;
    }

}
