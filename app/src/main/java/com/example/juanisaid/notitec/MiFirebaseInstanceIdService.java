package com.example.juanisaid.notitec;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.iid.FirebaseInstanceId;

public class MiFirebaseInstanceIdService extends FirebaseInstanceIdService {
    public static final String TAG = "NOTICIAS";
    @Override
    public void onTokenRefresh() {
        //super.onTokenRefresh();

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "ESTE ES EL TOKEN:    " + refreshedToken);

        Log.d("Firebase", "ESTE ES EL TOKEN************"+ FirebaseInstanceId.getInstance().getToken());
    }
}
