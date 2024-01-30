package com.example.medicaldelivery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Doctor_update_profile extends AppCompatActivity implements JsonResponse {
    EditText e1,e2,e3,e4,e5,e6,e7;
    Button b1;
    String fname,lname,place,pin,phn,mail;
    SharedPreferences sh;
    String logid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_update_profile);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        logid=sh.getString("logid", "");
        b1=(Button)findViewById(R.id.btup);
        e1=(EditText)findViewById(R.id.etfname);
        e2=(EditText)findViewById(R.id.etlname);
//        e3=(EditText)findViewById(R.id.ethname);
        e4=(EditText)findViewById(R.id.etplace);
//        e5=(EditText)findViewById(R.id.etpin);
        e6=(EditText)findViewById(R.id.etphn);
        e7=(EditText)findViewById(R.id.etmail);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(e1.getText().toString().equals(""))
                {
                    e1.setError("Enter a  name");
                    e1.setFocusable(true);
                }
                    if(e2.getText().toString().equals(""))
                    {
                        e2.setError("Enter a  name");
                        e2.setFocusable(true);
                }
                if (e4.getText().toString().equals("")) {
                    e4.setError("Enter a place");
                    e4.setFocusable(true);
                }
//                else if(e5.getText().toString().equals(""))
//                {
//                    e5.setError("Enter a number");
//                    e5.setFocusable(true);
//                }
                else if (e6.getText().toString().equals("")) {
                    e6.setError("Enter a number");
                    e6.setFocusable(true);
                } else if (e7.getText().toString().equals("")) {
                    e7.setError("Enter a email");
                    e7.setFocusable(true);
                } else if (e6.getText().toString().length() != 10) {
                    e6.setError("Phone number should be of length 10");
                    e6.setFocusable(true);
                }
//				else if(!Patterns.EMAIL_ADDRESS.matcher(mail).matches())
//					{
//						e7.setError("Enter a email");
//						e7.setFocusable(true);
//					}
                else {
//                    hname=e3.getText().toString();
                    fname = e1.getText().toString();
                    lname = e2.getText().toString();
                    place = e4.getText().toString();
                    phn = e6.getText().toString();
                    mail = e7.getText().toString();
                    JsonReq jr = new JsonReq();
                    jr.json_response = (JsonResponse) Doctor_update_profile.this;
                    String q = "/userupprof?place=" + place + "&fname=" + fname+ "&lname=" + lname+ "&pincode=" + pin + "&email=" + mail + "&phone=" + phn + "&logid=" + Login.logid;
                    q.replace("", "%20");
                    jr.execute(q);
                }
            }
        });

        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse)Doctor_update_profile.this;
        String q = "/userviewprofile/?login_id="+Login.logid;
        q=q.replace(" ","%20");
        JR.execute(q);


    }
    @Override
    public void response(JSONObject jo) {
        try {

            String method = jo.getString("method");
            if (method.equalsIgnoreCase("userviewprofile")) {

                String status = jo.getString("status");
                Log.d("pearl", status);
//			Toast.makeText(getApplicationContext(),status, Toast.LENGTH_SHORT).show();
                if (status.equalsIgnoreCase("success")) {

                    JSONArray ja1 = (JSONArray) jo.getJSONArray("data");

                    e1.setText(ja1.getJSONObject(0).getString("fname"));
                    e2.setText(ja1.getJSONObject(0).getString("lname"));
                    e4.setText(ja1.getJSONObject(0).getString("place"));
//                    e5.setText(ja1.getJSONObject(0).getString("pincode"));
                    e6.setText(ja1.getJSONObject(0).getString("phone"));
                    e7.setText(ja1.getJSONObject(0).getString("email"));
                } else {
                    Toast.makeText(getApplicationContext(), "FAILED TO LOAD DATA.....", Toast.LENGTH_LONG).show();
                }
            }
            if (method.equalsIgnoreCase("userupprof")) {
                String status = jo.getString("status");
                Toast.makeText(getApplicationContext(), status, Toast.LENGTH_LONG).show();
                if (status.equalsIgnoreCase("success")) {
                    Toast.makeText(getApplicationContext(), "Profile Updated Successfully", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), DoctorHome.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Failed to update. Try Again!!!", Toast.LENGTH_LONG).show();
                }
            }

        } catch (Exception e) {
            // TODO: handle exception

            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }
}