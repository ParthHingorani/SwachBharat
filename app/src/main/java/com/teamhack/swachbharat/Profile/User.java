package com.teamhack.swachbharat.Profile;

/**
 * Created by Rishi on 08-12-2016.
 */

public class User {
    public String uid;
    public String name;
    public String email;
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

    public String getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

//    public String getPlace() {
//        return place;
//    }
}
