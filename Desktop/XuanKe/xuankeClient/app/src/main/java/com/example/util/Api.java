package com.example.util;

import com.example.bean.Ip;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;

import org.json.JSONException;
import org.json.JSONObject;


public class Api {
    public static class MyCallBack extends AbsCallback<JSONObject> {
        @Override
        public void onSuccess(Response<JSONObject> response) {

        }

        @Override
        public JSONObject convertResponse(okhttp3.Response response) throws Throwable {
            String obj = response.body().string();
            return new JSONObject(obj);
        }
    }

    public static void get(String url, final CallBack callBack) {
        OkGo.<JSONObject>get(Ip.ips + url).execute(new MyCallBack() {

            @Override
            public void onSuccess(Response<JSONObject> response) {
                super.onSuccess(response);
                JSONObject object = response.body();
                callBack.success(object);


            }

            @Override
            public void onError(Response<JSONObject> response) {
                super.onError(response);
                callBack.faile();
            }
        });

    }

    public static void post(final String url, HttpParams params, final CallBack callBack) {
        OkGo.<JSONObject>post(Ip.ips + url).params(params).execute(new MyCallBack() {

            @Override
            public void onSuccess(Response<JSONObject> response) {
                super.onSuccess(response);
                JSONObject object = response.body();
                callBack.success(object);


            }

            @Override
            public void onError(Response<JSONObject> response) {
                super.onError(response);
                callBack.faile();
            }
        });
    }

    public interface CallBack {
        void success(JSONObject result);

        void faile();
    }

}
