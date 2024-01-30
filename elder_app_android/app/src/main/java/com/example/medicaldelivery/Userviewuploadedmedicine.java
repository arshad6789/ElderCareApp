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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Userviewuploadedmedicine extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {
    String[] medicinetype,medicine,quantity,rate,total,value,mid,statuss,pid;
    public static String mids,amounts,stat,pids,quan;
    SharedPreferences sh;
    ListView l1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userviewuploadedmedicine);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        l1=(ListView)findViewById(R.id.lvview);
//        l1.setOnItemClickListener(this);
        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) Userviewuploadedmedicine.this;
        String q = "/userviewuploadedmedicaldetails?pid="+sh.getString("pid","");
        q = q.replace(" ", "%20");
        JR.execute(q);
    }

    @Override
    public void response(JSONObject jo) {
        try {

            String method = jo.getString("method");
            if (method.equalsIgnoreCase("useracceptmedicine")) {

                String status = jo.getString("status");
                Log.d("pearl", status);


                if (status.equalsIgnoreCase("success")) {
                    Toast.makeText(getApplicationContext(), "UPDATED SUCCESSFULLY", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), Userviewuploadedmedicine.class));

                } else {

                    Toast.makeText(getApplicationContext(), " failed.TRY AGAIN!!", Toast.LENGTH_LONG).show();
                }
            }
          else  if (method.equalsIgnoreCase("userrejectmedicine")) {

                String status = jo.getString("status");
                Log.d("pearl", status);


                if (status.equalsIgnoreCase("success")) {
                    Toast.makeText(getApplicationContext(), "UPDATED SUCCESSFULLY", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), Userviewuploadedmedicine.class));

                } else {

                    Toast.makeText(getApplicationContext(), " failed.TRY AGAIN!!", Toast.LENGTH_LONG).show();
                }
            }

            else if (method.equalsIgnoreCase("userviewuploadedmedicaldetails")) {

                String status = jo.getString("status");
                Log.d("pearl", status);


                if (status.equalsIgnoreCase("success")) {
                    JSONArray ja1 = (JSONArray) jo.getJSONArray("data");

                    mid = new String[ja1.length()];
                    pid = new String[ja1.length()];
                    medicinetype = new String[ja1.length()];
                    medicine = new String[ja1.length()];
                    rate = new String[ja1.length()];
                    quantity = new String[ja1.length()];
                    total = new String[ja1.length()];
                    statuss = new String[ja1.length()];
                    value = new String[ja1.length()];

                    for (int i = 0; i < ja1.length(); i++) {

                        mid[i] = ja1.getJSONObject(i).getString("medicine_id");
                        pid[i]=ja1.getJSONObject(i).getString("prescription_id");
                        medicine[i] = ja1.getJSONObject(i).getString("medicine");
                        quantity[i] = ja1.getJSONObject(i).getString("mquantity");
                        medicinetype[i] = ja1.getJSONObject(i).getString("type");
                        rate[i] = ja1.getJSONObject(i).getString("mrate");
                        statuss[i] = ja1.getJSONObject(i).getString("status");
                        total[i] = ja1.getJSONObject(i).getString("mtotal");
                        value[i] = "Medicine: " + medicine[i]+"\nMedicine Type: " + medicinetype[i] + "\nRate: " + rate[i] + "\nQuantity: " + quantity[i] + "\nTotal: " + total[i]+ "\nStatuss: " + statuss[i];

                    }
                    ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, value);
                    l1.setAdapter(ar);
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        mids=mid[position];
        pids=pid[position];
        amounts=total[position];
        stat=statuss[position];
        quan=quantity[position];
        if(stat.equalsIgnoreCase("pending")) {
            final CharSequence[] items = {"Accept", "Reject", "Cancel"};

            AlertDialog.Builder builder = new AlertDialog.Builder(Userviewuploadedmedicine.this);
            // builder.setTitle("Add Photo!");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {

                    if (items[item].equals("Accept")) {

                        JsonReq JR = new JsonReq();
                        JR.json_response = (JsonResponse) Userviewuploadedmedicine.this;
                        String q = "/useracceptmedicine?pid=" + Userviewuploadedmedicine.pids;
                        q = q.replace(" ", "%20");
                        JR.execute(q);

                    }
                  else  if (items[item].equals("Reject")) {

                        JsonReq JR = new JsonReq();
                        JR.json_response = (JsonResponse) Userviewuploadedmedicine.this;
                        String q = "/userrejectmedicine?pid=" + Userviewuploadedmedicine.pids;
                        q = q.replace(" ", "%20");
                        JR.execute(q);

                    }

                    else if (items[item].equals("Cancel")) {
                        dialog.dismiss();
                    }

                }

            });
            builder.show();
        }
       else if(stat.equalsIgnoreCase("accept")) {
            final CharSequence[] items = {"Payment", "Cancel"};

            AlertDialog.Builder builder = new AlertDialog.Builder(Userviewuploadedmedicine.this);
            // builder.setTitle("Add Photo!");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {

                    if (items[item].equals("Payment")) {

                       startActivity(new Intent(getApplicationContext(),Usermakepayment.class));

                    }

                    else if (items[item].equals("Cancel")) {
                        dialog.dismiss();
                    }

                }

            });
            builder.show();
        }

        else if(stat.equalsIgnoreCase("pickup")) {
            final CharSequence[] items = {"Delivery Boy", "Cancel"};

            AlertDialog.Builder builder = new AlertDialog.Builder(Userviewuploadedmedicine.this);
            // builder.setTitle("Add Photo!");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {

                    if (items[item].equals("Delivery Boy")) {

                        startActivity(new Intent(getApplicationContext(),Userviewdeliveryboys.class));

                    }

                    else if (items[item].equals("Cancel")) {
                        dialog.dismiss();
                    }

                }

            });
            builder.show();
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