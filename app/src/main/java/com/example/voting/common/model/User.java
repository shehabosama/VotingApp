package com.example.voting.common.model;

import com.google.gson.annotations.SerializedName;


/*
Fetch the user name and password and email through setter and getter function we are use it as object
*/
public class User  {
    @SerializedName("username")
    public String username;
    @SerializedName("password")
    public String password;
    @SerializedName("email")
    public String email;
    @SerializedName("address")
    public String address;
    @SerializedName("details_address")
    public String details_address;
    @SerializedName("ssid")
    public int ssid;
    @SerializedName("age")
    public String age;
    @SerializedName("gender")
    public int gender;
    public int id;
}
