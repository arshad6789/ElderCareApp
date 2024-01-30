package com.example.medicaldelivery;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class User_view_medication_details extends AppCompatActivity implements JsonResponse {
    EditText e1, e2,e3;
    Button b1;
    public static String user, pass, logid, appo_date,medication_ids;
    String[] rem,time,stat,value,date,medication_id;
    SharedPreferences sh;
    ListView lv1;
    public static String req_id,lts,lgs,log_id,u_id,p_id,am_id,amounts,dates,status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_medication_details);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        log_id=sh.getString("log_id","");

        lv1=(ListView)findViewById(R.id.lvreq);
//        lv1.setOnItemClickListener(this);


        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) User_view_medication_details.this;
        String q = "/User_view_medication_details?log_id="+log_id;
        q=q.replace(" ","%20");
        JR.execute(q);


    }

    @Override
    public void response(JSONObject jo) {
        try {

            String status = jo.getString("status");
            Log.d("pearl", status);


            if (status.equalsIgnoreCase("success")) {
                JSONArray ja1 = (JSONArray) jo.getJSONArray("data");
//                name[i] = ja1.getJSONObject(i).getString("fname")+" "+ja1.getJSONObject(i).getString("lname");
                rem = new String[ja1.length()];
                time = new String[ja1.length()];
                stat = new String[ja1.length()];
                value = new String[ja1.length()];
                for (int i = 0; i < ja1.length(); i++) {
                    rem[i] = ja1.getJSONObject(i).getString("reminder");
                    time[i] = ja1.getJSONObject(i).getString("time");
                    stat[i] = ja1.getJSONObject(i).getString("date");
                    value[i] = "Details: " + rem[i] + "\nTime: " + time[i] + "\nDate: " + stat[i];

                }
                ArrayAdapter<String> ar=new ArrayAdapter<String>(getApplicationContext(),R.layout.cust_news_view,value);
                lv1.setAdapter(ar);
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }

    }
    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(),Userhome.class);
        startActivity(b);
    }
}