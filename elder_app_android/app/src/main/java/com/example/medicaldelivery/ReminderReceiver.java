package com.example.medicaldelivery;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;

public class ReminderReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // Retrieve the alarm sound URI from the intent
        String soundUriString = intent.getStringExtra("alarmSound");
        if (soundUriString != null) {
            // Convert the sound URI string to Uri
            Uri soundUri = Uri.parse(soundUriString);

            // Play the alarm sound
            Ringtone ringtone = RingtoneManager.getRingtone(context, soundUri);
            if (ringtone != null) {
                ringtone.play();
            }
        }
    }
}