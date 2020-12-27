package com.spring.secondskill.access;


import com.spring.secondskill.domain.SecondsKillUser;

public class UserContext {

    private static ThreadLocal<SecondsKillUser> userHolder = new ThreadLocal<SecondsKillUser>();

    public static void setUser(SecondsKillUser secondsKillUser) {
        userHolder.set(secondsKillUser);
    }

    public static SecondsKillUser getUser() {
        return userHolder.get();
    }

}