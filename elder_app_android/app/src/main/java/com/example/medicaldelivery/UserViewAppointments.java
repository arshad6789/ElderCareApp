package com.example.medicaldelivery;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class UserViewAppointments extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {
    ListView l1;
    String[] name,phone,email,date,time,value,app_id,stat;
    public static String pns,appoint_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_appointments);
        l1=(ListView)findViewById(R.id.lvview);
        l1.setOnItemClickListener(this);
        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) UserViewAppointments.this;
        String q = "/userviewappoinments?lid="+Login.logid;
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
                name = new String[ja1.length()];
                phone = new String[ja1.length()];
                app_id = new String[ja1.length()];
                email = new String[ja1.length()];
                date = new String[ja1.length()];
                time = new String[ja1.length()];
                stat = new String[ja1.length()];
                value = new String[ja1.length()];

                for (int i = 0; i < ja1.length(); i++) {
                    name[i] = ja1.getJSONObject(i).getString("fname")+" "+ja1.getJSONObject(i).getString("lname");
                    phone[i] = ja1.getJSONObject(i).getString("phone");
                    app_id[i] = ja1.getJSONObject(i).getString("appoinment_id");
                    email[i] = ja1.getJSONObject(i).getString("email");
                    date[i] = ja1.getJSONObject(i).getString("date");
                    time[i] = ja1.getJSONObject(i).getString("time");
                    stat[i] = ja1.getJSONObject(i).getString("status");
                    value[i] = "Name: " + name[i]  + "\nphone: " + phone[i] + "\nEmail: " + email[i] + "\nDate: " + date[i] + "\nTime: " + time[i] + "\nStatus: " + stat[i];

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
        appoint_id=app_id[position];
        final CharSequence[] items = {"View Details","Payment","Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(UserViewAppointments.this);
        // builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {


                if (items[item].equals("Make A Call")) {

                    pns=phone[item];

                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:"+pns));//change the number
                    startActivity(callIntent);

//
                }
                else if (items[item].equals("View Details")) {

                    startActivity(new Intent(getApplicationContext(),User_view_food_details.class));
//xxxxxx
                }
                else if (items[item].equals("Payment")) {

                    startActivity(new Intent(getApplicationContext(),User_view_payment_apo.class));
//
                }

                else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }

            }

        });
        builder.show();
    }
}