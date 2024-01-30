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
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class user_uploaddoctor_prescription_to_medicalshop extends AppCompatActivity implements JsonResponse, AdapterView.OnItemSelectedListener{
    Spinner s1;
    Button b1;
    String medicalshop,id;
    String [] medical,mid,value;
    SharedPreferences sh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_uploaddoctor_prescription_to_medicalshop);

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        s1=(Spinner)findViewById(R.id.etpl);
        b1=(Button)findViewById(R.id.button_reg);

        s1.setOnItemSelectedListener(this);

        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse)user_uploaddoctor_prescription_to_medicalshop.this;
        String q="/viewmedicalshop";
        q = q.replace(" ", "%20");
        JR.execute(q);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                JsonReq JR=new JsonReq();
                JR.json_response=(JsonResponse) user_uploaddoctor_prescription_to_medicalshop.this;
                String q = "/user_upload_doctor_prescription_to_medical_shop?file="+UserViewDoctorPrescription.files+"&date="+UserViewDoctorPrescription.dates+"&statusss="+UserViewDoctorPrescription.stat+"&medicalshop="+id+"&lid="+Login.logid;
                q=q.replace(" ","%20");
                JR.execute(q);

            }
        });

    }

    @Override
    public void response(JSONObject jo) {
        try {

            String method = jo.getString("method");

            if (method.equalsIgnoreCase("viewmedicalshop")) {


                String status = jo.getString("status");
                Log.d("pearl", status);


                if (status.equalsIgnoreCase("success")) {
                    JSONArray ja1 = (JSONArray) jo.getJSONArray("data");
                    mid = new String[ja1.length()];
                    medical = new String[ja1.length()];
                    value = new String[ja1.length()];

                    for (int i = 0; i < ja1.length(); i++) {
                        mid[i] = ja1.getJSONObject(i).getString("medicalshop_id");
                        medical[i] = ja1.getJSONObject(i).getString("shopname");
                        value[i] = medical[i];

                    }
                    ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, value);
                    s1.setAdapter(ar);
                }
            }
            else if (method.equalsIgnoreCase("user_upload_doctor_prescription_to_medical_shop")) {

                String status = jo.getString("status");
                Log.d("pearl", status);


                if (status.equalsIgnoreCase("success")) {


                    Toast.makeText(getApplicationContext(), "SUCCESS", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), UserViewAppointments.class));

                } else if (status.equalsIgnoreCase("duplicate")) {
                    startActivity(new Intent(getApplicationContext(), user_uploaddoctor_prescription_to_medicalshop.class));
                    Toast.makeText(getApplicationContext(), "Username already Exist...", Toast.LENGTH_LONG).show();
                } else {
                    startActivity(new Intent(getApplicationContext(), user_uploaddoctor_prescription_to_medicalshop.class));
                    Toast.makeText(getApplicationContext(), " failed.TRY AGAIN!!", Toast.LENGTH_LONG).show();
                }

            }


        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        id=mid[i];
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}