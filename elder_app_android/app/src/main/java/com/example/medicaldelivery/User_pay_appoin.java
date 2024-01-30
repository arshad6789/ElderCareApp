package com.example.medicaldelivery;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

public class User_pay_appoin extends AppCompatActivity implements JsonResponse {
    EditText e1,e2,e3,e4,e5;
    Button b1;
    SharedPreferences sh;
    String amounts,dates;
    public static String log_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_pay_appoin);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        log_id=sh.getString("log_id","");

        e1=(EditText) findViewById(R.id.amt);
        e2=(EditText) findViewById(R.id.etno);
        e3=(EditText) findViewById(R.id.cvv);
        e4=(EditText) findViewById(R.id.name);
        e5=(EditText) findViewById(R.id.exp);
        b1=(Button) findViewById(R.id.btn2);

        e1.setText(User_view_payment_apo.amts);
        e1.setEnabled(false);
//        e2.setText(User_view_request.dates);
//        e2.setEnabled(false);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                amounts=e1.getText().toString();
                if(amounts.equalsIgnoreCase("")){
                    e1.setError("Amount Required");
                    e1.setFocusable(true);
                }
                else if(e2.equals("")){
                    e2.setError(" Required");
                    e2.setFocusable(true);
                }
                else if(e3.equals("")){
                    e3.setError(" Required");
                    e3.setFocusable(true);
                }
                else if(e4.equals("")){
                    e4.setError(" Required");
                    e4.setFocusable(true);
                }
                else if(e5.equals("")){
                    e5.setError(" Required");
                    e5.setFocusable(true);
                }
                else {

//                dates=e2.getText().toString();
                    JsonReq jr = new JsonReq();
                    jr.json_response = (JsonResponse) User_pay_appoin.this;
                    String q = "/apayment?&Amount=" + User_view_payment_apo.amts + "&log_id=" + Login.logid + "&mid=" + User_view_payment_apo.mids;

                    q.replace("", "%20");
                    jr.execute(q);
                }



            }
        });


    }

    @Override
    public void response(JSONObject jo) {
        try {
            String status=jo.getString("status");
            Log.d("pearl",status);


            if(status.equalsIgnoreCase("success")){


                Toast.makeText(getApplicationContext(), "PAYMENT SUCCESSFULLY", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(),Userhome.class));

            }
//            else if(status.equalsIgnoreCase("duplicate"))
//            {
//                startActivity(new Intent(getApplicationContext(),User_home.class));
//                Toast.makeText(getApplicationContext(), " already Buy...", Toast.LENGTH_LONG).show();
//            }
            else
            {
                startActivity(new Intent(getApplicationContext(),User_make_payment.class));
                Toast.makeText(getApplicationContext(), " failed.TRY AGAIN!!", Toast.LENGTH_LONG).show();
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