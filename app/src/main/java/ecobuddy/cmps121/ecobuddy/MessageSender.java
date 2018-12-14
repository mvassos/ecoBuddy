package ecobuddy.cmps121.ecobuddy;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class MessageSender extends AppCompatActivity {
    EditText body_et;
    Button snd_msg;
    Spinner choices, contacts;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);

        body_et=(EditText)findViewById(R.id.body_field);
        snd_msg =(Button)findViewById(R.id.sendnotification_button);
        choices = findViewById(R.id.reminders_spinner);
        contacts = findViewById(R.id.contacts_spinner);
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
        String number = contacts.getItemAtPosition(contacts.getSelectedItemPosition()).toString();

        if (body.length() > 0) {
            checkForSmsPermission();
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(number, null, subject + ": " + body, null, null);
            finish();
        } else {
            toastMessage("No Text was entered...", 0);
        }
    }

    private void checkForSmsPermission() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS},
                    MY_PERMISSIONS_REQUEST_SEND_SMS);
        } else {
            enableSmsButton();
        }
    }

    private void enableSmsButton() {
        snd_msg.setEnabled(true);
    }


    private void toastMessage(String msg, int len) {
        Toast toast = Toast.makeText(this, msg, len);
        View view = toast.getView();
        view.getBackground().setColorFilter(getResources().getColor(R.color.Black), PorterDuff.Mode.SRC_IN);
        TextView text = view.findViewById(android.R.id.message);
        text.setTextColor(getResources().getColor(R.color.Teal));
        toast.show();
    }
}