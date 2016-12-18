package com.teamhack.swachbharat.Statistics;

/**
 * Created by neptune on 17/12/16.
 */

public class User
{
        public String uid;
        public String name;
        public String email;
        public int taken;

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

        public int getTaken()
        {
            return taken;
        }

        public void setTaken(int taken)
        {
            this.taken = taken;
        }

}
