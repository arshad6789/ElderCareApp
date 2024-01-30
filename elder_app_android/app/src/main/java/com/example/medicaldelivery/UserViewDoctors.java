package com.example.medicaldelivery;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class UserViewDoctors extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {
    ListView l1;
    String[] name,place,phone,email,value,doct_id;
    public static String docc_ids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_doctors);

        l1=(ListView)findViewById(R.id.lvview);
        l1.setOnItemClickListener(this);
        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) UserViewDoctors.this;
        String q = "/userview_doctors";
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
                place = new String[ja1.length()];
                phone = new String[ja1.length()];
                name = new String[ja1.length()];
                email = new String[ja1.length()];
                doct_id = new String[ja1.length()];
                value = new String[ja1.length()];

                for (int i = 0; i < ja1.length(); i++) {
                    doct_id[i] = ja1.getJSONObject(i).getString("doctor_id");
                    place[i] = ja1.getJSONObject(i).getString("place");
                    phone[i] = ja1.getJSONObject(i).getString("phone");
                    email[i] = ja1.getJSONObject(i).getString("email");
                    name[i] = ja1.getJSONObject(i).getString("fname")+" "+ja1.getJSONObject(i).getString("lname");
                    value[i] = "Name: " + name[i] + "\nPlace: " + place[i] + "\nphone: " + phone[i] + "\nEmail: " + email[i];

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
        docc_ids=doct_id[position];

        final CharSequence[] items = {"Make Appointment","Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(UserViewDoctors.this);
        // builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {


                if (items[item].equals("Make Appointment")) {

                    startActivity(new Intent(getApplicationContext(),Addreminter.class));
                }

                else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }

            }

        });
        builder.show();
    }
}