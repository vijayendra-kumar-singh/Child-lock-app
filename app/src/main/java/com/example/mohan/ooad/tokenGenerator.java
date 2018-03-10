package com.example.mohan.ooad;

import com.google.firebase.iid.FirebaseInstanceId;

public class tokenGenerator extends com.google.firebase.iid.FirebaseInstanceIdService {

    String token;

    @Override
    public void onTokenRefresh() {
        token = FirebaseInstanceId.getInstance().getToken();
    }

    public String getToken(){
        onTokenRefresh();
        return token;
    }
}
