package com.industrialmaster.cafeapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
    }

    public void toRecharge(View view){
        Intent intent = new Intent(getApplicationContext(),Recharge.class);
        startActivity(intent);
    }

    public void toDiscounts(View view){
        Intent intent = new Intent(getApplicationContext(),Discounts.class);
        startActivity(intent);
    }

    public void toProfile(View view){
        Intent intent = new Intent(getApplicationContext(),Profile.class);
        startActivity(intent);
    }

    public void toContact(View view){
        Intent intent = new Intent(getApplicationContext(),Contact.class);
        startActivity(intent);
    }


}
