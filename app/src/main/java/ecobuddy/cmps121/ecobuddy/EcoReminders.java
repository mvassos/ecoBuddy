package ecobuddy.cmps121.ecobuddy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EcoReminders extends AppCompatActivity {

    Button app_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ecoreminders_activity);

        app_button = (Button) findViewById(R.id.appliances_button);
        app_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appliances_nav();


            }
        });



    }

    public void appliances_nav() {
        Intent intent = new Intent(this, ApplianceReminder.class);
        startActivity(intent);

    }
}
