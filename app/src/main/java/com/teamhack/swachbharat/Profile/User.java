package com.teamhack.swachbharat.Profile;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Rishi on 08-12-2016.
 */
@IgnoreExtraProperties
public class User {
    public String uid;
    public String name;
    public String email;
    public String type;
    public int posts;
    public int completed;
    public String info;
    //public String place;

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public void setPlace(String place) {
//        this.place = place;
//    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCompleted(int completed) {
        this.completed = completed;
    }

    public void setPosts(int posts)
    {
        this.posts = posts;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getPosts()
    {
        return posts;
    }

    public int getCompleted() {
        return completed;
    }

    public String getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getType() {
        return type;
    }

    public String getInfo() {
        return info;
    }
}
