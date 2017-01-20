package com.virtusventures.simpleqc.control;

import com.google.gson.JsonElement;

/**
 * Created by mac on 08/01/2017.
 */

public interface APICallback {

    public void doNext(JsonElement jsonObject);
    public void doCompleted();
    public void doError(Throwable e);

}
