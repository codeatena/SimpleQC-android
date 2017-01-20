package com.virtusventures.simpleqc.control;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by mac on 08/01/2017.
 */

public class APIService {


    private static APIService ourInstance = new APIService();
    private ApiEndpointInterface apiService;
    private APICallback callback;

    public static APIService getInstance() {
        return ourInstance;
    }

    private APIService()
    {
        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://thumbfu.com/qc/v1/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(rxAdapter)
                .build();
        apiService = retrofit.create(ApiEndpointInterface.class);

    }

    public void setOnCallback(APICallback callback1)
    {
        callback = callback1;
    }

    public Subscription  authoLogin(String userId , String password)
    {
        final Observable<JsonObject> call = apiService.login(userId ,password);
        Subscription subscription = call
                .subscribeOn(Schedulers.io()) // optional if you do not wish to override the default behavior
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<JsonObject>() {
                    @Override
                    public void onCompleted() {

                        callback.doCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        // cast to retrofit.HttpException to get the response code
                        callback.doError(e);
                    }

                    @Override
                    public void onNext(JsonObject jsonObject) {

                        Log.d("response" ,jsonObject.toString());
                        callback.doNext(jsonObject);
                    }
                });

        return subscription;
    }

    public Subscription  searchByPoNumber(String ponumber)
    {
        final Observable<JsonElement> call = apiService.searchbyPo(ponumber);
        Subscription subscription = call
                .subscribeOn(Schedulers.io()) // optional if you do not wish to override the default behavior
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<JsonElement>() {
                    @Override
                    public void onCompleted() {

                        callback.doCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        // cast to retrofit.HttpException to get the response code
                        callback.doError(e);
                    }

                    @Override
                    public void onNext(JsonElement jsonObject) {

                        Log.d("response" ,jsonObject.toString());
                        callback.doNext(jsonObject);
                    }
                });

        return subscription;
    }

    public Subscription  searchByJobNumber(String jobnumber)
    {
        final Observable<JsonElement> call = apiService.searchbyJob(jobnumber);
        Subscription subscription = call
                .subscribeOn(Schedulers.io()) // optional if you do not wish to override the default behavior
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<JsonElement>() {
                    @Override
                    public void onCompleted() {

                        callback.doCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        // cast to retrofit.HttpException to get the response code
                        callback.doError(e);
                    }

                    @Override
                    public void onNext(JsonElement jsonObject) {

                        Log.d("response" ,jsonObject.toString());
                        callback.doNext(jsonObject);
                    }
                });

        return subscription;
    }

    public Subscription getUSProject(String id)
    {
        final Observable<JsonArray> call = apiService.getusProject(id);
        Subscription subscription = call
                .subscribeOn(Schedulers.io()) // optional if you do not wish to override the default behavior
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<JsonArray>() {
                    @Override
                    public void onCompleted() {

                        callback.doCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        // cast to retrofit.HttpException to get the response code
                        callback.doError(e);
                    }

                    @Override
                    public void onNext(JsonArray jsonObject) {

                        Log.d("response" ,jsonObject.toString());
                        callback.doNext(jsonObject);
                    }
                });

        return subscription;
    }

    public Subscription getCNProject(String id)
    {
        final Observable<JsonArray> call = apiService.getcnProject(id);
        Subscription subscription = call
                .subscribeOn(Schedulers.io()) // optional if you do not wish to override the default behavior
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<JsonArray>() {
                    @Override
                    public void onCompleted() {

                        callback.doCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        // cast to retrofit.HttpException to get the response code
                        callback.doError(e);
                    }

                    @Override
                    public void onNext(JsonArray jsonObject) {

                        Log.d("response" ,jsonObject.toString());
                        callback.doNext(jsonObject);
                    }
                });

        return subscription;
    }

    public Subscription getQuestion(String  pId)
    {
        final Observable<JsonArray> call = apiService.getQuestion(pId);
        Subscription subscription = call
                .subscribeOn(Schedulers.io()) // optional if you do not wish to override the default behavior
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<JsonArray>() {
                    @Override
                    public void onCompleted() {

                        callback.doCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        // cast to retrofit.HttpException to get the response code
                        callback.doError(e);
                    }

                    @Override
                    public void onNext(JsonArray jsonObject) {

                        Log.d("response" ,jsonObject.toString());
                        callback.doNext(jsonObject);
                    }
                });

        return subscription;
    }

    public Subscription updateProjectReceiver(String pId)
    {
        final Observable<JsonObject> call = apiService.updateProjectReceiver(pId);
        Subscription subscription = call
                .subscribeOn(Schedulers.io()) // optional if you do not wish to override the default behavior
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<JsonObject>() {
                    @Override
                    public void onCompleted() {

                        callback.doCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        // cast to retrofit.HttpException to get the response code
                        callback.doError(e);
                    }

                    @Override
                    public void onNext(JsonObject jsonObject) {

                        Log.d("response" ,jsonObject.toString());
                        callback.doNext(jsonObject);
                    }
                });

        return subscription;
    }

    public Subscription updateFailure(String qIds ,String failures)
    {
        final Observable<JsonObject> call = apiService.uploadFailure(qIds ,failures);
        Subscription subscription = call
                .subscribeOn(Schedulers.io()) // optional if you do not wish to override the default behavior
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<JsonObject>() {
                    @Override
                    public void onCompleted() {

                        callback.doCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        // cast to retrofit.HttpException to get the response code
                        callback.doError(e);
                    }

                    @Override
                    public void onNext(JsonObject jsonObject) {

                        Log.d("response" ,jsonObject.toString());
                        callback.doNext(jsonObject);
                    }
                });

        return subscription;
    }
}
