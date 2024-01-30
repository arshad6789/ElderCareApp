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
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class UserViewDoctorPrescription extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {
    String[] file,date,value,statuss,pidss;
    public static String files,dates,stat;
    SharedPreferences sh;
    ListView l1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_doctor_prescription);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        l1=(ListView)findViewById(R.id.lvview);
        l1.setOnItemClickListener(this);
        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) UserViewDoctorPrescription.this;
        String q = "/user_view_doctor_prescription?appoi_id="+UserViewAppointments.appoint_id;
        q = q.replace(" ", "%20");
        JR.execute(q);
    }

    @Override
    public void response(JSONObject jo) {
        try {


                String status=jo.getString("status");
                Log.d("pearl",status);


                if(status.equalsIgnoreCase("success")){
                    JSONArray ja1=(JSONArray)jo.getJSONArray("data");
                    file=new String[ja1.length()];
                    date=new String[ja1.length()];
//                    statuss=new String[ja1.length()];
                    date=new String[ja1.length()];

                    for(int i = 0;i<ja1.length();i++)
                    {
                        file[i]=ja1.getJSONObject(i).getString("file");
                        date[i]=ja1.getJSONObject(i).getString("date");
//                        statuss[i]=ja1.getJSONObject(i).getString("status");


                    }
//
                    Custimage1 cc=new Custimage1(this,file,date);
                    l1.setAdapter(cc);

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
        files=file[position];
        dates=date[position];
        stat=statuss[position];

//        final CharSequence[] items = {"Upload Prescription to medicalshop","Cancel"};
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(UserViewDoctorPrescription.this);
//        // builder.setTitle("Add Photo!");
//        builder.setItems(items, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int item) {
//
//
//                if (items[item].equals("Upload Prescription to medicalshop")) {
//
//                   startActivity(new Intent(getApplicationContext(),user_uploaddoctor_prescription_to_medicalshop.class));
//                }
//
//                else if (items[item].equals("Cancel")) {
//                    dialog.dismiss();
//                }
//
//            }
//
//        });
//        builder.show();
    }
}