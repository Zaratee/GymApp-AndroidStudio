package com.example.carloz.gymapp.adaptador;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleyImagen {

    private static VolleyImagen mInstance;
    private RequestQueue requestQueue;
    private static Context mCtx;

    public VolleyImagen(Context context) {
        mCtx = context;
        requestQueue = getRequestQueue();
    }

    private RequestQueue getRequestQueue() {
        if (requestQueue == null)
            requestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        return requestQueue;
    }

    public static synchronized VolleyImagen getmInstance(Context context){
        if (mInstance == null)
            mInstance = new VolleyImagen(context);
        return mInstance;
    }

    public<T> void addToRequestQue(Request<T> request){
        getRequestQueue().add(request);
    }


}
