package com.industrialmaster.cafeapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;



public class SignUp extends AppCompatActivity {

    EditText editName,editEmail,editTelephone,editPass,editRePass;
    String server_url = "http://cafekdu.000webhostapp.com/signup.php";
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editName = (EditText)findViewById(R.id.editName);
        editEmail = (EditText)findViewById(R.id.editEmail);
        editTelephone = (EditText)findViewById(R.id.editTelephone);
        editPass = (EditText)findViewById(R.id.editPass);
        editRePass = (EditText)findViewById(R.id.editRePass);
        builder = new AlertDialog.Builder(SignUp.this);
    }

    public void signup(View view){
        final String name = editName.getText().toString().trim();
        final String email = editEmail.getText().toString().trim();
        final String tele = editTelephone.getText().toString().trim();
        final String pass = editPass.getText().toString().trim();
        final String repass = editRePass.getText().toString().trim();

        if(name.isEmpty()){
            editName.setError("Name is required");
            editName.requestFocus();
            return;
        }

        if(email.isEmpty()){
            editEmail.setError("Email is required");
            editEmail.requestFocus();
            return;
        }


        if(tele.isEmpty()){
            editTelephone.setError("Name is required");
            editTelephone.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editEmail.setError("Enter a valid Email");
            editEmail.requestFocus();
            return;
        }

        if(pass.isEmpty()){
            editPass.setError("Password is required");
            editPass.requestFocus();
            return;
        }

        if(pass.length() < 6){
            editPass.setError("Password should me minimum 6 charachter long!");
            editPass.requestFocus();
            return;
        }


        if(repass.isEmpty()){
            editRePass.setError("Re-Enter the password");
            editRePass.requestFocus();
            return;
        }
        if(!repass.equals(pass)){
            editRePass.setError("Password doesn't match");
            //editRePass.getText().clear();
            editRePass.requestFocus();
        }

        /*User registration using Volley*/
        else{
            StringRequest stringRequest  = new StringRequest(Request.Method.POST, server_url, new com.android.volley.Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        String code = jsonObject.getString("code");
                        String message = jsonObject.getString("message");

                        builder.setTitle("Server Response");
                        builder.setMessage(message);
                        displayAlert(code);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }, new com.android.volley.Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(SignUp.this,"Eror",Toast.LENGTH_SHORT).show();
                    error.printStackTrace();
                }
            }
            ){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("name",name);
                    params.put("email",email);
                    params.put("password", pass);
                    params.put("telephone",tele);
                    return params;
                }
            };

            MySingleton.getInstance(SignUp.this).addTorequestqueue(stringRequest);
        }

    }

    private void displayAlert(final String code) {
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if(code.equals("input_error")){
                    editPass.setText("");
                    editRePass.setText("");
                }

                else if(code.equals("reg_success")){
                    finish();
                }

                else if(code.equals("reg_failed")){
                    editPass.setText("");
                    editRePass.setText("");
                    editTelephone.setText("");
                    editName.setText("");
                    editPass.setText("");
                    editEmail.setText("");
                }


            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    public void tologin(View view){
        Intent intent = new Intent(getApplicationContext(),Login.class);
        startActivity(intent);
    }
}
