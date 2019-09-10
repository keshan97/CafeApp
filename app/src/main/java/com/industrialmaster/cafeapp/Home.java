package com.industrialmaster.cafeapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void loginpage(View view){
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    public void signuppage(View view){
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }
}
