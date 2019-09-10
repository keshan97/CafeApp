package com.industrialmaster.cafeapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    EditText UserName, Password;
    String username, password;
    String login_url = "http://cafekdu.000webhostapp.com/login.php";
    AlertDialog.Builder builder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        builder = new AlertDialog.Builder(Login.this);
        UserName = (EditText)findViewById(R.id.username);
        Password = (EditText)findViewById(R.id.password);
    }


    public void login (View view){

        username =UserName.getText().toString().trim();
        password = Password.getText().toString().trim();

        if(username.isEmpty()){
            UserName.setError("Email is required");
            UserName.requestFocus();
            return;
        }

        if(password.isEmpty()){
            Password.setError("Password is required");
            Password.requestFocus();
            return;
        }


        else{
            StringRequest stringRequest  = new StringRequest(Request.Method.POST, login_url, new com.android.volley.Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        String code = jsonObject.getString("code");

                        if(code.equals("login_failed")){
                            builder.setTitle("Login Error");
                            displayAlert(jsonObject.getString("message"));
                        }

                        else{
                            Intent intent = new Intent(Login.this,Dashboard.class);
                            startActivity(intent);

                            //Intent intent1 = new Intent(Login.this,Profile.class);
                            //Bundle bundle = new Bundle();
                            //bundle.putString("name",jsonObject.getString("name"));
                            //bundle.putString("email",jsonObject.getString("email"));
                        }





                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }, new com.android.volley.Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Login.this,"Error",Toast.LENGTH_SHORT).show();
                    error.printStackTrace();
                }
            }
            ){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("email",username);
                    params.put("password",password);
                    return params;
                }
            };

            MySingleton.getInstance(Login.this).addTorequestqueue(stringRequest);
        }




    }


    public void tosignup(View view){
        Intent intent = new Intent(getApplicationContext(),SignUp.class);
        startActivity(intent);
    }

    private void displayAlert(final String message) {
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                UserName.setText("");
                Password.setText("");
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
