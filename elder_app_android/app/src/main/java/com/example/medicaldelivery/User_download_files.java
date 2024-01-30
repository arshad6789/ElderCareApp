package com.example.medicaldelivery;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class User_download_files extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {
    ListView l1;
    String[] product, pres_id,  file,value,doctor;
    SharedPreferences sh;
    public static String upload_ids, qpath;
    String dwld_path, path1;
    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_download_files);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        l1 = (ListView) findViewById(R.id.lvview);
        l1.setOnItemClickListener(this);
        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) User_download_files.this;
        String q = "/User_download_files?log_id=" + Login.logid;
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
                product = new String[ja1.length()];
                file = new String[ja1.length()];
                pres_id = new String[ja1.length()];
                doctor = new String[ja1.length()];
//                file = new String[ja1.length()];
                value = new String[ja1.length()];

                for (int i = 0; i < ja1.length(); i++) {
                    pres_id[i] = ja1.getJSONObject(i).getString("pres_id");
                    product[i] = ja1.getJSONObject(i).getString("prisdate");
                    file[i] = ja1.getJSONObject(i).getString("file");
                    doctor[i] = ja1.getJSONObject(i).getString("fname");

                    value[i] = "Date: " + product[i]+"\nDoctor :" + doctor[i] ;

                }
                ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, value);
                l1.setAdapter(ar);
            }


        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        upload_ids = pres_id[position];
        qpath = file[position];
        final CharSequence[] items = {"Download File", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(User_download_files.this);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {


//                if (items[item].equals("View Marks")) {
//
//                    startActivity(new Intent(getApplicationContext(), StudentViewMarks.class));
//                }
               if (items[item].equals("Download File")) {

                    sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    Toast.makeText(getApplicationContext(), sh.getString("ip", ""), Toast.LENGTH_LONG).show();
                    dwld_path = "http://" + sh.getString("ip", "") + "/" + qpath;
                    dwld_path = dwld_path.replace(" ", "%20");
                    String[] temp = qpath.split("\\/");
                    qpath = temp[1];
                    Toast.makeText(getApplicationContext(), qpath, Toast.LENGTH_LONG).show();
                    downloadFile();

                }
//                else if (items[item].equals("View Comments")) {
//
//                    startActivity(new Intent(getApplicationContext(), StudentViewComments.class));
//                } else if (items[item].equals("Cancel")) {
//                    dialog.dismiss();
//                }
            }

        });
        builder.show();
    }

    void downloadFile()
    {
        // declare the dialog as a member field of your activity
//        ProgressDialog mProgressDialog;

        // instantiate it within the onCreate method
        mProgressDialog = new ProgressDialog(User_download_files.this);
        mProgressDialog.setMessage("Ready To Download..");
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setCancelable(true);

        // execute this when the downloader must be fired
        final DownloadTask downloadTask = new DownloadTask(getApplicationContext()); //YourActivity.this
        downloadTask.execute(dwld_path);

        mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                downloadTask.cancel(true);
            }
        });
    }

    // usually, subclasses of AsyncTask are declared inside the activity class.
    // that way, you can easily modify the UI thread from here
    private class DownloadTask extends AsyncTask<String, Integer, String> {

        private Context context;
        private PowerManager.WakeLock mWakeLock;

        public DownloadTask(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... sUrl) {
            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;
            try {
                URL url = new URL(sUrl[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                // expect HTTP 200 OK, so we don't mistakenly save error report
                // instead of the file
                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return "Server returned HTTP " + connection.getResponseCode()
                            + " " + connection.getResponseMessage();
                }

                // this will be useful to display download percentage
                // might be -1: server did not report the length
                int fileLength = connection.getContentLength();

                // download the file
                input = connection.getInputStream();
                output = new FileOutputStream("/sdcard/Download"+ qpath);

                byte data[] = new byte[4096];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    // allow canceling with back button
                    if (isCancelled()) {
                        input.close();
                        return null;
                    }
                    total += count;
                    // publishing the progress....
                    if (fileLength > 0) // only if total length is known
                        publishProgress((int) (total * 100 / fileLength));
                    output.write(data, 0, count);
                }
            } catch (Exception e) {
                return e.toString();
            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                }

                if (connection != null)
                    connection.disconnect();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // take CPU lock to prevent CPU from going off if the user
            // presses the power button during download
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                    getClass().getName());
            mWakeLock.acquire();
            mProgressDialog.show();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            // if we get here, length is known, now set indeterminate to false
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setMax(100);
            mProgressDialog.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String s) {

            mWakeLock.release();
            mProgressDialog.dismiss();
            if (s != null)
                Toast.makeText(context,"Download error : "+s, Toast.LENGTH_LONG).show();
            else
                Toast.makeText(context,"File downloaded", Toast.LENGTH_SHORT).show();
        }
    }

}