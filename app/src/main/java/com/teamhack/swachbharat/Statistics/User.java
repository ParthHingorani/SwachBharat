package com.teamhack.swachbharat.Statistics;

/**
 * Created by neptune on 17/12/16.
 */

public class User
{
        public String uid;
        public String name;
        public String email;
        public String type;
        public int taken;
        public int completed;

        public void setUid(String uid) {
            this.uid = uid;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setType(String type) {  this.type = type; }

        public void setTaken(int taken)
    {
        this.taken = taken;
    }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setCompleted(int completed)
    {
        this.completed=completed;
    }

        public String getUid() {
            return uid;
        }

        public String getType() {
            return type;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }

        public int getTaken()
        {
            return taken;
        }

        public int getCompleted(){ return completed;}

}
