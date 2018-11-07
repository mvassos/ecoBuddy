package ecobuddy.cmps121.ecobuddy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class ApplianceReminder extends AppCompatActivity {
    EditText ed1;
    EditText ed2;
    EditText ed3;
    Button button6;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appliance_reminder_activity);

        ed1=(EditText)findViewById(R.id.name_field);
        ed2=(EditText)findViewById(R.id.subject_field);
        ed3=(EditText)findViewById(R.id.body_field);
        button6 =(Button)findViewById(R.id.sendnotification_button);

        button6.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                grabData();
            }
        });
    }

    private void grabData() {
        String tittle=ed1.getText().toString().trim();
        String subject=ed2.getText().toString().trim();
        String body=ed3.getText().toString().trim();

        NotificationManager nm =(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notify=new Notification.Builder
                (getApplicationContext()).setContentTitle(tittle).setContentText(body).
                setContentTitle(subject).setSmallIcon(R.drawable.download).build();

        notify.flags |= Notification.FLAG_AUTO_CANCEL;
        nm.notify(0, notify);
    }
}