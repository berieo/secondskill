package com.spring.secondskill.redis;

public class SecondsKillUserKey extends BasePrefix {


    /*
        在一个类中声明一个静态的自己
     */
    public static SecondsKillUserKey token = new SecondsKillUserKey("tk");
    public static SecondsKillUserKey getByName = new SecondsKillUserKey("name");

    public SecondsKillUserKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public SecondsKillUserKey(String prefix) {
        super(prefix);
    }
}
