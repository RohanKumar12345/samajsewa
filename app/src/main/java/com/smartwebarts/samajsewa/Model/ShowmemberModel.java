package com.smartwebarts.samajsewa.Model;

public class ShowmemberModel {
    String name,profile_img;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile_img() {
        return profile_img;
    }

    public void setProfile_img(String profile_img) {
        this.profile_img = profile_img;
    }

    public ShowmemberModel(String name, String profile_img) {
        this.name = name;
        this.profile_img = profile_img;
    }
}
