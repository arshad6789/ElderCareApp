package com.example.medicaldelivery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

public class DoctorRegistration extends AppCompatActivity implements JsonResponse{
    EditText e1,e2,e3,e4,e5,e6,e7,e8,e9;
    Button b1;

    String fname,lname,place,phone,email,user,password,quali,licen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_registration);
        e1=(EditText)findViewById(R.id.etfname);
        e2=(EditText)findViewById(R.id.etlname);
        e3=(EditText)findViewById(R.id.etplace);
        e4=(EditText)findViewById(R.id.etphone);
        e5=(EditText)findViewById(R.id.etemail);
        e6=(EditText)findViewById(R.id.etuser);
        e7=(EditText)findViewById(R.id.etpass);
        e8=(EditText)findViewById(R.id.etlis);
        e9=(EditText)findViewById(R.id.etqual);
        b1=(Button)findViewById(R.id.button);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fname=e1.getText().toString();
                lname=e2.getText().toString();
                place=e3.getText().toString();
                phone=e4.getText().toString();
                email=e5.getText().toString();
                user=e6.getText().toString();
                password=e7.getText().toString();
                licen=e8.getText().toString();
                quali=e9.getText().toString();


                if(fname.equalsIgnoreCase(""))
                {
                    e1.setError("Enter your firstname");
                    e1.setFocusable(true);
                }
                else if(lname.equalsIgnoreCase(""))
                {
                    e2.setError("Enter your lastname");
                    e2.setFocusable(true);
                }

                else if(place.equalsIgnoreCase(""))
                {
                    e3.setError("Enter your place");
                    e3.setFocusable(true);
                }
                else if(phone.equalsIgnoreCase(""))
                {
                    e4.setError("Enter your phone");
                    e4.setFocusable(true);
                }
                else if(email.equalsIgnoreCase(""))
                {
                    e5.setError("Enter your email");
                    e5.setFocusable(true);
                }
                else if(user.equalsIgnoreCase(""))
                {
                    e6.setError("Enter your username");
                    e6.setFocusable(true);
                }
                else if(password.equalsIgnoreCase(""))
                {
                    e7.setError("Enter your password");
                    e7.setFocusable(true);
                }
                else {
                    JsonReq JR = new JsonReq();
                    JR.json_response = (JsonResponse) DoctorRegistration.this;
                    String q ="/doctorregister?fname="+fname+"&lname="+lname+"&place="+place+"&phone="+phone+"&email="+email+"&username="+user+"&password="+password+"&quali="+quali+"&licen="+licen;
                    q = q.replace(" ","%20");
                    JR.execute(q);
                }
            }
        });
    }

    @Override
    public void response(JSONObject jo) {
        try {
            String status = jo.getString("status");
            Log.d("pearl", status);


            if (status.equalsIgnoreCase("success")) {
                Toast.makeText(getApplicationContext(), "REGISTRATION SUCCESS", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), Login.class));

            } else if (status.equalsIgnoreCase("duplicate")) {
                startActivity(new Intent(getApplicationContext(), DoctorRegistration.class));
                Toast.makeText(getApplicationContext(), "Username and Password already Exist...", Toast.LENGTH_LONG).show();

            } else {
                startActivity(new Intent(getApplicationContext(), DoctorRegistration.class));

                Toast.makeText(getApplicationContext(), " failed.TRY AGAIN!!", Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }
}