package com.industrialmaster.cafeapp;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.util.HashMap;
import java.util.Map;

public class Recharge extends AppCompatActivity {

    EditText editEmail,editCardNo;
    String server_url = "http://cafekdu.000webhostapp.com/recharge.php";
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);

        editEmail = (EditText)findViewById(R.id.email);
        editCardNo = (EditText)findViewById(R.id.card_no);
    }

    public void recharge(View view){
        final String email = editEmail.getText().toString().trim();
        final String card_no = editCardNo.getText().toString().trim();


        if(email.isEmpty()){
            editEmail.setError("Email is required");
            editEmail.requestFocus();
            return;
        }

        if(card_no.isEmpty()){
            editCardNo.setError("Please enter the recharge card number");
            editCardNo.requestFocus();
            return;
        }

        else{
            StringRequest stringRequest  = new StringRequest(Request.Method.POST, server_url, new com.android.volley.Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        String code = jsonObject.getString("code");
                        String message = jsonObject.getString("message");

                        if(code.equals("re_charged")){
                            //builder.setTitle("Server Response");
                            //displayAlert(jsonObject.getString("message"));
                            Toast.makeText(Recharge.this,"Successfully Recharged !",Toast.LENGTH_SHORT).show();

                        }

                        else{
                            Toast.makeText(Recharge.this,"Invalid Card Number !",Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                  // Toast.makeText(Recharge.this,"Successfully Recharged !",Toast.LENGTH_SHORT).show();


                }
            }, new com.android.volley.Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //Toast.makeText(Recharge.this,"Error",Toast.LENGTH_SHORT).show();
                    //error.printStackTrace();
                }
            }
            ){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("card_no",card_no);
                    //params.put("email",email);
                    return params;
                }
            };

            MySingleton.getInstance(Recharge.this).addTorequestqueue(stringRequest);
        }
    }

    private void displayAlert(final String message) {
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                editEmail.setText("");
                editCardNo.setText("");
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
