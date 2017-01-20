package com.virtusventures.simpleqc.entity;

import com.google.gson.JsonObject;

import java.io.Serializable;

/**
 * Created by mac on 25/12/2016.
 */

public class Question implements Serializable{

    public String questionTitle;
    public String issueCount;
    public String questionEn;
    public String questionCn;
    public String id;

    public Question(JsonObject jsonObject)
    {
        id =  jsonObject.get("id").getAsString();

        questionTitle = jsonObject.get("title").getAsString();
        issueCount = jsonObject.get("failures").getAsString();

        questionEn = jsonObject.get("quiz_en").getAsString();
        questionCn = jsonObject.get("quiz_cn").getAsString();

    }
}
