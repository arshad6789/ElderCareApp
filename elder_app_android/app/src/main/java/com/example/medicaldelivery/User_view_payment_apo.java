package com.example.medicaldelivery;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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

public class User_view_payment_apo extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {
    String[] medicinetype,medicine,quantity,rate,total,value,mid,statuss,pid;
    public static String mids,amts,amounts,stat,pids,quan;
    SharedPreferences sh;
    ListView l1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_payment_apo);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        l1=(ListView)findViewById(R.id.lvview);
        l1.setOnItemClickListener(this);
        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) User_view_payment_apo.this;
        String q = "/User_view_appo?aid="+UserViewAppointments.appoint_id;
        q = q.replace(" ", "%20");
        JR.execute(q);
    }

    @Override
    public void response(JSONObject jo) {

        try {

            String status = jo.getString("status");
            Log.d("pearl", status);


            if (status.equalsIgnoreCase("success")) {
                JSONArray ja1 = (JSONArray) jo.getJSONArray("data");
                mid = new String[ja1.length()];
                pid = new String[ja1.length()];

                value = new String[ja1.length()];

                for (int i = 0; i < ja1.length(); i++) {
                    mid[i] = ja1.getJSONObject(i).getString("appoinment_id");
                    pid[i]=ja1.getJSONObject(i).getString("appoin_amount");

                    value[i] = "Amount: " + pid[i];

                }
                ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, value);
                l1.setAdapter(ar);
            }
        }
        catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        mids=mid[position];
        amts=pid[position];

      {
            final CharSequence[] items = {"Pay", "Cancel"};

            AlertDialog.Builder builder = new AlertDialog.Builder(User_view_payment_apo.this);
            // builder.setTitle("Add Photo!");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {

                    if (items[item].equals("Pay")) {
                        startActivity(new Intent(getApplicationContext(),User_pay_appoin.class));


                    }


                    else if (items[item].equals("Cancel")) {
                        dialog.dismiss();
                    }

                }

            });
            builder.show();
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