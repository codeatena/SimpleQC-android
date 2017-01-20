package com.virtusventures.simpleqc.entity;

import com.google.gson.JsonObject;

/**
 * Created by mac on 10/01/2017.
 */

public class User {

    public String dbid;
    public String userId;
    public String userName;
    public String type;

    public User(JsonObject jsonObject)
    {
        dbid = jsonObject.get("id").getAsString();
        userName = jsonObject.get("username").getAsString();
        userId = jsonObject.get("userid").getAsString();
        type = jsonObject.get("usertype").getAsString();

    }
}
