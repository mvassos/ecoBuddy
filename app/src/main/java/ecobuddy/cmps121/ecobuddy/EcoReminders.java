package ecobuddy.cmps121.ecobuddy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EcoReminders extends AppCompatActivity {

    Button bill_btn, send_msg_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eco_reminders);


        send_msg_btn = (Button) findViewById(R.id.send_message_btn);
        send_msg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });

        bill_btn = (Button) findViewById(R.id.set_bill_button);
        bill_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBill_nav();
            }
        });

    }

    public void setBill_nav() {
        Intent i = new Intent(this, SetBillReminder.class);
        startActivity(i);
        finish();
    }

    public void sendMessage() {
        Intent intent = new Intent(this, MessageSender.class);
        startActivity(intent);
    }
}
