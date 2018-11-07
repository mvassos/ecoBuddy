package ecobuddy.cmps121.ecobuddy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button button;
    Button button2;
    Button button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.ecoreminders_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ecoReminders_nav();


            }
        });

        button2 = (Button) findViewById(R.id.showertimer_button);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showerTimer_nav();

            }
        });

        button3 = (Button) findViewById(R.id.data_button);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataAnalytics_nav();
            }
        });
    }

    public void ecoReminders_nav() {
        Intent intent = new Intent(this, EcoReminders.class);
        startActivity(intent);
    }

    public void showerTimer_nav() {
        Intent intent = new Intent(this, ShowerTimer.class);
        startActivity(intent);
    }

    public void dataAnalytics_nav() {
        Intent intent = new Intent(this, DataAnalytics.class);
        startActivity(intent);
    }
}
