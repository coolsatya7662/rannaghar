package com.plabon.rannaghar.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BannerResult {
    @SerializedName("banners")
    List<Banner> bannerList;
    @SerializedName("code")
    int code;
    @SerializedName("status")
    String status;

    public List<Banner> getBannerList() {
        return bannerList;
    }

    public void setBannerList(List<Banner> bannerList) {
        this.bannerList = bannerList;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
