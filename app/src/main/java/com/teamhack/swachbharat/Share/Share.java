package com.teamhack.swachbharat.Share;

import com.teamhack.swachbharat.Profile.User;

/**
 * Created by Rishi on 11-12-2016.
 */

public class Share {

    String key;
    String latitude;
    String longitude;
    String category;
    String time;
    String status;
    User takenBy;
    User createdBy;

    public void setKey(String key) {
        this.key = key;
    }

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

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTakenBy(User takenBy) {
        this.takenBy = takenBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public String getKey() {
        return key;
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

    public String getStatus() {
        return status;
    }

    public User getTakenBy() {
        return takenBy;
    }

    public User getCreatedBy() {
        return createdBy;
    }
}
