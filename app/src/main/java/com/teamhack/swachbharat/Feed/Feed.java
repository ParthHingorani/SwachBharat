package com.teamhack.swachbharat.Feed;

import com.teamhack.swachbharat.Profile.User;

/**
 * Created by Rishi on 08-12-2016.
 */

public class Feed {

    String title;
    String content;
    String key;
    String time;
    User user;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getKey() {
        return key;
    }

    public String getTime() {
        return time;
    }

    public User getUser() {
        return user;
    }
}
