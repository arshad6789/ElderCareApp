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
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;

public class User_medication_details extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {
    EditText e1, e2,e3;
    Button b1;
    ListView l1;
    public static String user, pass, logid, appo_date,medication_ids,weekly,daily;
    String[] rem,time,stat,value,date,medication_id;
    SharedPreferences sh;
    TextView t1;
    DatePickerDialog datePickerDialog;
    RadioButton r1,r2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_medication_details);
        l1=findViewById(R.id.lv1);
        l1.setOnItemClickListener(this);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//        startService(new Intent(getApplicationContext(),SocialDistanceAlert.class));
        startService(new Intent(getApplicationContext(),LocationService.class));

        e1 = (EditText) findViewById(R.id.etuname);
        e2 = (EditText) findViewById(R.id.etpass);
        e3 = (EditText) findViewById(R.id.ettrdate);
        b1 = (Button) findViewById(R.id.loginbtn);
        r1=(RadioButton)findViewById(R.id.radio0);
        r2=(RadioButton)findViewById(R.id.radio1);

        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) User_medication_details.this;
        String q = "/view_medication?uid="+Login.logid+"&patient_id="+ DoctorViewAppointments.patient_ids;
        q = q.replace(" ", "%20");
        JR.execute(q);

        e2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(User_medication_details.this,
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
                datePickerDialog = new DatePickerDialog(User_medication_details.this,
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

                if(r1.isChecked())
                {
                    daily="daily";
                }
                if(r2.isChecked())
                {
                    weekly="weekly";
                }

                if (user.equalsIgnoreCase("")) {
                    e1.setError("Enter your reminder");
                    e1.setFocusable(true);
                } else if (pass.equalsIgnoreCase("")) {
                    e2.setError("Select Time");
                    e2.setFocusable(true);
                }
                else if (appo_date.equalsIgnoreCase("")) {
                    e3.setError("Select date");
                    e3.setFocusable(true);
                }

                else{
                    JsonReq JR = new JsonReq();
                    JR.json_response = (JsonResponse) User_medication_details.this;
//                    String q = "/reminder?reminder=" + user + "&time=" + pass+"&lid="+Login.logid  ;
                    String q="/User_medication_details?time="+pass+"&lid="+Login.logid+"&date="+appo_date+"&reminder=" + user+"&patient_id="+ DoctorViewAppointments.patient_ids+"&daily="+daily+"&weekly="+weekly;
                    Toast.makeText(getApplicationContext(),"username : "+DoctorViewAppointments.patient_ids, Toast.LENGTH_LONG).show();

                    q = q.replace(" ", "%20");
                    JR.execute(q);
//                    startService(new Intent(getApplicationContext(),SocialDistanceAlert.class));

                }



            }
        });
    }

    @Override
    public void response(JSONObject jo) {
//        startService(new Intent(getApplicationContext(),SocialDistanceAlert.class));
//        startService(new Intent(getApplicationContext(),LocationService.class));
        try {
//            startService(new Intent(getApplicationContext(),SocialDistanceAlert.class));
//            startService(new Intent(getApplicationContext(),LocationService.class));
            String status = jo.getString("status");
            Log.d("pearl", status);
            String method = jo.getString("method");
            Log.d("pearl", status);
            if (method.equalsIgnoreCase("view_medication")) {
                JSONArray ja1 = (JSONArray) jo.getJSONArray("data");
                rem = new String[ja1.length()];
                time = new String[ja1.length()];
                date = new String[ja1.length()];
                medication_id = new String[ja1.length()];
                value = new String[ja1.length()];
//                startService(new Intent(getApplicationContext(),SocialDistanceAlert.class));
//                startService(new Intent(getApplicationContext(),LocationService.class));

                for (int i = 0; i < ja1.length(); i++) {
                    rem[i] = ja1.getJSONObject(i).getString("reminder");
                    time[i] = ja1.getJSONObject(i).getString("time");
                    date[i] = ja1.getJSONObject(i).getString("date");
                    medication_id[i] = ja1.getJSONObject(i).getString("medication_id");
                    value[i] = "Reminder: " + rem[i] + "\nTime: " + time[i] + "\nDate: " + date[i]+"\n";
//                    startService(new Intent(getApplicationContext(),SocialDistanceAlert.class));
//                    startService(new Intent(getApplicationContext(),LocationService.class));

                }
                ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), R.layout.cust_list_view, value);
                l1.setAdapter(ar);
//                CustomSerpro a = new CustomSerpro(this, college, desc, email, place, phone, years);
//                l1.setAdapter(a);
            }
            if (method.equalsIgnoreCase("User_medication_details")) {


            if (status.equalsIgnoreCase("success")) {

                Toast.makeText(getApplicationContext(), "Medication Added Successfully", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), User_medication_details.class));
            } else {
                Toast.makeText(getApplicationContext(), "Cant Added Appoint Please Try Agin Som times", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), User_medication_details.class));
            }
            }
        } catch (Exception e) {
            // TODO: handle exception

            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        medication_ids = medication_id[position];

        final CharSequence[] items = {"Delete",  "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(User_medication_details.this);
        // builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {


                if (items[item].equals("Delete")) {
//                    startActivity(new Intent(getApplicationContext(), User_make_payment.class));
                    JsonReq JR=new JsonReq();
                    JR.json_response=(JsonResponse) User_medication_details.this;
                    String q = "/delete_medication?medication_ids="+medication_ids;
                    q=q.replace(" ","%20");
                    JR.execute(q);
                }

                     else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }


        });
        builder.show();
    }
    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(),DoctorHome.class);
        startActivity(b);
    }
}