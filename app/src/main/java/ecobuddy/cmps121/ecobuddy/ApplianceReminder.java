package ecobuddy.cmps121.ecobuddy;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class ApplianceReminder extends AppCompatActivity {
    EditText body_et, phone_number_et;
    Button snd_msg;
    Spinner choices;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appliance_reminder_activity);

        body_et=(EditText)findViewById(R.id.body_field);
        phone_number_et = findViewById(R.id.phone_number_field);
        snd_msg =(Button)findViewById(R.id.sendnotification_button);
        choices = findViewById(R.id.reminders_spinner);
        snd_msg.setEnabled(true);

        snd_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grabData();
            }
        });
    }

    private void grabData() {
        String subject = choices.getItemAtPosition(choices.getSelectedItemPosition()).toString();
        String body = body_et.getText().toString().trim();
        String number = phone_number_et.getText().toString();

        if (checkForSmsPermission()) {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(number, null, subject + ": " + body, null, null);
            Toast.makeText(this, "Message sent!", Toast.LENGTH_LONG);
        }


        /*
        NotificationManager nm =(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notify=new Notification.Builder
                (getApplicationContext()).setContentText(body).
                setContentTitle(subject).setSmallIcon(R.drawable.download).build();
        notify.flags |= Notification.FLAG_AUTO_CANCEL;
        nm.notify(0, notify);
  */
        finish();
    }
    private boolean checkForSmsPermission() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS) !=
                PackageManager.PERMISSION_GRANTED) {
            Log.d("permission", "not granted");
            // Permission not yet granted. Use requestPermissions().
            // MY_PERMISSIONS_REQUEST_SEND_SMS is an
            // app-defined int constant. The callback method gets the
            // result of the request.
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS},
                    MY_PERMISSIONS_REQUEST_SEND_SMS);
        } else {
            // Permission already granted. Enable the message button.
            enableSmsButton();
            return true;
        }
        return false;
    }
    private void enableSmsButton() {
        snd_msg.setEnabled(true);
    }
}