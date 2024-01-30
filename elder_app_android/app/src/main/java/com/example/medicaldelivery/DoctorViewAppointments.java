package com.example.medicaldelivery;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class DoctorViewAppointments extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {
    ListView l1;
    String[] name,phone,email,date,time,value,app_id,statuss,patient_id;
    public static String appoint_id,pns,sts,patient_ids;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_view_appointments);
        l1=(ListView)findViewById(R.id.lvview);
        l1.setOnItemClickListener(this);
        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) DoctorViewAppointments.this;
        String q = "/doctorviewappoinments?lid="+Login.logid;
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
                date = new String[ja1.length()];
                time = new String[ja1.length()];
                app_id = new String[ja1.length()];
                statuss = new String[ja1.length()];
                patient_id = new String[ja1.length()];
                value = new String[ja1.length()];

                for (int i = 0; i < ja1.length(); i++) {
                    name[i] = ja1.getJSONObject(i).getString("firstname")+" "+ja1.getJSONObject(i).getString("lastname");
                    phone[i] = ja1.getJSONObject(i).getString("phone");
                    email[i] = ja1.getJSONObject(i).getString("email");
                    date[i] = ja1.getJSONObject(i).getString("date");
                    time[i] = ja1.getJSONObject(i).getString("time");
                    app_id[i] = ja1.getJSONObject(i).getString("appoinment_id");
                    statuss[i] = ja1.getJSONObject(i).getString("status");
                    patient_id[i] = ja1.getJSONObject(i).getString("user_id");
                    value[i] = "Name: " + name[i]  + "\nphone: " + phone[i] + "\nEmail: " + email[i] + "\nDate: " + date[i] + "\nTime: " + time[i]+ "\nStatus: " + statuss[i];

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

        appoint_id=app_id[position];
        sts=statuss[position];
        pns=phone[position];
        patient_ids=patient_id[position];
//        Toast.makeText(getApplicationContext(),"username : "+patient_ids, Toast.LENGTH_LONG).show();



        if (sts.equalsIgnoreCase("accept")) {
            final CharSequence[] items = {"Consulting Fees","Cancel"};
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(DoctorViewAppointments.this);
            // builder.setTitle("Add Photo!");
            builder.setItems(items, new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int item) {

                    if (items[item].equals("Consulting Fees")) {
                        startActivity(new Intent(getApplicationContext(), Doctor_add_amount.class));
//
                    }
//                    if (items[item].equals("Update Medication")) {
//                        startActivity(new Intent(getApplicationContext(), User_medication_details.class));
////
//                    }
//
//                    if (items[item].equals("Update Details")) {
//
//                        startActivity(new Intent(getApplicationContext(), Doctor_update_food_details.class));
//                    }
//
//                    if (items[item].equals("Upload Checkup Details")) {
//
//                        startActivity(new Intent(getApplicationContext(), DoctorUploadPrescription.class));
//                    }

                    else if (items[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                }
            });
            builder.show();


        }

        else if (sts.equalsIgnoreCase("paid")) {
            final CharSequence[] items = {"Update Details","Upload Checkup Details","Update Medication","Cancel"};
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(DoctorViewAppointments.this);
            // builder.setTitle("Add Photo!");
            builder.setItems(items, new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int item) {
                    if (items[item].equals("Update Medication")) {
                        startActivity(new Intent(getApplicationContext(), User_medication_details.class));
//
                    }

                    if (items[item].equals("Update Details")) {

                        startActivity(new Intent(getApplicationContext(), Doctor_update_food_details.class));
                    }

                    if (items[item].equals("Upload Checkup Details")) {

                        startActivity(new Intent(getApplicationContext(), DoctorUploadPrescription.class));
                    }

                    else if (items[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                }
            });
            builder.show();
        }



        else {
            final CharSequence[] items = {"Customer Details","Accept","Cancel"};
            android.app.AlertDialog.Builder builder = new AlertDialog.Builder(DoctorViewAppointments.this);
            // builder.setTitle("Add Photo!");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {
                        if (items[item].equals("Accept")) {

                            JsonReq JR = new JsonReq();
                            JR.json_response = (JsonResponse) DoctorViewAppointments.this;
                            String q = "/accept?appoint_id=" + appoint_id;
                            q = q.replace(" ", "%20");
                            JR.execute(q);
                            startActivity(new Intent(getApplicationContext(), DoctorHome.class));
                        }
                        if (items[item].equals("Customer Details")) {
                            startActivity(new Intent(getApplicationContext(),DoctorViewCustomers.class));
                        }
                    if (items[item].equals("Make a Call")) {
                        startActivity(new Intent(getApplicationContext(),DoctorViewCustomers.class));
                        pns=phone[item];
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:"+pns));//change the number
                        startActivity(callIntent);
//
                    }

                    }
            });
            builder.show();
        }

//        final CharSequence[] items = {"Customer Details","Make a Call","Update Details","Upload Checkup Details","Cancel"};
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(DoctorViewAppointments.this);
//        // builder.setTitle("Add Photo!");
//        builder.setItems(items, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int item) {
//
//
//                if (items[item].equals("Customer Details")) {
//
//                    startActivity(new Intent(getApplicationContext(),DoctorViewCustomers.class));
//                }
//                else if (items[item].equals("Make a Call")) {
//
//                    pns=phone[item];
//
//                    Intent callIntent = new Intent(Intent.ACTION_CALL);
//                    callIntent.setData(Uri.parse("tel:"+pns));//change the number
//                    startActivity(callIntent);
//
////
//                }
//                if (items[item].equals("Update Details")) {
//
//                    startActivity(new Intent(getApplicationContext(),Doctor_update_food_details.class));
//                }
//
//                if (items[item].equals("Upload Checkup Details")) {
//
//                    startActivity(new Intent(getApplicationContext(),DoctorUploadPrescription.class));
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