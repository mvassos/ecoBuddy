package ecobuddy.cmps121.ecobuddy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;


public class ApplianceReminder extends AppCompatActivity {
    EditText body_et;
    Button snd_msg;
    Spinner choices;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appliance_reminder_activity);

        body_et=(EditText)findViewById(R.id.body_field);
        snd_msg =(Button)findViewById(R.id.sendnotification_button);
        choices = findViewById(R.id.reminders_spinner);

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

        NotificationManager nm =(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notify=new Notification.Builder
                (getApplicationContext()).setContentText(body).
                setContentTitle(subject).setSmallIcon(R.drawable.download).build();
        notify.flags |= Notification.FLAG_AUTO_CANCEL;
        nm.notify(0, notify);
        finish();
    }
}