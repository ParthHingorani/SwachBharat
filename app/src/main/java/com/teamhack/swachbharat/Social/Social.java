package com.teamhack.swachbharat.Social;

import com.teamhack.swachbharat.Profile.User;

/**
 * Created by ParthHingorani on 16-12-2016.
 */

public class Social {

    String latitude;
    String longitude;
    String category;
    String time;
    User user;
    String socialDetail;
    String key;

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setSocailDetail(String title) {
        this.socialDetail = socialDetail;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getCategory() {
        return category;
    }

    public String getTime() {
        return time;
    }

    public String getSocialDetail() {
        return socialDetail;
    }

    public String getKey() {
        return key;
    }

    public User getUser() {
        return user;
    }
}
