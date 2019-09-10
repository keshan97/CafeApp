package com.industrialmaster.cafeapp;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MySingleton {
    private static MySingleton mInstance;
    private RequestQueue requestQueue;
    private static Context mCx;

    private MySingleton(Context context){
        mCx = context;
        requestQueue =getRequestQueue();
    }

    public static synchronized MySingleton getInstance(Context context){
        if(mInstance==null){
            mInstance = new MySingleton(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue(){

        if(requestQueue==null){
            requestQueue = Volley.newRequestQueue(mCx.getApplicationContext());
        }
        return requestQueue;
    }

    public <T>void addTorequestqueue(Request<T> request){
        requestQueue.add(request);
    }
}
