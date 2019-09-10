package com.industrialmaster.cafeapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Profile extends AppCompatActivity {

    //TextView name,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //name=(TextView)findViewById(R.id.name);
        //email=(TextView)findViewById(R.id.email);
        //Bundle bundle = getIntent().getExtras();
        //name.setText(bundle.getString("name"));
        //email.setText(bundle.getString("email"));
    }
}
