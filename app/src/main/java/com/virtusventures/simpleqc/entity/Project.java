package com.virtusventures.simpleqc.entity;

import com.google.gson.JsonObject;

import java.io.Serializable;

/**
 * Created by mac on 25/12/2016.
 */


public class Project implements Serializable{

    public String projectName;
    public String poNumber;
    public String jobNumber;
    public String orderNumber;
    public String status;
    public String projectId;

    public Project(JsonObject jsonObject)
    {
        projectName = jsonObject.get("name").getAsString();
        poNumber = jsonObject.get("po_number").getAsString();
        jobNumber = jsonObject.get("job_number").getAsString();
        orderNumber = jsonObject.get("order_quantity").getAsString();
        status = jsonObject.get("status").getAsString();
        projectId = jsonObject.get("id").getAsString();

    }
}


