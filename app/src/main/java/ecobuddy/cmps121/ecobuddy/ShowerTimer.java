package ecobuddy.cmps121.ecobuddy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

public class ShowerTimer extends AppCompatActivity {

    private Chronometer chronometer;
    private boolean running;
    Button start_b, stop_b, log_b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shower_timer_activity);
        chronometer = findViewById(R.id.timer_shower);
        start_b = findViewById(R.id.button_start_timer);
        start_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startChronometer(v);
            }
        });

        stop_b = findViewById(R.id.button_stop_timer);
        stop_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopChronometer(v);
            }
        });

        log_b = findViewById(R.id.logdata_button);
        log_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logData();
            }
        });
    }

    public void startChronometer(View v){
        if(!running){
            chronometer.start();
            running = true;

        }
    }

    public void stopChronometer(View v){
        if(running){
            chronometer.stop();
            running = false;

        }
    }

    private void logData() {

    }
}
