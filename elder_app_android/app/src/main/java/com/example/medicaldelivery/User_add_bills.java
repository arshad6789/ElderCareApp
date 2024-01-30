package com.example.medicaldelivery;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;

public class User_add_bills extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {
    EditText e1, e2,e3,e4;
    Button b1;
    ListView l1;
    public static String user, pass, logid, appo_date,amounts,bill_id,statuss;
    String[] rem,time,stat,value,amount,appo_dates,bill_ids;
    SharedPreferences sh;
    TextView t1;

    DatePickerDialog datePickerDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_add_bills);
        l1=findViewById(R.id.lv1);
        l1.setOnItemClickListener(this);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        startService(new Intent(getApplicationContext(),SocialDistanceAlert.class));

        e1 = (EditText) findViewById(R.id.etuname);
        e2 = (EditText) findViewById(R.id.etpass);
        e3 = (EditText) findViewById(R.id.ettrdate);
        e4 = (EditText) findViewById(R.id.etamount);
        b1 = (Button) findViewById(R.id.loginbtn);

        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) User_add_bills.this;
        String q = "/view_bill_details?uid="+Login.logid;
        q = q.replace(" ", "%20");
        JR.execute(q);

        e2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(User_add_bills.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                // Set the selected time to the EditText in the desired format
                                String selectedTime = String.format("%02d:%02d:00", hourOfDay, minute);
                                e2.setText(selectedTime);
                            }
                        }, hour, minute, false);
                timePickerDialog.show();


            }
        });


        e3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(User_add_bills.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                e3.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = e1.getText().toString();
                pass = e2.getText().toString();
                appo_date=e3.getText().toString();
                amounts=e4.getText().toString();

                if (user.equalsIgnoreCase("")) {
                    e1.setError("Enter your bill details");
                    e1.setFocusable(true);
                }else if (pass.equalsIgnoreCase("")) {
                    e2.setError("Select Time");
                    e2.setFocusable(true);
                }
                else if (appo_date.equalsIgnoreCase("")) {
                    e3.setError("Select date");
                    e3.setFocusable(true);
                }
                else if (amounts.equalsIgnoreCase("")) {
                    e4.setError("Enter the bill amount");
                    e4.setFocusable(true);
                }
                else{
                    JsonReq JR = new JsonReq();
                    JR.json_response = (JsonResponse) User_add_bills.this;
//                    String q = "/reminder?reminder=" + user + "&time=" + pass+"&lid="+Login.logid  ;
                    String q="/User_add_bills?time="+pass+"&lid="+Login.logid+"&date="+appo_date+"&reminder=" + user+"&amounts=" + amounts;
                    q = q.replace(" ", "%20");
                    JR.execute(q);

                }



            }
        });
    }

    @Override
    public void response(JSONObject jo) {
        try {
            String status = jo.getString("status");
            Log.d("pearl", status);
            String method = jo.getString("method");
            Log.d("pearl", status);
            if (method.equalsIgnoreCase("view_bill_details")) {
                JSONArray ja1 = (JSONArray) jo.getJSONArray("data");
                rem = new String[ja1.length()];
                time = new String[ja1.length()];
                amount = new String[ja1.length()];
                appo_dates = new String[ja1.length()];
                stat = new String[ja1.length()];
                bill_ids = new String[ja1.length()];
                value = new String[ja1.length()];

                for (int i = 0; i < ja1.length(); i++) {
                    rem[i] = ja1.getJSONObject(i).getString("reminder");
                    time[i] = ja1.getJSONObject(i).getString("bill_time");
                    appo_dates[i] = ja1.getJSONObject(i).getString("bill_date");
                    amount[i] = ja1.getJSONObject(i).getString("bill_amount");
                    bill_ids[i] = ja1.getJSONObject(i).getString("bill_id");
                    stat[i] = ja1.getJSONObject(i).getString("bill_status");
                    value[i] = "Reminder: " + rem[i] + "\nTime: " + time[i] +  "\nDate: " + appo_dates[i] + "\nAmount: " + amount[i]+ "\nPayment: " + stat[i]+"\n";

                }
                ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), R.layout.cust_list_view, value);
                l1.setAdapter(ar);
//                CustomSerpro a = new CustomSerpro(this, college, desc, email, place, phone, years);
//                l1.setAdapter(a);
            }
            if (method.equalsIgnoreCase("User_add_bills")) {


            if (status.equalsIgnoreCase("success")) {

                Toast.makeText(getApplicationContext(), "Appoint Added Successfully", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), User_add_bills.class));
            } else {
                Toast.makeText(getApplicationContext(), "Cant Added Appoint Please Try Agin Som times", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), User_add_bills.class));
            }
            }
        } catch (Exception e) {
            // TODO: handle exception

            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        bill_id= bill_ids[position];
        amounts = amount[position];
        statuss=stat[position];

        if (statuss.equals("payment pending")) {


            final CharSequence[] items = {"Make Payment", "Cancel"};

            AlertDialog.Builder builder = new AlertDialog.Builder(User_add_bills.this);
            // builder.setTitle("Add Photo!");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {


                    if (items[item].equals("Make Payment")) {
                        startActivity(new Intent(getApplicationContext(), User_make_payment.class));
//                    JsonReq JR=new JsonReq();
//                    JR.json_response=(JsonResponse) User_view_request.this;
//                    String q = "/User_make_payment?log_id="+log_id;
//                    q=q.replace(" ","%20");
//                    JR.execute(q);
                    }
                else if (items[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                }


            });
            builder.show();
//	Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            //startActivityForResult(i, GALLERY_CODE);
        }
        else {

            Toast.makeText(getApplicationContext(), "Already Paid ", Toast.LENGTH_LONG).show();


        }
    }
    //   }
    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(),Userhome.class);
        startActivity(b);
    }

}