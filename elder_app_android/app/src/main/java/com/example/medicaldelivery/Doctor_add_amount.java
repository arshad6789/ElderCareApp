package com.example.medicaldelivery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Doctor_add_amount extends AppCompatActivity implements JsonResponse {

    EditText e1,e2;
    Button b1;
    ListView l1;
    String food,time;
    String[] foods,val,date,times;
    SharedPreferences sh;
    public static String log_id,logid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_add_amount);


        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        log_id=sh.getString("log_id","");
        e1=(EditText) findViewById(R.id.etpro);
        b1=(Button) findViewById(R.id.btnct);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                food = e1.getText().toString();


                if (food.equalsIgnoreCase("")) {
                    e1.setError("Enter Amount");
                    e1.setFocusable(true);
                }

                else {
                    JsonReq jr = new JsonReq();
                    jr.json_response = (JsonResponse) Doctor_add_amount.this;
                    String q = "/Add_amount?log_id=" +Login.logid+"&amt="+food;
                    q.replace("", "%20");
                    jr.execute(q);
//                    Toast.makeText(getApplicationContext(),"log_id : "+log_id,Toast.LENGTH_LONG).show();
//                    startActivity(new Intent(getApplicationContext(),Doctor_update_food_details.class));

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
                Toast.makeText(getApplicationContext(), "AMOUNT SUCCESSFULLY ADDED!...", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), DoctorViewAppointments.class));

            } else if (status.equalsIgnoreCase("duplicate")) {
                startActivity(new Intent(getApplicationContext(), DoctorViewAppointments.class));
                Toast.makeText(getApplicationContext(), "Username and Password already Exist...", Toast.LENGTH_LONG).show();

            } else {
                startActivity(new Intent(getApplicationContext(), DoctorViewAppointments.class));

                Toast.makeText(getApplicationContext(), " failed.TRY AGAIN!!", Toast.LENGTH_LONG).show();
            }


        } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                }
            }
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b = new Intent(getApplicationContext(), DoctorHome.class);
        startActivity(b);
    }
}