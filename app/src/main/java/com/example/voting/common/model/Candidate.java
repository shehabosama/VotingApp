package com.example.voting.common.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Candidate {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("center_id")
    @Expose
    private int center_id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("electoral_program")
    @Expose
    private String electoral_program;
    @SerializedName("symbol")
    @Expose
    private String symbol;
    @SerializedName("age")
    @Expose
    private String age;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCenter_id() {
        return center_id;
    }

    public void setCenter_id(int center_id) {
        this.center_id = center_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getElectoral_program() {
        return electoral_program;
    }

    public void setElectoral_program(String electoral_program) {
        this.electoral_program = electoral_program;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
