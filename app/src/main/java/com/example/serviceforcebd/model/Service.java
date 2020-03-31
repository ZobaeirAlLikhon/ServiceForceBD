package com.example.serviceforcebd.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Service {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}