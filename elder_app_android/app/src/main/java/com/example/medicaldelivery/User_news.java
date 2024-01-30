package com.example.medicaldelivery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class User_news extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener  {
    ListView lv1;
    String amount,logid,ress_id;

    String[] news,all,news_date,news_heading,news_id;
    SharedPreferences sh,sh1,sh2;
    String men_id;
    public static String log_id,product_ids,news_ids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_news);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        log_id=sh.getString("log_id","");

        lv1=(ListView)findViewById(R.id.lvreq);
        lv1.setOnItemClickListener(this);
        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) User_news.this;
        String q = "/User_news";
        q=q.replace(" ","%20");
        JR.execute(q);



    }

    @Override
    public void response(JSONObject jo) {
        try {
            String method=jo.getString("method");

            if(method.equalsIgnoreCase("User_news")){

                String status=jo.getString("status");
                Log.d("pearl",status);
                Toast.makeText(getApplicationContext(), status, Toast.LENGTH_LONG).show();


                if(status.equalsIgnoreCase("success")){

                    JSONArray ja1=(JSONArray)jo.getJSONArray("data");
//                    accusedperson_id=new String[ja1.length()];

                    news=new String[ja1.length()];
                    news_heading=new String[ja1.length()];
                    news_date=new String[ja1.length()];
                    news_id=new String[ja1.length()];

                    all=new String[ja1.length()];
                    for(int i = 0;i<ja1.length();i++)
                    {
                        news[i]=ja1.getJSONObject(i).getString("summary");
                        news_heading[i]=ja1.getJSONObject(i).getString("news_heading");
                        news_date[i]=ja1.getJSONObject(i).getString("news_date");
                        news_id[i]=ja1.getJSONObject(i).getString("type_id");
                        all[i]="\nDate:"+news_date[i]+"\nHeading :   "+news_heading[i];
                    }
                    ArrayAdapter<String> ar=new ArrayAdapter<String>(getApplicationContext(),R.layout.cust_news_view,all);
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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        news_ids = news_id[i];
        startActivity(new Intent(getApplicationContext(), User_view_news.class));
    }
}