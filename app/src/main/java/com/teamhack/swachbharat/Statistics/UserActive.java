package com.teamhack.swachbharat.Statistics;

/**
 * Created by neptune on 18/12/16.
 */

public class UserActive {

    public String uid;
    public String name;
    public String email;
    public int posts;
    public int rank;

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setEmail(String email) {
        this.email = email;
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

    public void setPosts(int posts)
    {
        this.posts = posts;
    }

    public int getRank()
    {
        return rank;
    }

    public void setRank(int rank)
    {
        this.rank = rank;
    }

}
