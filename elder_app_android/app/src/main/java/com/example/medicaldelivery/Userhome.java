package com.example.medicaldelivery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Userhome extends AppCompatActivity implements JsonResponse ,TextToSpeech.OnInitListener {

    Button b1,b2,b3,b4,b5,b6,b7,b8,b9,b10,b11,b12;


    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;
    SharedPreferences sh;

    private TextToSpeech textToSpeech;
    public static String aid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userhome);


        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        textToSpeech = new TextToSpeech(this, this);

        //////////////////////////////////////////////////////////////////
        // ShakeDetector initialization
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector();
        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {

            public void onShake(int count) {
                // TODO Auto-generated method stub

                if (count > 2) {
                    Toast.makeText(getApplicationContext(),"hai",Toast.LENGTH_LONG).show();

                    JsonReq jr= new JsonReq();
                    jr.json_response=(JsonResponse) Userhome.this;
                    String q="/getnumber?lid="+Login.logid+"&lati=" + LocationService.lati + "&longi=" + LocationService.logi;
//				Toast.makeText(getApplicationContext(),q,Toast.LENGTH_LONG).show();

                    q.replace("", "%20");
                    jr.execute(q);
                }
//                    if (sh.getString("drivemode", "").equalsIgnoreCase("ON")) {
//                        SmsManager sms = SmsManager.getDefault();
//                        sms.sendTextMessage("9400772704", null, "An accident Occured at the location, http://www.google.com/maps?q=" + LocationService.lati + "," + LocationService.logi, null, null);
//                        sms.sendTextMessage("8594081643", null, "An accident Occured at the location, http://www.google.com/maps?q=" + LocationService.lati + "," + LocationService.logi, null, null);
//                        Toast.makeText(getApplicationContext(), "Accident Detected and  Message is sent", Toast.LENGTH_LONG).show();
//
//                        try {
//                            SQLiteDatabase sqd = openOrCreateDatabase("drowsy", SQLiteDatabase.CREATE_IF_NECESSARY, null);
//                            sqd.setVersion(1);
//                            sqd.setLocale(Locale.getDefault());
//                            String sql = "create table if not exists accident(aid integer PRIMARY KEY AUTOINCREMENT,user_id text, lati text,longi text,date text,status text)";
//                            sqd.execSQL(sql);
//                            ContentValues cv = new ContentValues();
//                            cv.put("user_id", sh.getString("logid", ""));
//                            cv.put("lati", LocationService.lati);
//                            cv.put("longi", LocationService.logi);
//                            cv.put("status", "pending");
//
//                            Calendar c = Calendar.getInstance();
//                            SimpleDateFormat df = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
//                            String formattedDate = df.format(c.getTime());
//
//                            cv.put("date", formattedDate);
//                            sqd.insert("accident", null, cv);
//
//                            cv.clear();
//
////				      				Toast.makeText(getApplicationContext(),"Uploaded", Toast.LENGTH_LONG).show();
//                            sqd.close();
//
//                            Intent a = new Intent(getApplicationContext(), Userhome.class);
//                            startActivity(a);
//
//
//                        } catch (Exception e) {
//                            Toast.makeText(getApplicationContext(), "Exception11111 : " + e, Toast.LENGTH_LONG).show();
//
//                        }
////								Toast.makeText(getApplicationContext(), "Accident Detected"+count, Toast.LENGTH_LONG).show();
////								JsonReq jr= new JsonReq();
////								jr.json_response=(JsonResponse)UserHome.this;
////								String q="/accidentdetect?latitude="+LocationService.lati+"&longitude="+LocationService.logi+"&logid=" + sh.getString("logid", "");
////								jr.execute(q);
//
//                    } else {
//                        Toast.makeText(getApplicationContext(), "Driving mode OFF", Toast.LENGTH_LONG).show();
//
//                    }
//                }
            }
        });

        ///////////////////////////////////////////////


        b1 = (Button) findViewById(R.id.rules);
        b3 = (Button) findViewById(R.id.logout);
        b4 = (Button) findViewById(R.id.traffic);
        b7 = (Button) findViewById(R.id.doct);
        b8 = (Button) findViewById(R.id.d_app);
        b9=(Button)findViewById(R.id.emerbtn);
        b10=(Button)findViewById(R.id.down);
        b11=(Button)findViewById(R.id.btnews);
        b11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), User_news.class));


            }
        });
        b12=(Button)findViewById(R.id.feed);
        b12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Usersendfeedback.class));


            }
        });
        b10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), User_download_files.class));


            }
        });
        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), User_emer_no.class));

            }
        });

        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), UserViewAppointments.class));
            }
        });

        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), UserViewDoctors.class));
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), User_view_medication_details.class));
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), User_add_bills.class));
            }
        });
    }

        private void convertTextToSpeech(String text) {

            if (null == text || "".equals(text)) {
                text = "Please give some input.";
            }
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }


        @Override
        public void onInit(int status) {
            if (status == TextToSpeech.SUCCESS) {
                int result = textToSpeech.setLanguage(Locale.US);
                if (result == TextToSpeech.LANG_MISSING_DATA
                        || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e("error", "This Language is not supported");
                } else {
                    //convertTextToSpeech("Started");
                }
            } else {
                Log.e("error", "Initilization Failed!");
            }
        }

        @Override
        public void onResume() {
            super.onResume();
            // Add the following line to register the Session Manager Listener onResume
            mSensorManager.registerListener(mShakeDetector, mAccelerometer,	SensorManager.SENSOR_DELAY_UI);
        }

        @Override
        public void onPause() {
            // Add the following line to unregister the Sensor Manager onPause
            mSensorManager.unregisterListener(mShakeDetector);
            super.onPause();
        }

        @Override
        public void response(JSONObject jo) {
            // TODO Auto-generated method stub
            // TODO Auto-generated method stub


            try {
                String status = jo.getString("status");
                Log.d("result", status);
                //   Toast.makeText(getApplicationContext(), status,Toast.LENGTH_LONG).show();
                if (status.equalsIgnoreCase("success")) {
                    String number = jo.getString("contact_no");

//				SharedPreferences.Editor ed=sh.edit();
//				ed.putString("number", number);
//
//				ed.commit();

                    SmsManager sms = SmsManager.getDefault();
                    sms.sendTextMessage(number, null, "An Emergency Occured at the location, http://www.google.com/maps?q=" + LocationService.lati + "," + LocationService.logi, null, null);
//                        sms.sendTextMessage("8594081643", null, "An accident Occured at the location, http://www.google.com/maps?q=" + LocationService.lati + "," + LocationService.logi, null, null);
                    Toast.makeText(getApplicationContext(), "Emergency Detected and  Message is sent", Toast.LENGTH_LONG).show();



                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + number));
                    startActivity(callIntent);
//                    startService(new Intent(getApplicationContext(),CameraService.class));

                    //startService(new Intent(getApplicationContext(), LocationService.class));






                } else
                {
                    Toast.makeText(getApplicationContext(), "No number", Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
            }

//            try{
//                String status=jo.getString("status");
//                String method=jo.getString("method");
//                if(method.equalsIgnoreCase("accidentdetect"))
//                {
//                    if(status.equalsIgnoreCase("success"))
//                    {
//
//                        Toast.makeText(getApplicationContext(), "Accident Detected..", Toast.LENGTH_LONG).show();
//                        convertTextToSpeech("Accident Detected..");
//
//                    }
//                    else
//                    {
//                        Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();
//                    }
//                }
//            }
//            catch(Exception e){
//                e.printStackTrace();
//                Toast.makeText(getApplicationContext(), "exp : "+e, Toast.LENGTH_LONG).show();
//            }
    }
    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(),Userhome.class);
        startActivity(b);
    }


}