package com.example.medicaldelivery;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Userregister extends AppCompatActivity implements JsonResponse{
    EditText e1,e2,e3,e4,e5,e6,e7,e8,e9,e10,e11;
    Button b1;

    String fname,lname,gender,dob,housename,place,phone,email,user,password,pincode,district;
    private DatePickerDialog fromDatePickerDialog;

    private SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userregister);
       // startService(new Intent(getApplicationContext(),LocationService.class));
        e1=(EditText)findViewById(R.id.etfname);
        e2=(EditText)findViewById(R.id.etlname);
//        e3=(EditText)findViewById(R.id.ethouse);
//        e4=(EditText)findViewById(R.id.etpin);
        e10=(EditText)findViewById(R.id.etdis);
        e11=(EditText)findViewById(R.id.dob);
        e5=(EditText)findViewById(R.id.etplace);
        e6=(EditText)findViewById(R.id.etphone);
        e7=(EditText)findViewById(R.id.etemail);
        e8=(EditText)findViewById(R.id.etuser);
        e9=(EditText)findViewById(R.id.etpass);
        b1=(Button)findViewById(R.id.button);
//        r1=(RadioButton)findViewById(R.id.radioButton);
//        r2=(RadioButton)findViewById(R.id.radioButton2);

        e11.setInputType(InputType.TYPE_NULL);
        e11.requestFocus();
        setDateTimeField();
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fname=e1.getText().toString();
                lname=e2.getText().toString();
//                housename=e3.getText().toString();
//                pincode=e4.getText().toString();
                district=e10.getText().toString();
                dob=e11.getText().toString();
                place=e5.getText().toString();
                phone=e6.getText().toString();
                email=e7.getText().toString();
                user=e8.getText().toString();
                password=e9.getText().toString();

                if(fname.equalsIgnoreCase(""))
                {
                    e1.setError("Enter your firstname");
                    e1.setFocusable(true);
                }
                else if(lname.equalsIgnoreCase(""))
                {
                    e2.setError("Enter your lastname");
                    e2.setFocusable(true);
                }

                else if(place.equalsIgnoreCase(""))
                {
                    e5.setError("Enter your place");
                    e5.setFocusable(true);
                }
                else if(phone.equalsIgnoreCase(""))
                {
                    e6.setError("Enter your phone");
                    e6.setFocusable(true);
                }
                else if(email.equalsIgnoreCase(""))
                {
                    e7.setError("Enter your email");
                    e7.setFocusable(true);
                }
                else if(user.equalsIgnoreCase(""))
                {
                    e8.setError("Enter your username");
                    e8.setFocusable(true);
                }
                else if(password.equalsIgnoreCase(""))
                {
                    e9.setError("Enter your password");
                    e9.setFocusable(true);
                }
                else {
                    JsonReq JR = new JsonReq();
                    JR.json_response = (JsonResponse) Userregister.this;
                    String q ="/userregister?fname="+fname+"&lname="+lname+"&place="+place+"&phone="+phone+"&email="+email+"&username="+user+"&password="+password+"&district="+district+"&dob="+dob;
                    q = q.replace(" ","%20");
                    JR.execute(q);
                }
            }
        });
    }

    private void setDateTimeField() {

        e11.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                fromDatePickerDialog.show();
            }
        });
        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog =new DatePickerDialog(this,new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                e11.setText(dateFormatter.format(newDate.getTime()));
//                bdate=e2.getText().toString();

            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void response(JSONObject jo) {

        try {
            String status = jo.getString("status");
            Log.d("pearl", status);


            if (status.equalsIgnoreCase("success")) {
                Toast.makeText(getApplicationContext(), "REGISTRATION SUCCESS", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), Login.class));

            } else if (status.equalsIgnoreCase("duplicate")) {
                startActivity(new Intent(getApplicationContext(), Userregister.class));
                Toast.makeText(getApplicationContext(), "Username and Password already Exist...", Toast.LENGTH_LONG).show();

            } else {
                startActivity(new Intent(getApplicationContext(), Userregister.class));

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
        Intent b=new Intent(getApplicationContext(),Login.class);
        startActivity(b);
    }
}