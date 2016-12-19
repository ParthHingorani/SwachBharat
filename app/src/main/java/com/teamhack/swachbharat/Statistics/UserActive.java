package com.teamhack.swachbharat.Statistics;

/**
 * Created by neptune on 18/12/16.
 */

public class UserActive {

    public String uid;
    public String name;
    public String email;
    public String type;
    public int posts;

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPosts(int posts)
    {
        this.posts = posts;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType()
    {
        return type;
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

    public int getPosts()
    {
        return posts;
    }
}
