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

public class Doctor_update_food_details extends AppCompatActivity implements JsonResponse {

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
        setContentView(R.layout.activity_doctor_update_food_details);
        e1=(EditText) findViewById(R.id.etpro);
        e2=(EditText) findViewById(R.id.etdetail);
        l1=(ListView) findViewById(R.id.lvmenu);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        log_id=sh.getString("log_id","");
        b1=(Button) findViewById(R.id.btnct);

        JsonReq jr = new JsonReq();
        jr.json_response = (JsonResponse) Doctor_update_food_details.this;
        String q = "/viewfood?log_id=" +Login.logid + "&appoint_id="+ DoctorViewAppointments.appoint_id;
        q.replace("", "%20");
        jr.execute(q);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                food = e1.getText().toString();
                time = e2.getText().toString();

                if (food.equalsIgnoreCase("")) {
                    e1.setError("Enter  Product");
                    e1.setFocusable(true);
                }
                else if (time.equalsIgnoreCase("")) {
                    e2.setError("Enter Product Details");
                    e2.setFocusable(true);
                }

                else {
                    JsonReq jr = new JsonReq();
                    jr.json_response = (JsonResponse) Doctor_update_food_details.this;
                    String q = "/Doctor_update_food_details?log_id=" +Login.logid + "&food=" + food+ "&time=" + time+  "&appoint_id="+ DoctorViewAppointments.appoint_id;

                    q.replace("", "%20");
                    jr.execute(q);
//                    Toast.makeText(getApplicationContext(),"log_id : "+log_id,Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(),Doctor_update_food_details.class));

                }
            }
        });

    }

    @Override
    public void response(JSONObject jo) {
        try {

            String method=jo.getString("method");
            Log.d("pearl",method);


            if(method.equalsIgnoreCase("viewfood")) {

                String status = jo.getString("status");
                if (status.equalsIgnoreCase("success")) {
                    JSONArray ja = (JSONArray) jo.getJSONArray("data");

                    foods = new String[ja.length()];
                    times = new String[ja.length()];
                    val = new String[ja.length()];


                    for (int i = 0; i < ja.length(); i++) {
                        foods[i] = ja.getJSONObject(i).getString("food_details");
                        times[i] = ja.getJSONObject(i).getString("food_tyme");

                        val[i] = "\nTime : " + times[i] + "\nDetails : " + foods[i];
                    }
                    ArrayAdapter<String> ar=new ArrayAdapter<String>(getApplicationContext(),R.layout.cust_list_view,val);
                    l1.setAdapter(ar);

//                    l1.setAdapter(new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, val));
                    {

                    }
                }
            }
            if (method.equalsIgnoreCase("Doctor_update_food_details")) {
                try {
                    String status = jo.getString("status");
                    Log.d("pearl", status);


                    if (status.equalsIgnoreCase("success")) {
                        Toast.makeText(getApplicationContext(), " Details Add Successfully", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext(), Doctor_update_food_details.class));

                    }

                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        }catch (Exception e) {
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