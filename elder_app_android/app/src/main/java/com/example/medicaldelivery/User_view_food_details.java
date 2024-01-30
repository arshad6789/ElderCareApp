package com.example.medicaldelivery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class User_view_food_details extends AppCompatActivity implements JsonResponse {
    ListView lv1;
    String amount,logid,ress_id;

    String[] foods,times,all;
    SharedPreferences sh,sh1,sh2;
    String men_id;
    public static String log_id,product_ids;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_food_details);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        log_id=sh.getString("log_id","");

        lv1=(ListView)findViewById(R.id.lvreq);

        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) User_view_food_details.this;
        String q = "/User_view_food_details?log_id=" +Login.logid + "&appoint_id="+ UserViewAppointments.appoint_id;
        q=q.replace(" ","%20");
        JR.execute(q);
    }

    @Override
    public void response(JSONObject jo) {
        try {
            String method=jo.getString("method");

            if(method.equalsIgnoreCase("User_view_food_details")){

                String status=jo.getString("status");
                Log.d("pearl",status);
                Toast.makeText(getApplicationContext(), status, Toast.LENGTH_LONG).show();


                if(status.equalsIgnoreCase("success")){

                    JSONArray ja1=(JSONArray)jo.getJSONArray("data");
//                    accusedperson_id=new String[ja1.length()];

                    times=new String[ja1.length()];
                    foods=new String[ja1.length()];
                    all=new String[ja1.length()];
                    for(int i = 0;i<ja1.length();i++)
                    {
                        times[i]=ja1.getJSONObject(i).getString("food_tyme");
                        foods[i]=ja1.getJSONObject(i).getString("food_details");
                        all[i]="\nTime:"+times[i]+"\nDetails :   "+foods[i];
                    }
                    ArrayAdapter<String> ar=new ArrayAdapter<String>(getApplicationContext(),R.layout.cust_list_view,all);
                    lv1.setAdapter(ar);
//                    view_report_costum view_report_costum=new view_report_costum(this,Immigrationname,Report,Details);
//                    lv1.setAdapter(view_report_costum);
////                    lv1.setOnItemClickListener(this);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), " FAILED TO LOAD DATA.....TRY AGAIN!!", Toast.LENGTH_LONG).show();
                }

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
        Intent b = new Intent(getApplicationContext(), Userhome.class);
        startActivity(b);
    }

}