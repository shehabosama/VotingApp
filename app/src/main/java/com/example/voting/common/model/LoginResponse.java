package com.example.voting.common.model;

import com.google.gson.annotations.SerializedName;

/*
This class we can fetch status and message and the user is admin or not we are use it  as object
*/
public class LoginResponse
{
    @SerializedName("status")
    public int status;
    @SerializedName("message")
    public String message;
    @SerializedName("user")
    public User user;

    public static class User
    {
        @SerializedName("id")
        public String id;
        @SerializedName("name")
        public String username;
        @SerializedName("email")
        public String email;

        @SerializedName("password")
        public String password;
        @SerializedName("center_id")
        public int address;
        @SerializedName("ssid")
        public int ssid;
        @SerializedName("gender")
        public int gender;
        @SerializedName("address_ditals")
        public String address_ditals;;

    }
}
