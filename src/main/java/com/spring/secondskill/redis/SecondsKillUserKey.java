package com.spring.secondskill.redis;

public class SecondsKillUserKey extends BasePrefix {

    public static final int TOKEN_EXPIRE = 3600 * 24 * 2;

    /*
        在一个类中声明一个静态的自己,可以直接调用
     */
    public static SecondsKillUserKey token = new SecondsKillUserKey(TOKEN_EXPIRE,"tk");
    public static SecondsKillUserKey getByName = new SecondsKillUserKey("name");

    public SecondsKillUserKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public SecondsKillUserKey(String prefix) {
        super(prefix);
    }
}
