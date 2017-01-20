package com.virtusventures.simpleqc.control;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by mac on 08/01/2017.
 */

public interface ApiEndpointInterface {

    @FormUrlEncoded
    @POST("login")
    Observable<JsonObject> login(@Field("user_id") String userid , @Field("password") String password);

    @FormUrlEncoded
    @POST("search_project_by_po_number")
    Observable<JsonElement> searchbyPo(@Field("po_number") String ponumber);

    @FormUrlEncoded
    @POST("search_project_by_job_number")
    Observable<JsonElement> searchbyJob(@Field("job_number") String jobnumber);

    @FormUrlEncoded
    @POST("get_projects_for_us_insp")
    Observable<JsonArray> getusProject(@Field("id") String userId);

    @FormUrlEncoded
    @POST("get_projects_for_cn_insp")
    Observable<JsonArray> getcnProject(@Field("id") String userId);

    @FormUrlEncoded
    @POST("get_questions_by_project_id")
    Observable<JsonArray> getQuestion(@Field("p_id") String pId);

    @FormUrlEncoded
    @POST("update_project_to_receiver")
    Observable <JsonObject> updateProjectReceiver(@Field("p_id") String pId);

    @FormUrlEncoded
    @POST("update_question_failures")
    Observable <JsonObject> uploadFailure(@Field("q_ids") String qIds ,@Field("failures") String failures);
}
