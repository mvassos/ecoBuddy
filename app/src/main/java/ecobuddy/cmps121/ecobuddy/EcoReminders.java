package ecobuddy.cmps121.ecobuddy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EcoReminders extends AppCompatActivity {

    Button app_btn, bill_btn, trash_btn, chores_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ecoreminders_activity);

        app_btn = (Button) findViewById(R.id.appliances_button);
        app_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appliances_nav();
            }
        });

        trash_btn = (Button) findViewById(R.id.trash_button);
        trash_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                trash_nav();
            }
        });

        chores_btn = (Button) findViewById(R.id.chores_button);
        chores_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appliances_nav();
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
        Intent i = new Intent(this, SetBill.class);
        startActivity(i);
    }

    public void appliances_nav() {
        Intent intent = new Intent(this, ApplianceReminder.class);
        startActivity(intent);

    }
}
