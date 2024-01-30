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

public class Usermakepayment extends AppCompatActivity implements JsonResponse{


    EditText e1,e2,e3,e4,e5;
    Button b1;
    String card,cvv,exp,name,amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usermakepayment);
        e1=(EditText)findViewById(R.id.etcard);
        e2=(EditText)findViewById(R.id.etcvv);
        e3=(EditText)findViewById(R.id.etexp);
        e4=(EditText)findViewById(R.id.etname);
        e5=(EditText)findViewById(R.id.etamount);
        e5.setText(Userviewuploadedmedicine.amounts);
        e5.setEnabled(false);

        b1=(Button)findViewById(R.id.button);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                card=e1.getText().toString();
                cvv=e2.getText().toString();
                exp=e3.getText().toString();
                name=e4.getText().toString();
                amount=e5.getText().toString();

                JsonReq JR=new JsonReq();
                JR.json_response=(JsonResponse) Usermakepayment.this;
                String q="/usermakepayment?pid="+Userviewuploadedmedicine.pids+"&amount="+Userviewuploadedmedicine.amounts+"&mid="+Userviewuploadedmedicine.mids+"&quantity="+Userviewuploadedmedicine.quan;
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
                Toast.makeText(getApplicationContext(), "PAID SUCCESSFULLY", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), Usermakepayment.class));

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
    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(),Userhome.class);
        startActivity(b);
    }
}