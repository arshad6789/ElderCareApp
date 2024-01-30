package com.example.medicaldelivery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

public class UserMakeAppointment extends AppCompatActivity implements JsonResponse{

    EditText e1,e2;
    Button b1;
    String date,time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_make_appointment);

        e1=(EditText)findViewById(R.id.etdate);
        e2=(EditText)findViewById(R.id.ettime);

        b1=(Button)findViewById(R.id.button);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date=e1.getText().toString();
                time=e2.getText().toString();

                JsonReq JR=new JsonReq();
                JR.json_response=(JsonResponse) UserMakeAppointment.this;
                String q="/usermakeappointments?did="+UserViewDoctors.docc_ids+"&date="+date+"&time="+time+"&lid="+Login.logid;
                q=q.replace(" ","%20");
                JR.execute(q);
            }
        });
    }

    @Override
    public void response(JSONObject jo) {
        try {


            String status = jo.getString("status");
            Log.d("pearl", status);


            if (status.equalsIgnoreCase("success")) {
                Toast.makeText(getApplicationContext(), "ADDED SUCCESSFULLY", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), UserViewAppointments.class));

            } else {

                Toast.makeText(getApplicationContext(), " failed.TRY AGAIN!!", Toast.LENGTH_LONG).show();
            }



        }

        catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }

}