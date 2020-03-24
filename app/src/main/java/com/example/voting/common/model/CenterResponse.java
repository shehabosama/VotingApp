package com.example.voting.common.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CenterResponse {
    @SerializedName("centers")
    @Expose
    List<Centers> centers = null;

    public List<Centers> getCenters() {
        return centers;
    }

    public void setCenters(List<Centers> centers) {
        this.centers = centers;
    }
}
