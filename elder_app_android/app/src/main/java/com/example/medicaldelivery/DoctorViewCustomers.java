package com.example.medicaldelivery;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class DoctorViewCustomers extends AppCompatActivity implements JsonResponse{
    ListView l1;
    String[] name,phone,email,place,value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_view_customers);
        l1=(ListView)findViewById(R.id.lvview);
//        l1.setOnItemClickListener(this);

        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) DoctorViewCustomers.this;
        String q = "/doctorview_customers?app_id="+DoctorViewAppointments.appoint_id;
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
                email = new String[ja1.length()];
                place = new String[ja1.length()];
                value = new String[ja1.length()];

                for (int i = 0; i < ja1.length(); i++) {
                    name[i] = ja1.getJSONObject(i).getString("firstname")+" "+ja1.getJSONObject(i).getString("lastname");
                    phone[i] = ja1.getJSONObject(i).getString("phone");
                    email[i] = ja1.getJSONObject(i).getString("email");
                    place[i] = ja1.getJSONObject(i).getString("place");
                    value[i] = "Name: " + name[i]  + "\nPlace: " + place[i] + "\nPhone: " + phone[i] + "\nEmail: " + email[i] ;

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
}