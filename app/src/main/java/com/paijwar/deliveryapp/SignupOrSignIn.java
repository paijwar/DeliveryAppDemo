package com.paijwar.deliveryapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ViewFlipper;

import com.paijwar.deliveryapp.storage.LocalStorage;
import com.paijwar.deliveryapp.utils.HelperUtils;

public class SignupOrSignIn extends AppCompatActivity {

    ViewFlipper mFlipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        mFlipper = (ViewFlipper) findViewById(R.id.view_flipper);
    }

    public void onSubmitOtp(View view) {
        LocalStorage.getInstance().put("token","token");
        startActivity(new Intent(this, Home.class));
        finish();
    }

    public void onGenerateOtp(View view) {
        HelperUtils.getInstance().showMessage("OTP generated");
        mFlipper.showNext();
    }

}
